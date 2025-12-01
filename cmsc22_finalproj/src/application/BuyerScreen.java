package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Buyer;

public class BuyerScreen {

    private Scene buyerScene;

    public BuyerScreen(Stage stage, Buyer buyer) {

        BorderPane root = new BorderPane();
        root.getStyleClass().add("buyerscreen_initial");

        Scene scene = new Scene(root, 1024, 576);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
