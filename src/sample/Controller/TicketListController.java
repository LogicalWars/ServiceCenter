package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model.DataTickets;
import sample.Model.Tickets;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                    System.out.println(idRow);
                    mainMenuController.editTicket();
                }
            });

            return row;
        });
        tableTickets.setItems(dataTickets.getTicketsData());



    }


    public void search(String text){
        int i=0;
        int y=0;
        int z=0;
        ObservableList<Tickets> list = FXCollections.observableArrayList();
        Set<Tickets> l = new HashSet<>();
        list.clear();
        l.clear();
        l.addAll(searchArray(dataTickets.getIdSeachList(),text));
        l.addAll(searchArray(dataTickets.getPhoneSeachList(),text));
        l.addAll(searchArray(dataTickets.getFullNameSeachList(),text));
        list.addAll(l);
        Collections.sort(list);
        tableTickets.getItems().clear();
        tableTickets.setItems(list);

    }
    private Set<Tickets> searchArray (List<String> list, String text){
        Set<Tickets> l = new HashSet<>();
        int z=0;
        for(String f: list){
            if(f.contains(text)){
                l.add(new Tickets(Integer.parseInt(dataTickets.getIdSeachList().get(z)),dataTickets.getPhoneSeachList().get(z), dataTickets.getFullNameSeachList().get(z),dataTickets.getDateSeachList().get(z),dataTickets.getStatusSeachList().get(z)));
            }
            z++;
        }
        return l;
    }


}
