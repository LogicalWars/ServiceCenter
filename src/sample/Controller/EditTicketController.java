package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model.DataTickets;
import sample.Model.EditTicket;
import sample.Model.Status;
import sample.Model.TicketLogs;

import java.sql.SQLException;

public class EditTicketController {

    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    @FXML
    private Label idTicket;
    @FXML
    private TextField phone;
    @FXML
    private TextField fullName;
    @FXML
    private TextField repairPrice;
    @FXML
    private TextField sparePart;
    @FXML
    private Label sparePrice;
    @FXML
    private Label totalPrice;
    @FXML
    private TextField device;
    @FXML
    private TextField model;
    @FXML
    private TextField mark;
    @FXML
    private TextArea defect;
    @FXML
    private TextArea note;
    @FXML
    private TextArea condition;
    @FXML
    private Label status;
    @FXML
    private Label date;
    @FXML
    private ComboBox<Status> statusUpload;
    @FXML
    private TableView<TicketLogs> tableLogs;
    @FXML
    private TableColumn<TicketLogs, Integer> numberLog;
    @FXML
    private TableColumn<TicketLogs, String> dateLog;

    DataTickets dataTickets = new DataTickets();

    @FXML
    public void initialize() {

        try {
            dataTickets.editTicketRead(TicketListController.idRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataTickets.statusUploadRead();
        dataTickets.ticketLogsRead(dataTickets.getIdTicket());

        numberLog.setCellValueFactory(new PropertyValueFactory<TicketLogs, Integer>("idLog"));
        dateLog.setCellValueFactory(new PropertyValueFactory<TicketLogs, String>("dateLog"));

        idTicket.setText(String.valueOf(dataTickets.getIdTicket()));
        phone.setText(dataTickets.getPhoneNumber());
        fullName.setText(dataTickets.getFullName());
        device.setText(dataTickets.getDeviceTicket());
        model.setText(dataTickets.getModelTicket());
        mark.setText(dataTickets.getMarkTicket());
        defect.setText(dataTickets.getDefectTicket());
        note.setText(dataTickets.getNoteTicket());
        condition.setText(dataTickets.getConditionTicket());
        status.setText(dataTickets.getStatusTicket());
        date.setText(dataTickets.getDateCreateTicket());

        statusUpload.setItems(dataTickets.getStatusUpload());
        tableLogs.setItems(dataTickets.getTicketLogs());

    }

    public void saveEditTicket(){
        if(statusUpload.getSelectionModel().getSelectedIndex()+1 !=0) {
            dataTickets.saveEditTicketWrite(dataTickets.getIdTicket(), statusUpload.getSelectionModel().getSelectedIndex() + 1, phone.getText(), fullName.getText(), device.getText(), model.getText(),
                    mark.getText(), defect.getText(), note.getText(), condition.getText());
        }else{
            dataTickets.saveEditTicketWrite(dataTickets.getIdTicket(), Integer.parseInt(dataTickets.getIdStatusTicket()), phone.getText(), fullName.getText(), device.getText(), model.getText(),
                mark.getText(), defect.getText(), note.getText(), condition.getText());
        }
        mainMenuController.ticketList();
        dataTickets.ticketLogsWrite(dataTickets.getIdTicket());
    }
}
