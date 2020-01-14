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
	private Label lblWrongInput;
	
	// Menu label
	private Menu languageMenu;
	private Menu helpMenu;
	private MenuItem google;
     
	// Create account stuff
	private Stage createAccountPopup;
	private Button create;
	private Label lblCreateUsername;
	private Label lblCreatePassword;
	private Label lblRepeatPassword;
	private Label lblInstructions; 
	private TextField txtCreateUsername;
	private PasswordField txtCreatePassword;
	private PasswordField txtRepeatPassword;
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
		
		//new label for
		lblWrongInput = new Label();
		
		//Add paddings to pane
		gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        
        //Merge column span of txt fields
        GridPane.setColumnSpan(txtPassword, 2);
        GridPane.setColumnSpan(txtUsername, 2);
        GridPane.setColumnSpan(lblWrongInput, 3);
		
		//Adding all buttons, labels, txt fields to grid Pane
	    gridPane.add(lblUsername,0,0);
	    gridPane.add(txtUsername,1,0);
	    gridPane.add(lblPassword,0,1);
	    gridPane.add(txtPassword,1,1);
	    gridPane.add(lblWrongInput,1,2);
	    gridPane.add(btnLogin,1,3);
	    gridPane.add(btnCreatacc,2,3);
	    
	    lblWrongInput.setVisible(model.getState());
	    
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
		lblWrongInput.setText(trans.getString("program.login.WrongInput"));
		lblWrongInput.setVisible(model.getState());
		
		
		//Menut text
		languageMenu.setText(trans.getString("program.menu.file.language"));
		helpMenu.setText(trans.getString("program.menu.help"));
		google.setText(trans.getString("program.menu.google"));
	}
	
	private MenuBar createMenuBar()  {
		// Soource: https://o7planning.org/de/11125/anleitung-javafx-menu
		
		//Create menubar
		MenuBar menu = new MenuBar();
				
		//Menus without text, text is set in update method
		languageMenu = new Menu();
		helpMenu = new Menu();
		google = new MenuItem();
		
		helpMenu.getItems().add(google);
		
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
	    
		scene = new Scene(borderPane,600,350);
		scene.getStylesheets().addAll(this.getClass().getResource("login.css").toExternalForm());
		updateText();
	
		return scene;
		
	}
	
	private GridPane popupAccount;
	// Create popup to create an account
	public void createAccountPopup() {
		createAccountPopup = new Stage();
		createAccountPopup.initModality(Modality.APPLICATION_MODAL);
		
		popupAccount = new GridPane();
		popupAccount.setId("Create an Account");
		
		createAccountPopup.setResizable(false);
		createAccountPopup.setHeight(350);
        createAccountPopup.setWidth(400);
		popupAccount.setPadding(new Insets(10,10,10,10));
		popupAccount.setHgap(20);
		popupAccount.setVgap(20);
        popupAccount.setAlignment(Pos.CENTER);
		
        lblInstructions	= new Label();
		lblCreateUsername = new Label();
		lblCreatePassword = new Label();
		lblRepeatPassword = new Label();
		txtCreateUsername = new TextField();
		txtCreatePassword = new PasswordField();
		txtRepeatPassword = new PasswordField();
		create = new Button();
		
		lblInstructions.setWrapText(true);
		lblInstructions.setMinHeight(90);

		
		GridPane.setColumnSpan(lblInstructions, 2);
		popupAccount.add(lblInstructions,0,0);
		popupAccount.add(lblCreateUsername,0,1);
		popupAccount.add(txtCreateUsername,1,1);
		popupAccount.add(lblCreatePassword,0,2);
		popupAccount.add(txtCreatePassword,1,2);
		popupAccount.add(lblRepeatPassword,0,3);
		popupAccount.add(txtRepeatPassword,1,3);
		popupAccount.add(create,1,4);
		
		//Update the labels
		updateCreateAccount();
		
		Scene popupAccountScene = new Scene(popupAccount);
		popupAccount.getStylesheets().addAll(this.getClass().getResource("login.css").toExternalForm());
		createAccountPopup.setScene(popupAccountScene);
		createAccountPopup.show();
		
	}
	
	public void updateCreateAccount() {
		// ehm okey -> need to understand before exam
		Translator trans = ServiceLocator.getServiceLocator().getTranslator();
		
		//Reference to file
		lblCreateUsername.setText(trans.getString("program.login.Username"));
		lblCreatePassword.setText(trans.getString("program.login.Password"));
		lblRepeatPassword.setText(trans.getString("program.login.RepeatPassword"));
		lblInstructions.setText(trans.getString("program.login.instruction"));
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
	
	public Stage getPopupStage() {
		return createAccountPopup;
	}
	
	public MenuItem getGoogle() {
		return google;
	}
	
	public String getCreateUsername() {
        String username = txtCreateUsername.getText();
        return username;
	}
	
    public String getCreatePassword() {
        String password = txtCreatePassword.getText();
        return password;
    }
    
	public String getRepeatPassword() {
		String passwordR = txtRepeatPassword.getText();
		return passwordR;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
        String username = txtUsername.getText();
        return username;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
        String password = txtPassword.getText();
        return password;
	}
}
