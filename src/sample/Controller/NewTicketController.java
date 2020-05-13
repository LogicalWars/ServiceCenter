package sample.Controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;
import sample.Model.DB_Read.ListOfStock;
import sample.Model.DB_Read.NewTicketData;
import sample.Model.DataTickets;
import sample.Model.HotKeys;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;


public class NewTicketController {

    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
    @FXML
    private SplitPane paneNewTicket;
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

    ListOfStock listOfStock = new ListOfStock();
    NewTicketData newTicketData = new NewTicketData();

    @FXML
    public void initialize() {

        listOfStock.stockListDataRead();
        Set<String> set = new LinkedHashSet(listOfStock.getModelList());
        TextFields.bindAutoCompletion(textModel,set);

        newTicketData.newTicketListRead();
        Set<Integer> setPhone = new LinkedHashSet(newTicketData.getPhoneNumberList());
        TextFields.bindAutoCompletion(textPhone,setPhone);
        Set<String> setDevice = new LinkedHashSet(newTicketData.getDeviceList());
        TextFields.bindAutoCompletion(textDevice,setDevice);


        textPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){textPhone.setText(oldValue);}
            int idPhoneNumber = 0;
            for(String s : newTicketData.getPhoneNumberList()){
                if(newValue.equals(s)){
                    textFullName.setText(newTicketData.getFullNameList().get(idPhoneNumber));
                    break;
                }else{textFullName.setText("");}
                idPhoneNumber++;
            }
        });


    }
    @FXML
    public void createNewTicket(){
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

}
