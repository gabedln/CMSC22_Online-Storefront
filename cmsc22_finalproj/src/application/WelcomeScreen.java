package application;

import user.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


public class WelcomeScreen {
	
	private Scene welcome;
	
	public WelcomeScreen(Stage stage, Scene previous, User user) {
		Text welcomeLabel;
		Button continueButton;
		Button back = new Button("go back");
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				stage.setScene(previous);
			}
		});
		
		BorderPane root = new BorderPane();
		root.getStyleClass().add("welcome-border-pane");
		Scene welcome = new Scene(root, 1024, 576);
		welcome.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.welcome = welcome;
		
		if(user instanceof Buyer) {
			welcomeLabel = new Text("WELCOME, BUYER " + user.getDisplayName() + "!");
			continueButton = new Button("start shopping");
		} else {
			welcomeLabel = new Text("WELCOME, SELLER " + user.getDisplayName() + "!");
			continueButton = new Button("start selling");
			continueButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent arg0) {
					SellerScreen sellerscreen = new SellerScreen(stage, (Seller)user);
					stage.setScene(sellerscreen.getScene());
				}
			});
		}
		
		welcomeLabel.getStyleClass().add("welcome-text");
		continueButton.setMinWidth(350);
		back.setMinWidth(350);
		back.getStyleClass().add("back-button-main");
		continueButton.getStyleClass().add("continue-button");
		
		
		HBox buttons = new HBox(10, continueButton, back);
		buttons.setStyle("-fx-padding: 0 0 10 80");
		
		VBox show = new VBox(10, welcomeLabel, buttons);
		show.setStyle("-fx-padding: 400 40 20 65;");
		show.setAlignment(Pos.CENTER);
		root.setCenter(show);
	}
	
	public Scene getScene() { return this.welcome; }
}
