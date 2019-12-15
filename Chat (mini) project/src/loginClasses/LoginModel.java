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
	
	public void login() {
		
	}

	//Sends a message to the server to create the account
	public void createAccount(String username, String password) throws IOException {
		// TODO Auto-generated method stub
		
		OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
		String createAccount = "CreateAccount" + "|" + username + "|" + password;
		
        try {
            socketOut.write(createAccount + "\n");
            socketOut.flush();
            serviceLocator.getLogger().info("Sent: " + createAccount);
            serviceLocator.getConfiguration().communicateServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
