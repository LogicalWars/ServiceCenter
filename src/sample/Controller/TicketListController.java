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

import java.util.HashSet;

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


    public ObservableList<Tickets> search(String text){
        int i=0;
        ObservableList<Tickets> list0 = FXCollections.observableArrayList();
        ObservableList<Tickets> list = FXCollections.observableArrayList();
        ObservableList<Tickets> list1 = FXCollections.observableArrayList();
        ObservableList<Tickets> list2 = FXCollections.observableArrayList();
        for(String s: dataTickets.getPhoneSeachList() ){
            if(s.contains(text)){
                list0.add(new Tickets(Integer.parseInt(dataTickets.getIdSeachList().get(i)),dataTickets.getPhoneSeachList().get(i), dataTickets.getFullNameSeachList().get(i),dataTickets.getDateSeachList().get(i),dataTickets.getStatusSeachList().get(i)));
            }
            i++;}
        int y=0;
        for(String d: dataTickets.getIdSeachList()){
            if(d.contains(text)){
                list1.add(new Tickets(Integer.parseInt(dataTickets.getIdSeachList().get(y)),dataTickets.getPhoneSeachList().get(y), dataTickets.getFullNameSeachList().get(y),dataTickets.getDateSeachList().get(y),dataTickets.getStatusSeachList().get(y)));
            }
            y++;

        }
        int z=0;
        for(String f: dataTickets.getFullNameSeachList()){
            if(f.contains(text)){
                list1.add(new Tickets(Integer.parseInt(dataTickets.getIdSeachList().get(z)),dataTickets.getPhoneSeachList().get(z), dataTickets.getFullNameSeachList().get(z),dataTickets.getDateSeachList().get(z),dataTickets.getStatusSeachList().get(z)));
            }
            z++;
        }
        HashSet<Tickets> l = new HashSet<>();
        l.addAll(list0);
        l.addAll(list1);
        l.addAll(list2);
        list.addAll(l);
        tableTickets.getItems().clear();
        tableTickets.setItems(list);
        return list;
    }


}
