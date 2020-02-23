package sample.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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

        tableTickets.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    TablePosition pos = (TablePosition) tableTickets.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();
                    TableColumn col = pos.getTableColumn();
                    String data = (String) col.getCellObservableValue(tableTickets.getItems().get(row)).getValue();
                    System.out.println(data);
                }
            } });

        tableTickets.setItems(dataTickets.getTicketsData());
    }
}
