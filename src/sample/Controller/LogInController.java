package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Enum.User;
import sample.Model.DataTickets;

import java.io.IOException;

public class LogInController {

    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorPass;

    @FXML
    public void logInButton() {
        User.getUserRole(user.getText(), password.getText());
        if (User.USER != null) {
            Stage stage = (Stage) user.getScene().getWindow();
            stage.close();


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainMenu.fxml"));
                loader.setController(new MainMenuController());
                Parent pane = loader.load();
                Stage primaryStage = new Stage();
                Scene scene = new Scene(pane);
                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.setMinHeight(640);
                primaryStage.setMinWidth(920);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            errorPass.setVisible(true);
        }
    }

}
