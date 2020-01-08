package chatClasses;

import java.io.IOException;

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
		
		//Loads the chatrooms and updates every 50 seconds
		displayChatrooms();
		Thread t = new PeriodicChecker();
	    t.setDaemon(true);
		t.start();
		displayChatroomMessages();
		
		view.getJoinChatroom().setOnAction(e -> {
			try {
				model.joinChatroom(view.getChatRoomListview().getSelectionModel().getSelectedItem(), serviceL.getConfiguration().getToken(), serviceL.getConfiguration().getValidatedUser());
				} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
				
		view.getCreateChatroom().setOnAction(e -> {
			view.createChatroomPopup();
			view.getdefCreateChatroom().setOnAction(a -> {
			model.createChatroom(serviceL.getConfiguration().getToken(), view.getChatroomName());
			view.getCreateChatroomPopupStage().close();
			displayChatrooms();
			});	
		});
		
		view.getDeleteChatroom().setOnAction(e -> {
			model.deleteChatroom(serviceL.getConfiguration().getToken(), view.getChatRoomListview().getSelectionModel().getSelectedItem());
			serviceL.getConfiguration().clearChatMessagesOL();
			displayChatrooms();
		});
		
		view.getbtnSend().setOnAction(e -> {
			model.sendMessage(serviceL.getConfiguration().getJoinedChatroom(), view.getTxtMessage());
		});
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
	
	private void displayChatroomMessages() {
		serviceL.getConfiguration().getChatMessages().addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(Change<? extends String> arg0) {
				// TODO Auto-generated method stub
				while(arg0.next()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							view.getMessagesListview().getItems().clear();
							for (String a : serviceL.getConfiguration().getChatMessages()) {
									view.getMessagesListview().getItems().add(a);
						
							}
						}
						
					});
				}
			}
			
		});
	}
}
