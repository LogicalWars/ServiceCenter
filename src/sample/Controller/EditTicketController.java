package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Model.DataTickets;
import sample.Model.EditTicket;

import java.sql.SQLException;

public class EditTicketController {

    private MainMenuController mainMenuController;
    public void setMainMenuController(MainMenuController mainMenuController){
        this.mainMenuController = mainMenuController;
    }

    @FXML
    public Label idTicket;
    @FXML
    public TextField phone;
    @FXML
    private TextField fullName;
    @FXML
    private TextField device;
    @FXML
    private TextField model;



    public void initialize() {
        try {
            new DataTickets().editTicketRead(TicketListController.idRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idTicket.setText(String.valueOf(111111111));
        phone.setText("89888787342");


    }

}
