package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class StockListController {
    @FXML
    private TableView<?> tableStockList;

    @FXML
    private TableColumn<?, ?> elementId;

    @FXML
    private TableColumn<?, ?> model;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> amount;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private VBox editMenu;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField amountTextField;

    @FXML
    private TextField priceTextField1;

    @FXML
    void SaveСhanges() {

    }

    @FXML
    void addNewElement() {

    }


}
