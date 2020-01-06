package commonClasses;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import App.ServiceLocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * This class provides basic functionality for loading and saving program
 * options. Default options may be delivered with the application; local options
 * (changed by the user) are saved to a file. Program constants can be defined
 * by defining options that the user has no way to change.
 * 
 * @author Brad Richards
 */
public class Configuration {
    ServiceLocator sl = ServiceLocator.getServiceLocator();  // for easy reference
    Logger logger = sl.getLogger();       					// for easy reference

    private Properties defaultOptions;
    private Properties localOptions;
    private String token = null;
    private String validatedUser = null;
    
    // Server information
    String ipAddress = "147.86.8.31";
    int portNumber = 50001;
    private Socket socket = null;
    
    private ObservableList<String> chatRoomAL = FXCollections.observableArrayList();
    private ObservableList<String> chatMessagesOL = FXCollections.observableArrayList();
    private String user;
    private String joinedChatroom;

    
    //Establish SERVER CONNECTOIN
    public void connectServer() throws IOException {        
    	// Not safe connection implemented
    	socket = new Socket(ipAddress, portNumber);
    	
    	if (socket.isConnected() == false) {
    		logger.info("No Server Connection - Something went wrong");
    	} else {
    		logger.info("Server Connected succesfully");
    	}
    }
    
    Thread communicationThread;
    
    // SERVER COMMUNICATION
    public void communicateServer() {
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));		
						
				// Create thread to read incoming messages
				communicationThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						String msg;
						try {
							//Reads incoming messages
							msg = socketIn.readLine();
							saveServerMessages(msg);
							logger.info("Received: " + msg);
						} catch (IOException e) {
							break;
						}
					}
				}
			});
			// Thread is closed when program is closed
	        communicationThread.setDaemon(true);
	        communicationThread.start();	
	        
		} catch (IOException e) {
            e.printStackTrace();
		}
	}
        
    //Methods which saves the messages in an Array so that we can read the messages
    private void saveServerMessages(String serverInput) {
    	String[] msgArray = serverInput.split("\\|");
    	
    	if (msgArray[0].equals("Result") && msgArray[1].equals("true") && msgArray.length == 3)  {
    		//Find out if its a login information
    		token = msgArray[2];
    	} else if (msgArray[0].equals("Result") && msgArray[1].equals("true") && msgArray.length != 3) {
    		//Split into different with. split
    		String[] chatRooms = serverInput.split("\\|");
    		//Add to arrayList (remeber first 3 are not chatrooms "true" etc.)
    		for (int i = 2; i < chatRooms.length; i++) { 
    			if (!chatRoomAL.contains(chatRooms[i])) {
    			chatRoomAL.add(chatRooms[i]);	
    			}
    		} 
    	}
    	
    	if (msgArray[0].equals("MessageText")  && msgArray.length == 4 && msgArray[2].equals(joinedChatroom)) {
    		String user = msgArray[1];
    		String message = msgArray[3];
    		
    		// Create message and add it to the observable list
    		String messageString = user + ":  " + message;
    		chatMessagesOL.add(messageString);
    	}
    }
    
    public ObservableList<String> getChatRooms() {
    	return chatRoomAL;
    }
   
    public ObservableList<String> getChatMessages() {
    	return chatMessagesOL;
    }
    
    public void clearChatMessagesOL() {
    	chatMessagesOL.clear();
    }

    
    public Configuration() {
        // Load default properties from wherever the code is
        defaultOptions = new Properties();
        String defaultFilename = sl.getAPP_NAME() + "_defaults.cfg";
        InputStream inStream = sl.getAPP_CLASS().getResourceAsStream(defaultFilename);
        try {
            defaultOptions.load(inStream);
            logger.config("Default configuration file found");
        } catch (Exception e) {
            logger.warning("No default configuration file found: " + defaultFilename);
        } finally {
            try {
                inStream.close();
            } catch (Exception ignore) {
            }
        }

        // Define locally-saved properties; link to the default values
        localOptions = new Properties(defaultOptions);

        // Load the local configuration file, if it exists.
        try {
            inStream = new FileInputStream(sl.getAPP_NAME() + ".cfg");
            localOptions.load(inStream);
        } catch (FileNotFoundException e) { // from opening the properties file
            logger.config("No local configuration file found");
        } catch (IOException e) { // from loading the properties
            logger.warning("Error reading local options file: " + e.toString());
        } finally {
            try {
                inStream.close();
            } catch (Exception ignore) {
            }
        }
           
        for (Enumeration<Object> i = localOptions.keys(); i.hasMoreElements();) {
            String key = (String) i.nextElement();
            logger.config("Option: " + key + " = " + localOptions.getProperty(key));
        }
    }
    
    public void save() {
        FileOutputStream propFile = null;
        try {
            propFile = new FileOutputStream(sl.getAPP_NAME() + ".cfg");
            localOptions.store(propFile, null);
            logger.config("Local configuration file saved");
        } catch (Exception e) {
            logger.warning("Unable to save local options: " + e.toString());
        } finally {
            if (propFile != null) {
                try {
                    propFile.close();
                } catch (Exception e) {
                }
            }
        }
    }
    
    public String getOption(String name) {
        return localOptions.getProperty(name);
    }
    
    public void welcomeMessage() {
    	chatMessagesOL.add("Server: Welcome! You joined the chatroom " + getJoinedChatroom());
    }
    
    public void setLocalOption(String name, String value) {
        localOptions.setProperty(name, value);
    }
    
    public void setValidatedUser(String user) {
    	this.validatedUser = user;
    }
    
    public String getValidatedUser() {
    	return validatedUser;
    }
    
    public String getJoinedChatroom() {
    	return joinedChatroom;
    }
    
    public void setJoinedChatroom(String name) {
    	joinedChatroom = name;
    }

	public Socket getSocket() {
		// TODO Auto-generated method stub
		return socket;
	}


	public String getToken() {
		// TODO Auto-generated method stub
		return token;
	}

	public String getChatroom() {
		// TODO Auto-generated method stub
		return null;
	}
}
