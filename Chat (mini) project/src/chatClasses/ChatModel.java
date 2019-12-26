package chatClasses;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import App.ServiceLocator;
import abstractClasses.Model;
import javafx.collections.ObservableList;

public class ChatModel extends Model {
	
	ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private Socket socket = serviceLocator.getConfiguration().getSocket();
	
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
}
