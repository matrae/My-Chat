package loginClasses;

import App.ChatApp;
import abstractClasses.Controller;
import abstractClasses.View;
import javafx.concurrent.Worker;

public class LoginController extends Controller<LoginModel, LoginView>{

	public LoginController(final ChatApp main, LoginModel model, LoginView view) {
		super(model, view);
		// TODO Auto-generated constructor stub
      	
		//Prov starts the chat view until login is implemented
      	view.getLoginButton().setOnAction(event -> {
      		main.startApp();
      	});
      	
      	// Creates the popup window where you can create an account
      	view.getCreateAccButton().setOnAction(ecent -> {
      		view.createAccountPopup();
      	});
      	
      	//Create an account INPUT: Username and a Password
        //view.getCreate().setOnAction(event -> {
      		//Enter method here;
        //	});

	}	
}

