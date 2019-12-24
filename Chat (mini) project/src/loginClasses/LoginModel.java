package loginClasses;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import App.ChatApp;
import App.ServiceLocator;
import abstractClasses.Model;

public class LoginModel extends Model {
    ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
    private Socket socket = serviceLocator.getConfiguration().getSocket();
    
    private boolean passwordState = false;
	
	public void login(String username, String password) throws IOException {
		
		OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
		//Message
		String login = "Login" + "|" + username + "|" + password;
		
        try {
            socketOut.write(login + "\n");
            socketOut.flush();
            serviceLocator.getLogger().info("Sent: " + login);
            serviceLocator.getConfiguration().communicateServer();
        } catch (IOException e) {
            e.printStackTrace();
        }	
	}
	
	public void validateLogin(ChatApp main) {
		if (serviceLocator.getConfiguration().getToken() != null) {
        	//start app view
			main.startApp();
			passwordState = false;
		} else {
			//Add error message
			//changeState(passwordState);
			passwordState = true;
		}
	}
	
	public boolean getState() {
		return passwordState;
	}
	

	//Sends a message to the server to create the account
	public void createAccount(String username, String password, String passwordR) throws IOException {
		// TODO Auto-generated method stub
		OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());

		if (passwordR.contentEquals(password)) {
			String createAccount = "CreateLogin" + "|" + username + "|" + password;
	
			try {
				socketOut.write(createAccount + "\n");
				socketOut.flush();
				serviceLocator.getLogger().info("Sent: " + createAccount);
				serviceLocator.getConfiguration().communicateServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			//Create an errormessage here
			serviceLocator.getLogger().info("Password did not match");
		}
	}
}
