package sample.Controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
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

    DataTickets dataTickets = new DataTickets();

    @FXML
    public void initialize() {

        dataTickets.logDataRead(EditTicketController.idLogs);

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
        tableLogData.setItems(dataTickets.getTicketLogsData());
    }

}
