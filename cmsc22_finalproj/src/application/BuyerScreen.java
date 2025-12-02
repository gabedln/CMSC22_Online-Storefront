package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Buyer;
import java.util.ArrayList;

public class BuyerScreen {

	private Scene buyerScene;

	public BuyerScreen(Stage stage, Buyer buyer) {
		Image userIcon = new Image(getClass().getResourceAsStream("/application/images/user_icon.png"));
		ImageView usericon = new ImageView(userIcon);
		usericon.setFitHeight(45);
		usericon.setFitWidth(45);
		
		Button userButton = new Button();
		userButton.setGraphic(usericon);
		userButton.setStyle("-fx-background-color: transparent; -fx-padding: 10 0 0 960;");

		BorderPane root = new BorderPane();

		// temporary list (replace later with real product list)
		ArrayList<String> products = new ArrayList<>();

		// CHECK IF THERE ARE PRODUCTS
		if(products.isEmpty()) {
			root.getStyleClass().add("buyerscreen_initial");
		}else {
			root.getStyleClass().add("buyerscreen_with_products");
		}

		Scene scene = new Scene(root, 1024, 576);
		scene.getStylesheets().add(
				getClass().getResource("application.css").toExternalForm()
		);
		this.buyerScene = scene;

		VBox placeholder = new VBox(); 
		placeholder.setStyle("-fx-padding: 0 0 120 15;");
		placeholder.setAlignment(Pos.CENTER);

		root.setBottom(placeholder);
	}

	public Scene getScene() {
		return this.buyerScene;
	}
}
