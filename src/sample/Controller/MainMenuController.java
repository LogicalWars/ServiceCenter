package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Model.DBProcessor;
import sample.Model.DataTickets;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private BorderPane paneMainContent;
    @FXML
    private Label infoLeft;

    @FXML
    public void initialize() {
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
    public void ticketList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/TicketListView.fxml"));
            loader.setController(new TicketListController());
            Parent newPane = loader.load();
            paneMainContent.setCenter(newPane);
            TicketListController ticketListController = loader.getController();
            ticketListController.setMainMenuController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void editTicket() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EditTicketView.fxml"));
            loader.setController(new EditTicketController());
            Parent newPane = loader.load();
            paneMainContent.setCenter(newPane);
            EditTicketController editTicketController = loader.getController();
            editTicketController.setMainMenuController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void dialogLogs() {
        DataTickets dataTickets = new DataTickets();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/DialogLogsView.fxml"));
            loader.setController(new DialogLogsController());
            Parent pane = loader.load();
            DialogLogsController dialogLogsController = loader.getController();
            dialogLogsController.setMainMenuController(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle(dataTickets.getDateTimeLogRead(EditTicketController.idLogs));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
