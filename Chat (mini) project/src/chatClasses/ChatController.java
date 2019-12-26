package chatClasses;

import java.util.List;

import App.ServiceLocator;
import abstractClasses.Controller;

public class ChatController extends Controller<ChatModel, ChatView> {

	ServiceLocator serviceL = ServiceLocator.getServiceLocator();
	
	public ChatController(ChatModel model, ChatView view) {
		super(model, view);
		// TODO Auto-generated constructor stub
		
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
	}
	
	private void getChatrooms() {
		List<String> list = serviceL.getConfiguration().getChatRooms();
		for (String s : list) {
			view.getChatRoomList().getItems().add(s);
		}
	}
}
