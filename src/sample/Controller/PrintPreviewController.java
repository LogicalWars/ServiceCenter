package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Model.DataTickets;

import java.io.IOException;
import java.sql.SQLException;

public class PrintPreviewController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    public void initialize() {
        dialogPrintPreview();
    }

    @FXML
    public void dialogPrintPreview() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/PrintTableView.fxml"));
            loader.setController(new PrintTableController());
            Parent newPane = loader.load();
            scrollPane.setContent(newPane);
            PrintTableController printTableController = loader.getController();
            printTableController.setPrintPreviewController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






}
