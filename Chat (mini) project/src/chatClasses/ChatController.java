package chatClasses;

import java.io.IOException;
import java.util.List;

import App.ChatApp;
import App.ServiceLocator;
import abstractClasses.Controller;
import javafx.scene.control.Button;
import loginClasses.LoginModel;
import loginClasses.LoginView;

public class ChatController extends Controller<ChatModel, ChatView> {

	ServiceLocator serviceL = ServiceLocator.getServiceLocator();
	
	public ChatController(ChatModel model, ChatView view) {
		super(model, view);
		
		// First of all load all chatrooms, so that they can be displayed
		model.loadChatrooms();
		view.createCahtroomListView();
		
		try {
			Thread.sleep(100);
			getChatrooms();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		view.getJoinChatroom().setOnAction(e -> {
			try {
				model.joinChatroom(view.getChatRoomList().getSelectionModel().getSelectedItem(), serviceL.getConfiguration().getValidatedUser());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		/*
		view.getLeaveChatroom().setOnAction(e -> {
			
		});
		
		view.getCreateChatroom().setOnAction(e -> {
			
		});
		
		view.getDeleteChatroom().setOnAction(e -> {
			
		});
		
		*/
	}
	
	
	private void getChatrooms() {
		List<String> list = serviceL.getConfiguration().getChatRooms();
		for (String s : list) {
			view.getChatRoomList().getItems().add(s);
		}
	}
}
