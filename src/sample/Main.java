package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.MainMenuController;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View/MainMenu.fxml"));
            loader.setController(new MainMenuController());
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
            primaryStage.setMinHeight(640);
            primaryStage.setMinWidth(920);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
