package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import user.User;

public class UserInformation {
    private Scene userInfoScene;

    public UserInformation(Stage stage, Scene previous, User user) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("user-info");

        Scene userInfoScene = new Scene(root, 1024, 576);
        userInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        this.userInfoScene = userInfoScene;

        // ---------------- Labels ----------------
        Label title = new Label("USER'S ACCOUNT INFORMATION");
        title.getStyleClass().add("welcome-text");

    Label displayName = new Label("display name: " + user.getDisplayName());
    Label username = new Label("username: @" + user.getUsername());
    Label location = new Label("location: " + user.getLocation());
    Label accountType = new Label("account type: " + user.getAccountType());
    Label balance = new Label("balance: ₱" + user.getBalance());


        // ---------------- Edit Icons ----------------
        Image editIcon = new Image(getClass().getResourceAsStream("/application/images/edit_icon.png"));
        ImageView editNameIcon = new ImageView(editIcon);
        editNameIcon.setFitHeight(20);
        editNameIcon.setFitWidth(20);

        ImageView editLocationIcon = new ImageView(editIcon);
        editLocationIcon.setFitHeight(20);
        editLocationIcon.setFitWidth(20);

        Button editNameButton = new Button();
        editNameButton.setGraphic(editNameIcon);
        editNameButton.getStyleClass().add("edit-icon-button");
        
        Button editLocationButton = new Button();
        editLocationButton.setGraphic(editLocationIcon);
        editLocationButton.getStyleClass().add("edit-icon-button");


        // ---------------- Edit Actions ----------------
        editNameButton.setOnAction(e -> {
            TextField nameField = new TextField();
            nameField.setPromptText("Enter new display name");
            nameField.getStyleClass().add("text-field");

            Button confirm = new Button("confirm");
            confirm.getStyleClass().add("add-button");
            confirm.setOnAction(ev -> {
                user.setDisplayName(nameField.getText());
                stage.setScene(new UserInformation(stage, previous, user).getScene());
            });


            GridPane editPane = new GridPane();
            editPane.setAlignment(Pos.CENTER);
            editPane.setVgap(10);
            editPane.add(nameField, 0, 0);
            editPane.add(confirm, 0, 1);
            root.setCenter(editPane);
        });

        editLocationButton.setOnAction(e -> {
            TextField locationField = new TextField();
            locationField.setPromptText("Enter new location");
            locationField.getStyleClass().add("text-field");

            Button confirm = new Button("confirm");
            confirm.getStyleClass().add("add-button");
            confirm.setOnAction(ev -> {
                user.setLocation(locationField.getText());
                stage.setScene(new UserInformation(stage, previous, user).getScene());
            });


            GridPane editPane = new GridPane();
            editPane.setAlignment(Pos.CENTER);
            editPane.setVgap(10);
            editPane.add(locationField, 0, 0);
            editPane.add(confirm, 0, 1);
            root.setCenter(editPane);
        });

        // ---------------- Layout ----------------
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setVgap(15);
        grid.setStyle("-fx-padding: 100 0 0 100;");

        grid.add(title, 0, 0);
        grid.add(displayName, 0, 1);
        grid.add(editNameButton, 1, 1);
        grid.add(username, 0, 2);
        grid.add(location, 0, 3);
        grid.add(editLocationButton, 1, 3);
        grid.add(accountType, 0, 4);
        grid.add(balance, 0, 5);

        Button back = new Button("back");
        back.getStyleClass().add("back-button");
        back.setOnAction(e -> stage.setScene(previous));
        grid.add(back, 0, 6);

        root.setCenter(grid);
    }

    public Scene getScene() {
        return this.userInfoScene;
    }
}
