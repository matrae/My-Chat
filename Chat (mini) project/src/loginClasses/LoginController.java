package loginClasses;

import java.io.IOException;

import App.ChatApp;
import abstractClasses.Controller;
import abstractClasses.View;
import javafx.concurrent.Worker;

public class LoginController extends Controller<LoginModel, LoginView>{
		
	@SuppressWarnings("unused")
	public LoginController(final ChatApp main, LoginModel model, LoginView view) {
		super(model, view);
		// TODO Auto-generated constructor stub
      	
		
		//Prov starts the chat view until login is implemented
      	view.getLoginButton().setOnAction(event -> {
  			try {
				model.login(view.getUsername(), view.getPassword());
				//check if login message was true
				if (1==1) {
					//check if login worked
					
					//Display Chatrooms
					main.startApp();
				} else {
					//Display a message that the login did not work
					
				} 
  			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     	});
      	
   
      	
      	
      	
      	// Creates the popup window where you can create an account
      	view.getCreateAccButton().setOnAction(event -> {
      		view.createAccountPopup();
      		view.getCreate().setOnAction(event1 -> {
      			try {
					model.createAccount(view.getCreateUsername(), view.getCreatePassword());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
      		});
     	});
	}
}

