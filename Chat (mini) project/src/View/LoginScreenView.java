package View;


import Model.LoginScreenModel;
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

public class LoginScreenView {
	private LoginScreenModel model;
	public Stage stage;
	public Scene scene;
	
	public LoginScreenView(Stage stage, LoginScreenModel model) {
		// TODO Auto-generated constructor stub
		this.model = model;
		this.stage = stage;
		
	    //CREATE BorderPane and add GridPane and MENU BAR into it
	    BorderPane borderPane = new BorderPane();
	    borderPane.setCenter(createLoginGrid());
	    borderPane.setTop(createMenuBar());
	    stage.setResizable(false);
	    
	    
	    
	    //Create scene
	    Scene scene = new Scene(borderPane,400,250);
	    //Add scene to stage
	    stage.setScene(scene);
	    //Show the beautiful scene
	    stage.show();
	}
	
	private Pane createLoginGrid() {
		//Creating a grid pane
		GridPane gridPane = new GridPane();
		
		//Buttons, labels and textields
		Label lblUsername = new Label("Username");
		Label lblPassword = new Label("Password");
		TextField txtUsername = new TextField();
		PasswordField txtPassword = new PasswordField();
		Button btnLogin = new Button("Login");
		
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
	
	private MenuBar createMenuBar()  {
		// Soource: https://o7planning.org/de/11125/anleitung-javafx-menu
		
		//Create menubar
		MenuBar menu = new MenuBar();
		
		//Menus
        Menu languageMenu = new Menu("Language");
        Menu helpMenu = new Menu("Help");
       
        //Menuitems
        MenuItem english = new MenuItem("English");
        MenuItem german = new MenuItem("German");
        
        // Add menuItems to the Menus
        languageMenu.getItems().addAll(english, german);
        
        //Add menus to menubar
        menu.getMenus().addAll(languageMenu, helpMenu);
		
		return menu;
	}

}
