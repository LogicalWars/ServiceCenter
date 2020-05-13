package sample.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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
    private Button logInButton;
    @FXML
    public void initialize(){
        password.setText("1");
        user.setText("1");

        KeyCodeCombination kComb = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHIFT_DOWN);
        logInButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                   logInButton();
                }
            }
        });

    }
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
