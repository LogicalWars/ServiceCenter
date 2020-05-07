package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.Model.DB_Read.PatternPrint;
import sample.Model.DB_Read.TicketData;
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
    PatternPrint patternPrint =  new PatternPrint();
    @FXML
    public void initialize() {
        DataTickets dataTickets = new DataTickets();
        TicketData ticketData = new TicketData();
        try {
            ticketData.editTicketRead(TicketListController.idRow);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        nameTicket.setText(String.valueOf(ticketData.getNumberTicket()));
        fullName.setText(ticketData.getFullName());
        phone.setText(ticketData.getPhoneNumber());
        note.setText(ticketData.getNoteTicket());
        date.setText(ticketData.getDateCreateTicket());
        device.setText(ticketData.getDeviceTicket());
        defect.setText(ticketData.getDefectTicket());
        model.setText(ticketData.getModelTicket());
        condition.setText(ticketData.getConditionTicket());

        nameTicket1.setText(String.valueOf(ticketData.getNumberTicket()));
        fullName1.setText(ticketData.getFullName());
        phone1.setText(ticketData.getPhoneNumber());
        note1.setText(ticketData.getNoteTicket());
        date1.setText(ticketData.getDateCreateTicket());
        device1.setText(ticketData.getDeviceTicket());
        defect1.setText(ticketData.getDefectTicket());
        model1.setText(ticketData.getModelTicket());
        condition1.setText(ticketData.getConditionTicket());


        patternPrint.editPatternPrintRead();
        titleLabel.setText(patternPrint.getPrintPatternData().get(0));
        rulesLabel.setText(patternPrint.getPrintPatternData().get(1));
        signAcceptLabel.setText(patternPrint.getPrintPatternData().get(2));
        signPassedLabel.setText(patternPrint.getPrintPatternData().get(3));
        orderLabel.setText(patternPrint.getPrintPatternData().get(4));
        fullNameLabel.setText(patternPrint.getPrintPatternData().get(5));
        phoneLabel.setText(patternPrint.getPrintPatternData().get(6));
        noteLabel.setText(patternPrint.getPrintPatternData().get(7));
        dateLabel.setText(patternPrint.getPrintPatternData().get(8));
        deviceLabel.setText(patternPrint.getPrintPatternData().get(9));
        modelLabel.setText(patternPrint.getPrintPatternData().get(10));
        conditionLabel.setText(patternPrint.getPrintPatternData().get(11));
        defectLabel.setText(patternPrint.getPrintPatternData().get(12));

        titleLabel1.setText(patternPrint.getPrintPatternData().get(0));
        rulesLabel1.setText(patternPrint.getPrintPatternData().get(1));
        signAcceptLabel1.setText(patternPrint.getPrintPatternData().get(2));
        signPassedLabel1.setText(patternPrint.getPrintPatternData().get(3));
        orderLabel1.setText(patternPrint.getPrintPatternData().get(4));
        fullNameLabel1.setText(patternPrint.getPrintPatternData().get(5));
        phoneLabel.setText(patternPrint.getPrintPatternData().get(6));
        noteLabel1.setText(patternPrint.getPrintPatternData().get(7));
        dateLabel1.setText(patternPrint.getPrintPatternData().get(8));
        deviceLabel1.setText(patternPrint.getPrintPatternData().get(9));
        modelLabel1.setText(patternPrint.getPrintPatternData().get(10));
        conditionLabel1.setText(patternPrint.getPrintPatternData().get(11));
        defectLabel1.setText(patternPrint.getPrintPatternData().get(12));

    }

}
