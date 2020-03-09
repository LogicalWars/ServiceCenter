package sample.Controller;

import javafx.fxml.FXML;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model.*;
import sample.Model.Print.Print;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditTicketController {

    public static int idLogs;


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

        idTicket.setText(String.valueOf(dataTickets.getIdTicket()));
        phone.setText(dataTickets.getPhoneNumber());
        fullName.setText(dataTickets.getFullName());
        device.setText(dataTickets.getDeviceTicket());
        model.setText(dataTickets.getModelTicket());
        mark.setText(dataTickets.getMarkTicket());
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

        phone.textProperty().addListener((observable,oldValue,newValue) -> check());
        fullName.textProperty().addListener((observable,oldValue,newValue) ->check());
        device.textProperty().addListener((observable,oldValue,newValue) ->check());
        model.textProperty().addListener((observable,oldValue,newValue) ->check());
        mark.textProperty().addListener((observable,oldValue,newValue) ->check());
        defect.textProperty().addListener((observable,oldValue,newValue) ->check());
        note.textProperty().addListener((observable,oldValue,newValue) ->check());
        condition.textProperty().addListener((observable,oldValue,newValue) ->check());
        comment.textProperty().addListener((observable,oldValue,newValue) ->check());
        statusComboBox.valueProperty().addListener((observable, oldValue, newValue) ->check());



        tableLogs.setRowFactory(tv -> {
            TableRow<TicketLogs> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2 && (!row.isEmpty())){
                    int selectIndex = row.getIndex();
                    TicketLogs selectLog = tableLogs.getItems().get(selectIndex);
                    idLogs = selectLog.getIdLogFromDB();
                    mainMenuController.dialogLogs();
                }
            });
            return row;
        });



    }
    @FXML
    private void printed(){
        System.out.println("печать");
    }

    private void check(){
        int phoneC = dataTickets.getPhoneNumber().compareTo(phone.getText());
        int conditionC = dataTickets.getConditionTicket().compareTo(condition.getText());
        int fullNameC = dataTickets.getFullName().compareTo(fullName.getText());
        int deviceC = dataTickets.getDeviceTicket().compareTo(device.getText());
        int modelC = dataTickets.getModelTicket().compareTo(model.getText());
        int markC = dataTickets.getMarkTicket().compareTo(mark.getText());
        int defectC = dataTickets.getDefectTicket().compareTo(defect.getText());
        int noteC = dataTickets.getNoteTicket().compareTo(note.getText());
        int commentC = dataTickets.getCommentTicket().compareTo(comment.getText());
        String statusD = "null";
        int statusC =statusD.compareTo(String.valueOf(statusComboBox.getValue())) ;
        if(phoneC==0&&conditionC==0&&fullNameC==0&&deviceC==0&&modelC==0&&markC==0&&defectC==0&&noteC==0&&statusC==0&&commentC==0)
        {saveButton.setDisable(true);}else{saveButton.setDisable(false);}
    }

    public void saveEditTicket(){
        if(statusComboBox.getSelectionModel().getSelectedIndex()+1 !=0) {
            dataTickets.saveEditTicketWrite(dataTickets.getIdTicket(), statusComboBox.getSelectionModel().getSelectedIndex() + 1, phone.getText(), fullName.getText(), device.getText(), model.getText(),
                    mark.getText(), defect.getText(), note.getText(), condition.getText(), comment.getText());
        }else{
            dataTickets.saveEditTicketWrite(dataTickets.getIdTicket(), Integer.parseInt(dataTickets.getIdStatusTicket()), phone.getText(), fullName.getText(), device.getText(), model.getText(),
                    mark.getText(), defect.getText(), note.getText(), condition.getText(), comment.getText());
        }
        mainMenuController.ticketList();
//        dataTickets.ticketLogsWrite(dataTickets.getIdTicket(), dataTickets.getPhoneNumber(), phone.getText());
        dataTickets.ticketLogsWrite(dataTickets.getIdTicket(),
                                    dataTickets.getPhoneNumber(),
                                    phone.getText(),
                                    dataTickets.getFullName(),
                                    fullName.getText(),
                                    dataTickets.getStatusTicket(),
                                    String.valueOf(statusComboBox.getValue()),
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
                                    dataTickets.getMarkTicket(),
                                    mark.getText(),
                                    dataTickets.getCommentTicket(),
                                    comment.getText());
    }

}
