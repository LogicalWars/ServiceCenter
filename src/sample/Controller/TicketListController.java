package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model.DataTickets;
import sample.Model.Tickets;

public class TicketListController {

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


    @FXML
    private void initialize(){
        DataTickets dataTickets = new DataTickets();
        dataTickets.dataTicketsRead();

        idTicket.setCellValueFactory            (new PropertyValueFactory<Tickets, Integer>("idTicket"));
        phoneNumber.setCellValueFactory         (new PropertyValueFactory<Tickets, String>("phoneNumber"));
        fullName.setCellValueFactory            (new PropertyValueFactory<Tickets, String>("fullName"));
        dateCreateTicket.setCellValueFactory    (new PropertyValueFactory<Tickets, String>("dateCreateTicket"));
        statusTicket.setCellValueFactory        (new PropertyValueFactory<Tickets, String>("statusTicket"));

        tableTickets.setItems(dataTickets.getTicketsData());
    }
}
