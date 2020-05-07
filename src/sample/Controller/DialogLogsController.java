package sample.Controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Model.DB_Read.TicketLogData;
import sample.Model.DataTickets;
import sample.Model.TicketLogs;

import java.io.IOException;

public class DialogLogsController {

    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
    @FXML
    private TableView<TicketLogs> tableLogData;

    @FXML
    private TableColumn<TicketLogs, String> field;

    @FXML
    private TableColumn<TicketLogs, String> oldValue;

    @FXML
    private TableColumn<TicketLogs, String> newValue;

    @FXML
    private Button okButton;

    DataTickets dataTickets = new DataTickets();
    TicketLogData ticketLogData = new TicketLogData();
    @FXML
    public void initialize() {
        ticketLogData.logDataRead(EditTicketController.idLogs);


        field.setCellValueFactory(new PropertyValueFactory<>("field"));
        oldValue.setCellValueFactory(new PropertyValueFactory<>("oldValue"));
        newValue.setCellValueFactory(new PropertyValueFactory<>("newValue"));
        oldValue.setCellFactory(param -> {
            TableCell<TicketLogs,String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.textProperty().bind(cell.itemProperty());
            text.wrappingWidthProperty().bind(oldValue.widthProperty());
            return cell;
        });
        newValue.setCellFactory(param -> {
            TableCell<TicketLogs,String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.textProperty().bind(cell.itemProperty());
            text.wrappingWidthProperty().bind(oldValue.widthProperty());
            return cell;
        });
        tableLogData.setItems(ticketLogData.getTicketLogsData());
    }

    @FXML
    public void okButtonCloseDLogs(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
