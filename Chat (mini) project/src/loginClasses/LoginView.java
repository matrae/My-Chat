package loginClasses;

import java.util.Locale;

import App.ServiceLocator;
import abstractClasses.View;
import commonClasses.Translator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginView extends View<LoginModel> {
	
	// Labels for Login
	private Label lblUsername;
	private Label lblPassword;
	private TextField txtUsername;
	private PasswordField txtPassword;
	private Button btnLogin;
	
	// Menu label
	private Menu languageMenu;
     

	public LoginView(Stage stage, LoginModel model) {
		super(stage, model);
		// TODO Auto-generated constructor stub
	}
		
	private Pane createLoginGrid() {
		//Creating a grid pane
		GridPane gridPane = new GridPane();
		
		//Buttons, labels and textields
		lblUsername = new Label();
		lblPassword = new Label();
		txtUsername = new TextField();
		txtPassword = new PasswordField();
		btnLogin = new Button();
		
		//Add paddings to pane
		gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
		
		//Adding all buttons, labels, txt fields to grid Pane
	    gridPane.add(lblUsername,0,0);
	    gridPane.add(txtUsername,1,0);
	    gridPane.add(lblPassword,0,1);
	    gridPane.add(txtPassword,1,1);
	    gridPane.add(btnLogin,1,2);
	    
		return gridPane;
	}
	
	public void updateText() {
		// ehm okey -> need to understand before exam
		Translator trans = ServiceLocator.getServiceLocator().getTranslator();
		
		//Set reference to Trnaslation
		lblUsername.setText(trans.getString("program.login.Username"));
		lblPassword.setText(trans.getString("program.login.Password"));
		btnLogin.setText(trans.getString("program.login.Login"));
	}
	
	private MenuBar createMenuBar()  {
		// Soource: https://o7planning.org/de/11125/anleitung-javafx-menu
		
		//Create menubar
		MenuBar menu = new MenuBar();
				
		//Menus
		languageMenu = new Menu("Language");
		Menu helpMenu = new Menu("Help");
		        
		//Add menus to menubar
		menu.getMenus().addAll(languageMenu, helpMenu);
		        
		return menu;
	}

	@Override
	protected Scene create_GUI() {
		// Understand
		ServiceLocator serviceL = ServiceLocator.getServiceLocator();
		
		BorderPane borderPane = new BorderPane();
	    borderPane.setCenter(createLoginGrid());
	    borderPane.setTop(createMenuBar());
	    stage.setResizable(false);
	    
	    // Source: Bradley App View
	    for (Locale locale : serviceL.getLocales()) {
            MenuItem language = new MenuItem(locale.getLanguage());
            languageMenu.getItems().add(language);
            language.setOnAction(event -> {
            	serviceL.getConfiguration().setLocalOption("Language", locale.getLanguage());
            	serviceL.setTranslator(new Translator(locale.getLanguage()));
            	updateText();
             
            });
        }
	    
		scene = new Scene(borderPane,400,250);
	
		updateText();
	
		return scene;
		
	}

}
