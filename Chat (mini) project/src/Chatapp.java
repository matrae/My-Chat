import Controller.LoginScreenController;
import Model.LoginScreenModel;
import View.LoginScreenView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Chatapp extends Application{
	
	LoginScreenModel model;
	LoginScreenView view;
	LoginScreenController controller;
	
	public static void main(String[] args) {
		 launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Create MVC model for login screen
	   	model = new LoginScreenModel();
	   	view = new LoginScreenView(stage, model);
	   	controller = new LoginScreenController(model, view);
	   	//test
	}
}
