package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Enum.User;
import sample.Model.DataTickets;
import sample.Model.Print;
import sample.Model.Status;
import sample.Model.TicketLogs;

import java.io.IOException;
import java.sql.SQLException;

public class EditTicketController {

    public static int idLogs;

    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    @FXML
    private Label numberTicket;
    @FXML
    private TextField numberTicketText;
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
    private TextArea defect;
    @FXML
    private TextArea note;
    @FXML
    private TextArea condition;
    @FXML
    private TextArea comment;
    @FXML
    private Button saveButton;
    @FXML
    private Label status;
    @FXML
    private Label date;
    @FXML
    private Label dateClose;
    @FXML
    private ComboBox<Status> statusComboBox;
    @FXML
    private TableView<TicketLogs> tableLogs;
    @FXML
    private TableColumn<TicketLogs, Integer> numberLog;
    @FXML
    private TableColumn<TicketLogs, String> dateLog;
    @FXML
    private SplitPane editTicketViewPane;

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

        numberLog.setCellValueFactory(new PropertyValueFactory<>("idLog"));
        dateLog.setCellValueFactory(new PropertyValueFactory<>("dateLog"));

        numberTicket.setText(String.valueOf(dataTickets.getNumberTicket()));
        numberTicketText.setText(String.valueOf(dataTickets.getNumberTicket()));
        phone.setText(dataTickets.getPhoneNumber());
        fullName.setText(dataTickets.getFullName());
        device.setText(dataTickets.getDeviceTicket());
        model.setText(dataTickets.getModelTicket());
        defect.setText(dataTickets.getDefectTicket());
        note.setText(dataTickets.getNoteTicket());
        condition.setText(dataTickets.getConditionTicket());
        comment.setText(dataTickets.getCommentTicket());
        status.setText(dataTickets.getStatusTicket());
        date.setText(dataTickets.getDateCreateTicket());
        dateClose.setText(dataTickets.getDateCloseTicket());
        statusComboBox.setItems(dataTickets.getStatusUpload());
        tableLogs.setItems(dataTickets.getTicketLogs());

        saveButton.setDisable(true);

