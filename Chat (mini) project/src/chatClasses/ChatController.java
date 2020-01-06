package chatClasses;

import java.io.IOException;
import java.util.List;

import App.ChatApp;
import App.ServiceLocator;
import abstractClasses.Controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import loginClasses.LoginModel;
import loginClasses.LoginView;

public class ChatController extends Controller<ChatModel, ChatView> {

	ServiceLocator serviceL = ServiceLocator.getServiceLocator();

	
	public ChatController(ChatModel model, ChatView view) {
		super(model, view);
		
		Thread t = new PeriodicChecker();
	    t.setDaemon(true);
		t.start();
		
		view.getJoinChatroom().setOnAction(e -> {
			try {
				model.joinChatroom(view.getChatRoomListview().getSelectionModel().getSelectedItem(), serviceL.getConfiguration().getToken(), serviceL.getConfiguration().getValidatedUser());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		/*
		view.getLeaveChatroom().setOnAction(e -> {
			model.leaveChatroom(serviceL.getConfiguration().getToken(), chatroom, user);
		});
		*/
		
		// Somehow update chatrooms after
		view.getCreateChatroom().setOnAction(e -> {
			model.createChatroom(serviceL.getConfiguration().getToken(), "3333333333333333333");
			displayChatrooms();
		});
		/*
		view.getDeleteChatroom().setOnAction(e -> {
			model.deleteChatroom(serviceL.getConfiguration().getToken(), chatRoom);
		});
		*/

	}
	
	public class PeriodicChecker extends Thread
	{
	    @Override
	    public void run()
	    {
	        while(true) {
	           serviceL.getLogger().info("Updating Chatrooms");
	           displayChatrooms();
	           try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
	    }

	}
	
	private void displayChatrooms() {
		model.loadChatrooms();
		displayChattroomChanges();
	}
	
	private void displayChattroomChanges() {
		serviceL.getConfiguration().getChatRooms().addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(Change<? extends String> arg0) {
				// TODO Auto-generated method stub
				while(arg0.next()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							view.getChatRoomListview().getItems().clear();
							for (String a : serviceL.getConfiguration().getChatRooms()) {
								if(!view.getChatRoomListview().getItems().contains(a)) {
									view.getChatRoomListview().getItems().add(a);
								}								
							}
						}
						
					});
				}
			}
			
		});
	}
}
