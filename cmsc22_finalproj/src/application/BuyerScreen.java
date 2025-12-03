package application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import user.Buyer;
import user.User;

public class BuyerScreen {

	private Scene buyerScene;

	public BuyerScreen(Stage stage, Buyer buyer) {
		BorderPane root = new BorderPane();
		
		Image userIcon = new Image(getClass().getResourceAsStream("/application/images/user_icon.png"));
		
		ImageView userIconView = new ImageView(userIcon);
		userIconView.setFitHeight(45);
		userIconView.setFitWidth(45);
		Button userButton = new Button();
		userButton.setGraphic(userIconView);
		userButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
		
		userButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				UserInformation userInfo = new UserInformation(stage, buyerScene, (User)buyer);
				stage.setScene(userInfo.getScene());
			}
		});
		
		HBox topBar = new HBox(userButton);
		topBar.setAlignment(Pos.TOP_RIGHT);
		topBar.setStyle("-fx-padding: 10 20 0 0;");

		root.getStyleClass().add("buyerscreen");

		Scene scene = new Scene(root, 1024, 576);
		scene.getStylesheets().add(
				getClass().getResource("application.css").toExternalForm()
		);
		this.buyerScene = scene;

		root.setTop(topBar);
	}

	public Scene getScene() {
		return this.buyerScene;
	}
}
