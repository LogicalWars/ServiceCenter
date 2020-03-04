package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model.DataTickets;
import sample.Model.TicketLogs;

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

        tableLogData.setItems(dataTickets.getTicketLogsData());
    }

}
