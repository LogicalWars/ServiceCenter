package sample.Controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;
import sample.Model.DataTickets;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;


public class NewTicketController {

    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
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
    private TextArea textDefect;
    @FXML
    private TextArea textNote;
    @FXML
    private TextArea textCondition;
    @FXML
    private Button buttonSave;

    @FXML
    public void createNewTicket() throws SQLException {
        if (textPhone.getLength() > 0) {
            if (textFullName.getLength() > 0) {
                new DataTickets().createNewTicketWrite(textPhone.getText(), textFullName.getText(), textDevice.getText(), textModel.getText(), textDefect.getText(), textNote.getText(), textCondition.getText());
                mainMenuController.ticketList();
            } else {
                textFullName.setPromptText("Заполните поле");
            }
        } else {
            textPhone.setPromptText("Заполните поле");
        }

    }
    DataTickets dataTickets = new DataTickets();
    @FXML
    public void initialize() {

        dataTickets.stockListDataRead();
        Set<String> set = new LinkedHashSet(dataTickets.getModelList());
        TextFields.bindAutoCompletion(textModel,set);

        dataTickets.newTicketListRead();
        Set<Integer> setPhone = new LinkedHashSet(dataTickets.getPhoneNumberList());
        TextFields.bindAutoCompletion(textPhone,setPhone);
        Set<String> setDevice = new LinkedHashSet(dataTickets.getDeviceList());
        TextFields.bindAutoCompletion(textDevice,setDevice);


        textPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){textPhone.setText(oldValue);}
            int idPhoneNumber = 0;
            for(String s : dataTickets.getPhoneNumberList()){
                if(newValue.equals(s)){
                    textFullName.setText(dataTickets.getFullNameList().get(idPhoneNumber));
                    break;
                }else{textFullName.setText("");}
                idPhoneNumber++;
            }

        });

    }

}
