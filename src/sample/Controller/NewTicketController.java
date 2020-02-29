package sample.Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Model.DBProcessor;
import sample.Model.DataTickets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;


public class NewTicketController{

    private MainMenuController mainMenuController;
    public void setMainMenuController(MainMenuController mainMenuController){
        this.mainMenuController = mainMenuController;
    }

    @FXML
    public TextField textPhone;
    @FXML
    private TextField textFullName;
    @FXML
    private TextField textDevice;
    @FXML
    private TextField textModel;
    @FXML
    private TextField textMark;
    @FXML
    private TextArea textDefect;
    @FXML
    private TextArea textNote;
    @FXML
    private TextArea textCondition;
    @FXML
    private Button buttonSave;

    @FXML
    public void createNewTicket() throws SQLException {
        if(textPhone.getLength()>0){
            if(textFullName.getLength()>0){
                new DataTickets().createNewTicketWrite(textPhone.getText(), textFullName.getText(), textDevice.getText(), textModel.getText(), textDefect.getText(), textNote.getText(), textCondition.getText(), textMark.getText());
                mainMenuController.ticketList();
            }else{
                textFullName.setPromptText("Заполните поле");}
        }else{
              textPhone.setPromptText("Заполните поле");
        }

    }



    /**НЕОБХОДИМО ВВЕСТИ ДОБАВИТЬ В ЛОГИКУ ДАННЫЙ МЕТОД**/
    public void textColumns(){
        String inputPriceStr = textPhone.getText();
        int length = textPhone.getText().length();
        final int MAX = 10;
        if (inputPriceStr.matches("[0-9]*") && length < MAX ) {
        }
    }
}
