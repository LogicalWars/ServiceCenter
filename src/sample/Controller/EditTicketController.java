package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Model.EditTicket;

public class EditTicketController {

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


    public EditTicketController(){}
    public void initialize() {

//        EditTicket editTicket = null;
//        phone.setText(String.valueOf(editTicket.getPhoneNumber()));

    }

}