        numberTicketText.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){numberTicketText.setText(oldValue);}
            if(!dataTickets.allNumberTicket(StringToInt(numberTicketText.getText()))) {
                numberTicketText.setText(oldValue);
            }
            check();
        });
        phone.textProperty().addListener((observable, oldValue, newValue) -> check());
        fullName.textProperty().addListener((observable, oldValue, newValue) -> check());
        device.textProperty().addListener((observable, oldValue, newValue) -> check());
        model.textProperty().addListener((observable, oldValue, newValue) -> check());
        defect.textProperty().addListener((observable, oldValue, newValue) -> check());
        note.textProperty().addListener((observable, oldValue, newValue) -> check());
        condition.textProperty().addListener((observable, oldValue, newValue) -> check());
        comment.textProperty().addListener((observable, oldValue, newValue) -> check());
        statusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> check());

        tableLogs.setRowFactory(tv -> {
            TableRow<TicketLogs> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    int selectIndex = row.getIndex();
                    TicketLogs selectLog = tableLogs.getItems().get(selectIndex);
                    idLogs = selectLog.getIdLogFromDB();
                    mainMenuController.dialogLogs();
                }
            });
            return row;
        });

        switch (status.getText()){
            case "Диагностика":
                switch (User.USER){
                    case MASTER:  numberTicketText.setDisable(true);
                                  statusComboBox.getItems().remove(2,7);
                                  statusComboBox.getItems().remove(0);break;
                    case OPERATOR: numberTicketText.setDisable(true);
                                   statusComboBox.setDisable(true);break;
                };break;
            case "На согласовании":
                switch (User.USER){
                    case ADMIN:;break;
                    default: setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(5,7);
                        statusComboBox.getItems().remove(3);
                        statusComboBox.getItems().remove(0,2);break;

                };break;
            case "Согласованно":
                switch (User.USER){
                    case MASTER:  setEditableNumPhoNamDevModDefConNot();
                                  statusComboBox.getItems().remove(4,6);
                                  statusComboBox.getItems().remove(0,3);break;
                    case OPERATOR: setEditableNumPhoNamDevModDefConNot();
                                   statusComboBox.setDisable(true);break;
                };break;
            case "Готов":
                switch (User.USER){
                    case MASTER:  setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(0,6);
                        break;
                    case OPERATOR: setEditableNumPhoNamDevModDefConNot();break;
                };break;
            case "Отказ от ремонта":
                switch (User.USER){
                    case MASTER:  setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(4,6);
                        statusComboBox.getItems().remove(0,3);break;
                    case OPERATOR: setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.setDisable(true);break;
                };break;
            case "Готов к выдаче":
                switch (User.USER){
                    case ADMIN:;break;
                    default:setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(6);
                        statusComboBox.getItems().remove(0,5);break;
                };break;
            case "Выдан":
                switch (User.USER){
                    case ADMIN:;break;
                    default:setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.setDisable(true);break;
                };break;
        }
    }

    @FXML
    private void printed() throws IOException {
        Print print = new Print();
        print.printed();
    }

    private void check() {
        int numberTicketC =String.valueOf(dataTickets.getNumberTicket()).compareTo(numberTicketText.getText());
        int phoneC = dataTickets.getPhoneNumber().compareTo(phone.getText());
        int conditionC = dataTickets.getConditionTicket().compareTo(condition.getText());
        int fullNameC = dataTickets.getFullName().compareTo(fullName.getText());
        int deviceC = dataTickets.getDeviceTicket().compareTo(device.getText());
        int modelC = dataTickets.getModelTicket().compareTo(model.getText());
        int defectC = dataTickets.getDefectTicket().compareTo(defect.getText());
        int noteC = dataTickets.getNoteTicket().compareTo(note.getText());
        int commentC = dataTickets.getCommentTicket().compareTo(comment.getText());
        String statusD = "null";
        int statusC = statusD.compareTo(String.valueOf(statusComboBox.getValue()));
        if (numberTicketC == 0 && phoneC == 0 && conditionC == 0 && fullNameC == 0 && deviceC == 0 && modelC == 0 && defectC == 0 && noteC == 0 && statusC == 0 && commentC == 0) {
            saveButton.setDisable(true);
        } else {
            saveButton.setDisable(false);
        }
    }
    private int StringToInt(String inputString){
        try{
            return Integer.parseInt(inputString);
        }catch(NumberFormatException ex){
            return 0; // при пустой строке вернем "0"
        }
    }

    int idStatus;



    @FXML
    public void saveEditTicket() {
        /**
         * Проверка id статуса из массива getAllStatus()
         */
        int idStatus = 1;
        int idStatusTrue = 0;
        for(String s: dataTickets.getAllStatus()){
            if (!s.contains(String.valueOf(statusComboBox.getValue()))){
                idStatus++;
            }else{
                idStatusTrue =idStatus;
            }
        }
            if (statusComboBox.getSelectionModel().getSelectedIndex() + 1 != 0) {
                dataTickets.saveEditTicketWrite(dataTickets.getIdTicket(), idStatusTrue, phone.getText(), fullName.getText(), device.getText(), model.getText(),
                        defect.getText(), note.getText(), condition.getText(), comment.getText(), Integer.parseInt(numberTicketText.getText()));
            } else {
                dataTickets.saveEditTicketWrite(dataTickets.getIdTicket(), Integer.parseInt(dataTickets.getIdStatusTicket()), phone.getText(), fullName.getText(), device.getText(), model.getText(),
                        defect.getText(), note.getText(), condition.getText(), comment.getText(), Integer.parseInt(numberTicketText.getText()));
            }
        String getStatusComboBox;

        if (statusComboBox.getValue() == null) {
            getStatusComboBox = dataTickets.getStatusTicket();
        } else {
            getStatusComboBox = String.valueOf(statusComboBox.getValue());
        }

        mainMenuController.ticketList();
        dataTickets.ticketLogsWrite(dataTickets.getIdTicket(),
                dataTickets.getPhoneNumber(),
                phone.getText(),
                dataTickets.getFullName(),
                fullName.getText(),
                dataTickets.getStatusTicket(),
                getStatusComboBox,
                dataTickets.getDeviceTicket(),
                device.getText(),
                dataTickets.getModelTicket(),
                model.getText(),
                dataTickets.getDefectTicket(),
                defect.getText(),
                dataTickets.getNoteTicket(),
                note.getText(),
                dataTickets.getConditionTicket(),
                condition.getText(),
                dataTickets.getCommentTicket(),
                comment.getText(),
                String.valueOf(dataTickets.getNumberTicket()),
                numberTicketText.getText());

    }
    @FXML
    public void printPreview(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/PrintPreview.fxml"));
            loader.setController(new PrintPreviewController());
            Parent pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setEditableNumPhoNamDevModDefConNot(){
        numberTicketText.setEditable(false);
        phone.setEditable(false);
        fullName.setEditable(false);
        device.setEditable(false);
        model.setEditable(false);
        defect.setEditable(false);
        condition.setEditable(false);
        note.setEditable(false);
        numberTicketText.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        phone.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        fullName.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        defect.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        device.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        model.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        condition.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        note.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");

    }

}
