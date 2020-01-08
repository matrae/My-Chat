package chatClasses;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import App.ServiceLocator;
import abstractClasses.View;
import commonClasses.Translator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChatView extends View<ChatModel> {
	
	// Labels for Chat
	private Button btnSend;
	private TextField txtMessage;
	
	// Menu label
	private Menu languageMenu;
	private Menu helpMenu;
	private Menu settings;
	private MenuItem logout;
	private MenuItem setIP;
	
	// Chatroom List
	private ListView<String> lvChatRooms;
	private VBox chatRoomHolder;
	private Button joinChatroom;
	private Button createChatroom;
	private Button deleteChatroom;
	
	// Popup Chatroom creation
	private Stage createPopupChatroom;
	private Button defCreateChatroom;
	private Label infoCreateChatroom;
	private TextField txtChatroomName;
	
	// Chat window for messages
	private ListView<String> lvMessages;
	private VBox messageHolder;
	
	public ChatView(Stage stage, ChatModel model) {
		super(stage, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator serviceL = ServiceLocator.getServiceLocator();
		
		VBox basis = new VBox();
		VBox messText = new VBox();
		HBox roomsChat = new HBox();
		roomsChat.setPrefWidth(400);
		messText.setPrefWidth(600);
		
		messText.getChildren().addAll(createMessageView(), createMessSend());
		roomsChat.getChildren().addAll(createChatrooms(), messText);
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
		scene = new Scene(basis, 1000, 800);
		scene.getStylesheets().addAll(this.getClass().getResource("chat.css").toExternalForm());
		updateText();
		return scene;
	}
	
	// Display the Messages in a List maybe
	private VBox createMessageView() {
		
		lvMessages = new ListView();
		lvMessages.setPrefHeight(800);
		messageHolder = new VBox();
		messageHolder.setPadding(new Insets(10,10,10,10));
		messageHolder.getChildren().addAll(lvMessages);
		
		return messageHolder;
	}
	

	
	// Display all Chatrooms on the Server 
	private VBox createChatrooms() {
		//display the Chatrooms from the server
		
		joinChatroom = new Button();
		createChatroom = new Button();
		deleteChatroom = new Button();
		
		chatRoomHolder = new VBox();
		//We could use an observable list
		//obsLChatRooms = FXCollections.observableArrayList()
		//Create a list view
		lvChatRooms = new ListView();
		lvChatRooms.setPrefHeight(800);
		
		HBox roomButtons = new HBox();
		roomButtons.setPadding(new Insets(10,10,10,10));
		roomButtons.setSpacing(20);
		
		roomButtons.getChildren().addAll(joinChatroom,createChatroom,deleteChatroom);
	
		chatRoomHolder.getChildren().addAll(roomButtons, lvChatRooms);
		chatRoomHolder.setPrefWidth(400);
	   	return chatRoomHolder;
	}
	
	// When creating a new chatroom
	public void createChatroomPopup() {
		// TODO Auto-generated method stub

		createPopupChatroom = new Stage();
		createPopupChatroom.initModality(Modality.APPLICATION_MODAL);
		
		VBox popupChatroom = new VBox();
		popupChatroom.setSpacing(20);
		popupChatroom.setId("CreateChatroom");
		
		createPopupChatroom.setResizable(false);
		createPopupChatroom.setHeight(250);
		createPopupChatroom.setWidth(250);
        popupChatroom.setPadding(new Insets(10,10,10,10));
        popupChatroom.setAlignment(Pos.CENTER);
		
        infoCreateChatroom	= new Label();
        txtChatroomName = new TextField();
        defCreateChatroom = new Button();
        
        infoCreateChatroom.setWrapText(true);
        
        popupChatroom.getChildren().addAll(infoCreateChatroom, txtChatroomName, defCreateChatroom);
		
		//Update the labels
		updateCreateChatroom();
		
		Scene popupChatroomScene = new Scene(popupChatroom);
		popupChatroom.getStylesheets().addAll(this.getClass().getResource("chat.css").toExternalForm());
		createPopupChatroom.setScene(popupChatroomScene);
		createPopupChatroom.show();
		
	}
	
	private void updateCreateChatroom() {
		// TODO Auto-generated method stub
		Translator trans = ServiceLocator.getServiceLocator().getTranslator();
		
		//Reference to file
		infoCreateChatroom.setText(trans.getString("program.chat.infoCreateChatroom"));
		defCreateChatroom.setText(trans.getString("program.chat.createChatroom"));
	}

	// Returns a HBox with a text field and a send button
	private HBox createMessSend() {
		
		//Create Image for Send button
		ImageView icon = new ImageView();
		Image image = new Image("/chatClasses/send.png");
		icon.setImage(image);
		icon.setFitWidth(25);
		icon.setFitHeight(25);
		
		// Create buttons, and textfields
		btnSend = new Button("",icon);
		btnSend.setId("SendButton");
		txtMessage = new TextField();
		txtMessage.setPrefHeight(40);
		txtMessage.setPrefWidth(575);
		
		//BOTTOM: Textfield and messages
		HBox messSend = new HBox();
		
		messSend.setPadding(new Insets(10,10,10,10));
		messSend.getChildren().addAll(txtMessage, btnSend);
		
		return messSend;
	}	

	private void updateText() {
		Translator trans = ServiceLocator.getServiceLocator().getTranslator();
		
		//Set reference to Trnaslation
		//LoginScreen
		//tnSend.setText(trans.getString("program.chat.send"));
		
		//Menutext
		languageMenu.setText(trans.getString("program.menu.file.language"));
		helpMenu.setText(trans.getString("program.menu.help"));
		settings.setText(trans.getString("program.menu.settings"));
		logout.setText(trans.getString("program.menu.logout"));
		setIP.setText(trans.getString("program.menu.setIP"));
		joinChatroom.setText(trans.getString("program.chat.joinChatroom"));
		createChatroom.setText(trans.getString("program.chat.createChatroom"));
		deleteChatroom.setText(trans.getString("program.chat.deleteChatroom"));	
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

	public Button getbtnSend() {
		return btnSend;
	}
	
	public String getTxtMessage() {
		String message = txtMessage.getText();
	    return message;
	}
	
	public ListView<String> getChatRoomListview() {
		return lvChatRooms;
	}
	
	public ListView<String> getMessagesListview() {
		return lvMessages;
	}

	public Button getJoinChatroom() {
		return joinChatroom;
	}

	public Button getCreateChatroom() {
		return createChatroom;
	}

	public Button getDeleteChatroom() {
		return deleteChatroom;
	}
	
	public Button getdefCreateChatroom() {
		return defCreateChatroom;
	}
	
	public Stage getCreateChatroomPopupStage() {
		return createPopupChatroom;
	}

	public String getChatroomName() {
		String chatroom = txtChatroomName.getText();
	    return chatroom;
	}
}
