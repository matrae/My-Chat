package loginClasses;

import java.io.IOException;

import App.ChatApp;
import abstractClasses.Controller;
import abstractClasses.View;
import chatClasses.ChatModel;
import javafx.concurrent.Worker;

public class LoginController extends Controller<LoginModel, LoginView>{
	
	@SuppressWarnings("unused")
	public LoginController(final ChatApp main, LoginModel model, LoginView view) {
		super(model, view);
		// TODO Auto-generated constructor stub
      	
		
		//Prov starts the chat view until login is implemented
      	view.getLoginButton().setOnAction(event -> {
  			try {
  				// In the login method the passsword is validated
				model.login(view.getUsername(), view.getPassword());
				// add sleep because it validates to fast
				Thread.sleep(400);
				// Check if we got a true result login
				model.validateLogin(main);
				view.updateText();
				
  			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     	});
      	

      	// Creates the popup window where you can create an account
      	view.getCreateAccButton().setOnAction(event -> {
      		view.createAccountPopup();
      		view.getCreate().setOnAction(event1 -> {
      				try {
      					model.createAccount(view.getCreateUsername(), view.getCreatePassword(), view.getRepeatPassword());
      					
      					// oif the password match
      					if (view.getCreatePassword().contentEquals(view.getRepeatPassword())) {
          					view.getPopupStage().close();
      					}
      				} catch (IOException e) {
      					// TODO Auto-generated catch block
      					e.printStackTrace();
      				}      			
      		});
     	});
	}
}

