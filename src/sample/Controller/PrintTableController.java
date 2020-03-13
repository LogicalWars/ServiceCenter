package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.Model.DataTickets;

import java.sql.SQLException;

public class PrintTableController {
    @FXML
    private Label nameTicket;
    @FXML
    private Label phone;
    @FXML
    private Label fullName;
    @FXML
    private Label device;
    @FXML
    private Label model;
    @FXML
    private Label defect;
    @FXML
    private Label date;
    @FXML
    private Label note;
    @FXML
    private Label condition;
    @FXML
    private Label nameTicket1;
    @FXML
    private Label phone1;
    @FXML
    private Label fullName1;
    @FXML
    private Label device1;
    @FXML
    private Label model1;
    @FXML
    private Label defect1;
    @FXML
    private Label date1;
    @FXML
    private Label note1;
    @FXML
    private Label condition1;

    @FXML
    private Label titleLabel;
    @FXML
    private Label orderLabel;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label noteLabel;
    @FXML
    private Label defectLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label deviceLabel;
    @FXML
    private Label modelLabel;
    @FXML
    private Label conditionLabel;
    @FXML
    private Label rulesLabel;
    @FXML
    private Label signAcceptLabel;
    @FXML
    private Label signPassedLabel;

    @FXML
    private Label titleLabel1;
    @FXML
    private Label orderLabel1;
    @FXML
    private Label fullNameLabel1;
    @FXML
    private Label phoneLabel1;
    @FXML
    private Label noteLabel1;
    @FXML
    private Label defectLabel1;
    @FXML
    private Label dateLabel1;
    @FXML
    private Label deviceLabel1;
    @FXML
    private Label modelLabel1;
    @FXML
    private Label conditionLabel1;
    @FXML
    private Label rulesLabel1;
    @FXML
    private Label signAcceptLabel1;
    @FXML
    private Label signPassedLabel1;
    private PrintPreviewController printPreviewController;
    public void setPrintPreviewController(PrintPreviewController printPreviewController) {
        this.printPreviewController = printPreviewController;
    }

    @FXML
    public void initialize() {
        DataTickets dataTickets = new DataTickets();
        try {
            dataTickets.editTicketRead(TicketListController.idRow);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        nameTicket.setText(String.valueOf(dataTickets.getIdTicket()));
        fullName.setText(dataTickets.getFullName());
        phone.setText(dataTickets.getPhoneNumber());
        note.setText(dataTickets.getNoteTicket());
        date.setText(dataTickets.getDateCreateTicket());
        device.setText(dataTickets.getDeviceTicket());
        defect.setText(dataTickets.getDefectTicket());
        model.setText(dataTickets.getModelTicket());
        condition.setText(dataTickets.getConditionTicket());

        nameTicket1.setText(String.valueOf(dataTickets.getIdTicket()));
        fullName1.setText(dataTickets.getFullName());
        phone1.setText(dataTickets.getPhoneNumber());
        note1.setText(dataTickets.getNoteTicket());
        date1.setText(dataTickets.getDateCreateTicket());
        device1.setText(dataTickets.getDeviceTicket());
        defect1.setText(dataTickets.getDefectTicket());
        model1.setText(dataTickets.getModelTicket());
        condition1.setText(dataTickets.getConditionTicket());


        dataTickets.editPatternPrintRead();
        titleLabel.setText(dataTickets.getPrintPatternData().get(0));
        rulesLabel.setText(dataTickets.getPrintPatternData().get(1));
        signAcceptLabel.setText(dataTickets.getPrintPatternData().get(2));
        signPassedLabel.setText(dataTickets.getPrintPatternData().get(3));
        orderLabel.setText(dataTickets.getPrintPatternData().get(4));
        fullNameLabel.setText(dataTickets.getPrintPatternData().get(5));
        noteLabel.setText(dataTickets.getPrintPatternData().get(6));
        dateLabel.setText(dataTickets.getPrintPatternData().get(7));
        deviceLabel.setText(dataTickets.getPrintPatternData().get(8));
        modelLabel.setText(dataTickets.getPrintPatternData().get(9));
        conditionLabel.setText(dataTickets.getPrintPatternData().get(10));
        defectLabel.setText(dataTickets.getPrintPatternData().get(11));

        titleLabel1.setText(dataTickets.getPrintPatternData().get(0));
        rulesLabel1.setText(dataTickets.getPrintPatternData().get(1));
        signAcceptLabel1.setText(dataTickets.getPrintPatternData().get(2));
        signPassedLabel1.setText(dataTickets.getPrintPatternData().get(3));
        orderLabel1.setText(dataTickets.getPrintPatternData().get(4));
        fullNameLabel1.setText(dataTickets.getPrintPatternData().get(5));
        noteLabel1.setText(dataTickets.getPrintPatternData().get(6));
        dateLabel1.setText(dataTickets.getPrintPatternData().get(7));
        deviceLabel1.setText(dataTickets.getPrintPatternData().get(8));
        modelLabel1.setText(dataTickets.getPrintPatternData().get(9));
        conditionLabel1.setText(dataTickets.getPrintPatternData().get(10));
        defectLabel1.setText(dataTickets.getPrintPatternData().get(11));

    }

}
