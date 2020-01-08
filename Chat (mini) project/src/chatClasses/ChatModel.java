package chatClasses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import App.ServiceLocator;
import abstractClasses.Model;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ChatModel extends Model {
	
	ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private Socket socket = serviceLocator.getConfiguration().getSocket();
	
	public void sendMessage(String chatroom, String message) {
		String sendMessage = "SendMessage" + "|" + serviceLocator.getConfiguration().getToken() + "|" + chatroom + "|" + message;
		
		try {
			OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
			socketOut.write(sendMessage + "\n");
			socketOut.flush();
			serviceLocator.getLogger().info("Sent: " + sendMessage);
			serviceLocator.getConfiguration().communicateServer();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void saveChatroom(ObservableList<String> lsit, String chatroom) {
		if (chatroom != null) {
			try {
				FileOutputStream fileout = new FileOutputStream(chatroom + ".txt");
				ObjectOutputStream oos = new ObjectOutputStream(fileout);
				oos.writeObject(new ArrayList<String>(lsit));
				oos.close();
				serviceLocator.getLogger().info("File created for " + chatroom);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void loadChatroom(Path chatroom) {
		try {
			InputStream in = Files.newInputStream(chatroom);
			ObjectInputStream ois = new ObjectInputStream(in);			
			// get Arraylist
			 List<String> list = (List<String>) ois.readObject() ;
			// for every item in the list write to OL
			serviceLocator.getConfiguration().getChatMessages().addAll(list);
			serviceLocator.getLogger().info("File loaded for " + chatroom);
		} catch (IOException e) {
			//do nothing
		} catch (ClassNotFoundException e) {
			serviceLocator.getLogger().info("No file found for " + chatroom);
		}
        
	}
	
	public void loadChatrooms() {		
		// Server message to get chatrooms
		String getChatrooms = "ListChatrooms" + "|" + serviceLocator.getConfiguration().getToken();
		
		try {
			OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
			socketOut.write(getChatrooms + "\n");
			socketOut.flush();
			serviceLocator.getLogger().info("Sent: " + getChatrooms);
			serviceLocator.getConfiguration().communicateServer();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void joinChatroom(String selectedItem, String token, String user) throws IOException {
		String joinChatroom = "JoinChatroom" + "|" + token + "|" + selectedItem + "|" + user;
		serviceLocator.getConfiguration().clearChatMessagesOL();
		serviceLocator.getConfiguration().setJoinedChatroom(selectedItem);
		  try {
			 OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
			 socketOut.write(joinChatroom + "\n");
			 socketOut.flush();
			 serviceLocator.getLogger().info("Sent: " + joinChatroom);
	         serviceLocator.getConfiguration().communicateServer();
	         welcomeMessage(selectedItem);
	         
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
    public void welcomeMessage(String chatroom) {
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    	Date date = new Date(System.currentTimeMillis());
    	sendMessage(chatroom, " joined " + chatroom + " at " + formatter.format(date));
    	//chatMessagesOL.add("Server: Welcome! You joined the chatroom " + getJoinedChatroom());
    }
	
	//Not used yet
	public void leaveChatroom(String token, String chatroom, String user) {
		String leaveChatroom = "LeaveChatroom" + "|" + token + "|" + chatroom + "|" + user;
		
		  try {
			 OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
			 socketOut.write(leaveChatroom + "\n");
			 socketOut.flush();
			 serviceLocator.getLogger().info("Sent: " + leaveChatroom);
	         serviceLocator.getConfiguration().communicateServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createChatroom(String token, String chatRoomName) {
		String createChatroom = "CreateChatroom" + "|" + token + "|" + chatRoomName + "|" + "true";
		
		  try {
			 OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
			 socketOut.write(createChatroom + "\n");
			 socketOut.flush();
			 serviceLocator.getLogger().info("Sent: " + createChatroom);
	         serviceLocator.getConfiguration().communicateServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteChatroom(String token, String chatRoom) {
		String deleteChatroom = "DeleteChatroom" + "|" + token + "|" + chatRoom;
		
		  try {
			 serviceLocator.getConfiguration().clearRooms();
			 OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
			 socketOut.write(deleteChatroom + "\n");
			 socketOut.flush();
			 serviceLocator.getLogger().info("Sent: " + deleteChatroom);
	         serviceLocator.getConfiguration().communicateServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
