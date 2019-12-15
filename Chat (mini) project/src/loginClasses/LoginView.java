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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginView extends View<LoginModel> {
	
	// Labels for Login
	private Label lblUsername;
	private Label lblPassword;
	private TextField txtUsername;
	private PasswordField txtPassword;
	private Button btnLogin;
	private Button btnCreatacc;
	
	// Menu label
	private Menu languageMenu;
	private Menu helpMenu;
     
	// Create account stuff
	private Stage createAccountPopup;
	private Button create;
	private Label lblCreateUsername;
	private Label lblCreatePassword;
	private TextField txtCreateUsername;
	private PasswordField txtCreatePassword;
	private Button btnCreateCreatacc;

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
		btnCreatacc = new Button();
		
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
	    gridPane.add(btnCreatacc,1,3);
	    
		return gridPane;
	}
	
	public void updateText() {
		// ehm okey -> need to understand before exam
		Translator trans = ServiceLocator.getServiceLocator().getTranslator();
		
		//Set reference to Trnaslation
		//LoginScreen
		lblUsername.setText(trans.getString("program.login.Username"));
		lblPassword.setText(trans.getString("program.login.Password"));
		btnLogin.setText(trans.getString("program.login.Login"));
		btnCreatacc.setText(trans.getString("program.login.Createacc"));
		
		//Menut text
		languageMenu.setText(trans.getString("program.menu.file.language"));
		helpMenu.setText(trans.getString("program.menu.help"));
	}
	
	private MenuBar createMenuBar()  {
		// Soource: https://o7planning.org/de/11125/anleitung-javafx-menu
		
		//Create menubar
		MenuBar menu = new MenuBar();
				
		//Menus without text, text is set in update method
		languageMenu = new Menu();
		helpMenu = new Menu();
		        
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
	
	// Create popup to create an account
	public void createAccountPopup() {
		createAccountPopup = new Stage();
		createAccountPopup.initModality(Modality.APPLICATION_MODAL);
		
		GridPane popupAccount = new GridPane();
		popupAccount.setId("Create an Account");
		
		createAccountPopup.setResizable(false);
		createAccountPopup.setHeight(250);
        createAccountPopup.setWidth(300);
		popupAccount.setPadding(new Insets(10,10,10,10));
		popupAccount.setHgap(10);
		popupAccount.setVgap(10);
        popupAccount.setAlignment(Pos.CENTER);
		
		lblCreateUsername = new Label();
		lblCreatePassword = new Label();
		txtCreateUsername = new TextField();
		txtCreatePassword = new PasswordField();
		create = new Button();
		
		popupAccount.add(lblCreateUsername,0,0);
		popupAccount.add(txtCreateUsername,1,0);
		popupAccount.add(lblCreatePassword,0,1);
		popupAccount.add(txtCreatePassword,1,1);
		popupAccount.add(create,1,2);
		
		//Update the labels
		updateCreateAccount();
		
		Scene popupAccountScene = new Scene(popupAccount);
		createAccountPopup.setScene(popupAccountScene);
		createAccountPopup.showAndWait();
		
	}
	
	public void updateCreateAccount() {
		// ehm okey -> need to understand before exam
		Translator trans = ServiceLocator.getServiceLocator().getTranslator();
		
		//Reference to file
		lblCreateUsername.setText(trans.getString("program.login.Username"));
		lblCreatePassword.setText(trans.getString("program.login.Password"));
		create.setText(trans.getString("program.login.Createacc"));
	}
	
	//getter for button, mram
	public Button getLoginButton() {
		return btnLogin;
	}
	
	public Button getCreateAccButton() {
		return btnCreatacc;
	}
	
	public Button getCreate() {
		return create;
	}


}
