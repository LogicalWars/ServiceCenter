package sample.Controller;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Enum.User;
import sample.Model.DBProcessor;
import sample.Model.DB_Read.TicketLogData;
import sample.Model.DataTickets;
import sample.Model.HotKeys;

import javax.management.ListenerNotFoundException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class MainMenuController {

    @FXML
    private BorderPane paneMainContent;
    @FXML
    private Label infoLeft;
    @FXML
    private Label user;
    @FXML
    private Button logOffButton;
    @FXML
    private MenuItem stockButton;
    @FXML
    private MenuItem userButton;
    @FXML
    private Button createNewTicketButton;
    @FXML
    public TextField searchTextField;
    @FXML
    private HBox searchToolBox;


    TicketListController ticketListController;


    @FXML
    public void initialize() {

        ticketList();
        infoLeft.setText(new DBProcessor().resultConnection());
        user.setText(User.loginUser);
        if(User.USER == User.ADMIN || User.USER == User.MASTER) {
            stockButton.setDisable(false);
        }else{stockButton.setDisable(true);}

        if(User.USER == User.ADMIN) {
            userButton.setDisable(false);
        }else{userButton.setDisable(true);}

        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
        ticketListController.search(newValue);
         });

        ImageView iconsAdd = new ImageView("sample/View/icons/add.png");
        iconsAdd.setFitHeight(20);
        iconsAdd.setFitWidth(20);
        createNewTicketButton.graphicProperty().setValue(iconsAdd);

    }





    public void a(){    System.out.println("left");}
public void b(){    System.out.println("right");}
    @FXML
    void logOff(){
        Stage stage = (Stage) logOffButton.getScene().getWindow();
        stage.close();
        User.USER = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/LoginView.fxml"));
            loader.setController(new LogInController());
            Parent pane = loader.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        searchToolBox.setVisible(false);
    }

    @FXML
    public void ticketList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/TicketListView.fxml"));
            loader.setController(new TicketListController());
            Parent newPane = loader.load();
            paneMainContent.setCenter(newPane);
            ticketListController = loader.getController();
            ticketListController.setMainMenuController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        searchToolBox.setVisible(true);
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
        searchToolBox.setVisible(false);
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
            dialogStage.setTitle(new TicketLogData().getDateTimeLogRead(EditTicketController.idLogs) + "  ");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void editPrintPattern(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EditPatternPrint.fxml"));
            loader.setController(new EditPatternPrintController());
            Parent pane = loader.load();
            EditPatternPrintController editPatternPrintController = loader.getController();
            editPatternPrintController.setMainMenuController(this);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void userList(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/UserListView.fxml"));
            loader.setController(new UserListController());
            Parent pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void stockList() {
            stockButton.setDisable(false);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/StockListView.fxml"));
                loader.setController(new StockListController());
                Parent pane = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(pane);
                dialogStage.setScene(scene);
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
