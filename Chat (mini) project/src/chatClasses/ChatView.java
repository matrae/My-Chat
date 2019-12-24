package chatClasses;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatView extends View<ChatModel> {
	
	// Labels for Chat
	private Button btnSend;
	private TextArea txtMessage;
	
	// Menu label
	private Menu languageMenu;
	private Menu helpMenu;
	private Menu settings;
	private MenuItem logout;
	private MenuItem setIP;
	
	public ChatView(Stage stage, ChatModel model) {
		super(stage, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Scene create_GUI() {
		
		ServiceLocator serviceL = ServiceLocator.getServiceLocator();
		
		
		VBox basis = new VBox();
		HBox roomsChat = new HBox();
		roomsChat.getChildren().addAll(createChatrooms(), createMessSend());
		basis.getChildren().addAll(createMenuBar(), roomsChat);
		
	
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
		scene = new Scene(basis);
		updateText();
		return scene;
	}
	
	// Display the Messages in a List maybe
	private HBox createMessageView() {
		return null;
	}
	
	// Display all Chatrooms on the Server 	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Delete button and chagne
	private Button createChatrooms() {
		btnSend = new Button();
		return btnSend;
	}
	
	// Returns a HBox with a text field and a send button
	private HBox createMessSend() {
		// Create buttons, and textfields
		btnSend = new Button();
		txtMessage = new TextArea();
		
		//BOTTOM: Textfield and messages
		HBox messSend = new HBox();
		messSend.getChildren().addAll(txtMessage, btnSend);		
		
		return messSend;
	}	

	private void updateText() {
		// ehm okey -> need to understand before exam
		Translator trans = ServiceLocator.getServiceLocator().getTranslator();
		
		//Set reference to Trnaslation
		//LoginScreen
		btnSend.setText(trans.getString("program.chat.send"));
		
		//Menutext
		languageMenu.setText(trans.getString("program.menu.file.language"));
		helpMenu.setText(trans.getString("program.menu.help"));
		settings.setText(trans.getString("program.menu.settings"));
		logout.setText(trans.getString("program.menu.logout"));
		setIP.setText(trans.getString("program.menu.setIP"));
	}
	
	private MenuBar createMenuBar() {
		// Soource: https://o7planning.org/de/11125/anleitung-javafx-menu
		
		//Create menubar
		MenuBar menu = new MenuBar();
				
		//Menus without text, text is set in update method
		languageMenu = new Menu();
		helpMenu = new Menu();
		settings = new Menu();
		
        // Create MenuItems
		logout = new MenuItem();
        setIP = new MenuItem();
        
        // Add menuItems to the Menus
        settings.getItems().addAll(setIP, logout);
		        
		//Add menus to menubar
		menu.getMenus().addAll(languageMenu, helpMenu, settings);
		        
		return menu;
	}

}
