package loginClasses;

import App.ChatApp;
import abstractClasses.Controller;
import abstractClasses.View;
import javafx.concurrent.Worker;

public class LoginController extends Controller<LoginModel, LoginView>{

	public LoginController(final ChatApp main, LoginModel model, LoginView view) {
		super(model, view);
		// TODO Auto-generated constructor stub
      	
      	view.getLoginButton().setOnAction(event -> {
      		main.startApp();
      	});
      	
      	view.getCreateAccButton().setOnAction(ecent -> {
      		view.createAccountPopup();
      	});

	}	
}

