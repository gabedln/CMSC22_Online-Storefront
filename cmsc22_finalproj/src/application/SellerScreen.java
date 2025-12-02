package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Seller;

public class SellerScreen {
	
	private Scene seller;
	
	public SellerScreen(Stage stage, Seller seller) {
		Button startSelling = new Button("start selling");
		startSelling.setMinWidth(450);
		startSelling.getStyleClass().add("startSelling-button");
		
		BorderPane root = new BorderPane();
		Scene sellerScene = new Scene(root, 1024, 576);
		sellerScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.seller = sellerScene;
		
		VBox button = new VBox(startSelling);
		button.setStyle("-fx-padding: 0 0 120 15;");
		button.setAlignment(Pos.CENTER);
		
		if(seller.getProducts().size()==0) {
			root.getStyleClass().add("sellerscreen_initial");
			root.setBottom(button);
		}
	}
	
	public Scene getScene() { return this.seller; }
	
	
}
