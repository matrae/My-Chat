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
	         serviceLocator.getConfiguration().welcomeMessage();
	         
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
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
