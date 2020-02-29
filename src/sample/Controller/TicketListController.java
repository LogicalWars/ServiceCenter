package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model.DataTickets;
import sample.Model.Tickets;

import java.sql.SQLException;

public class TicketListController {

    public static int idRow;
    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    @FXML
    private TableView<Tickets> tableTickets;
    @FXML
    private TableColumn<Tickets, Integer> idTicket;
    @FXML
    private TableColumn<Tickets, String> phoneNumber;
    @FXML
    private TableColumn<Tickets, String> fullName;
    @FXML
    private TableColumn<Tickets, String> dateCreateTicket;
    @FXML
    private TableColumn<Tickets, String> statusTicket;

    DataTickets dataTickets = new DataTickets();

    @FXML
    private void initialize() {

        dataTickets.dataTicketsRead();

        idTicket.setCellValueFactory(new PropertyValueFactory<>("idTicket"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dateCreateTicket.setCellValueFactory(new PropertyValueFactory<>("dateCreateTicket"));
        statusTicket.setCellValueFactory(new PropertyValueFactory<>("statusTicket"));

        tableTickets.setRowFactory(tv -> {
            TableRow<Tickets> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    int selectIndex = row.getIndex();
                    Tickets selectTickets = tableTickets.getItems().get(selectIndex);
                    idRow = selectTickets.getIdTicket();
                    mainMenuController.editTicket();
                }
            });

            return row;
        });
        tableTickets.setItems(dataTickets.getTicketsData());
    }

}
