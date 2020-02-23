package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.Model.DBProcessor;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private BorderPane paneMainContent;
    @FXML
    private Label infoLeft;

    @FXML
    public void initialize () {
        ticketList();
        infoLeft.setText(new DBProcessor().resultConnection());
    }

    @FXML
    public void newTicket() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/NewTicketView.fxml"));
            loader.setController(new NewTicketController());
            Parent newPane = loader.load();
            paneMainContent.setCenter(newPane);
            NewTicketController newTicketController = loader.getController();
            newTicketController.setMainMenuController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ticketList () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/TicketListView.fxml"));
            loader.setController(new TicketListController());
            Parent newPane = loader.load();
            paneMainContent.setCenter(newPane);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editTicket () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EditTicketView.fxml"));
            loader.setController(new EditTicketController());
            Parent newPane = loader.load();
            paneMainContent.setCenter(newPane);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
