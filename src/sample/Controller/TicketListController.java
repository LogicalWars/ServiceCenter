package sample.Controller;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.ls.LSOutput;
import sample.Model.DataTickets;
import sample.Model.Tickets;

import java.sql.SQLException;

public class TicketListController {

    private MainMenuController mainMenuController;
    public void setMainMenuController(MainMenuController mainMenuController){
        this.mainMenuController = mainMenuController;
    }

//    private int idRow;
//    public int getIdRow() {return idRow;}

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
    private void initialize() {
        DataTickets dataTickets = new DataTickets();
        dataTickets.dataTicketsRead();

        idTicket.setCellValueFactory            (new PropertyValueFactory<Tickets, Integer>("idTicket"));
        phoneNumber.setCellValueFactory         (new PropertyValueFactory<Tickets, String>("phoneNumber"));
        fullName.setCellValueFactory            (new PropertyValueFactory<Tickets, String>("fullName"));
        dateCreateTicket.setCellValueFactory    (new PropertyValueFactory<Tickets, String>("dateCreateTicket"));
        statusTicket.setCellValueFactory        (new PropertyValueFactory<Tickets, String>("statusTicket"));

        tableTickets.setRowFactory( tv -> {
            TableRow<Tickets> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    int selectIndex = row.getIndex();
                    Tickets selectTickets = (Tickets)tableTickets.getItems().get(selectIndex);
                    dataTickets.setIdRow(selectTickets.getIdTicket());
                    System.out.println(selectTickets.getIdTicket());
                    mainMenuController.editTicket();
                    try {
                        dataTickets.editTicketRead();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        tableTickets.setItems(dataTickets.getTicketsData());
    }
}
