package sample.Controller;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;
import sample.Model.DataTickets;
import sample.Model.StockList;

import java.util.LinkedHashSet;
import java.util.Set;

public class StockListController {
    @FXML
    private TableView<StockList> tableStockList;

    @FXML
    private TableColumn<StockList, Integer> elementId;

    @FXML
    private TableColumn<StockList, String> model;

    @FXML
    private TableColumn<StockList, String> name;

    @FXML
    private TableColumn<StockList, Integer> amount;

    @FXML
    private TableColumn<StockList, Integer> price;

    @FXML
    private VBox editMenu;

    @FXML
    private TextField search;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField amountTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button updateButton;

    @FXML
    private Button saveButton;

    DataTickets dataTickets = new DataTickets();
    int selectIndex;
    int checkButton = 0;

    @FXML
    private void initialize(){

        editMenu.setDisable(true);



        dataTickets.stockListDataRead();
        priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            check();
            if(!newValue.matches("\\d*")){priceTextField.setText(oldValue);}
        });
        amountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            check();
            if(!newValue.matches("\\d*")){amountTextField.setText(oldValue);}
        });

        TextFields.bindAutoCompletion(modelTextField,searchModel(modelTextField.getText(), 0));

         modelTextField.textProperty().addListener((observable, oldValue, newValue) -> { check();});
         nameTextField.textProperty().addListener((observable, oldValue, newValue) -> { check();});
        search.textProperty().addListener((observable, oldValue, newValue) -> { search();});

        elementId.setCellValueFactory(new PropertyValueFactory<>("elementId"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableStockList.setItems(dataTickets.getStockListData());

        tableStockList.setRowFactory(tv -> {
            TableRow<StockList> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    selectIndex = row.getIndex() + 1;
                    editMenu.setDisable(false);
//                    System.out.println(modelComboBox.getValue());
                    nameTextField.setText(dataTickets.getStockListData().get(row.getIndex()).getName());
                    amountTextField.setText(String.valueOf(dataTickets.getStockListData().get(row.getIndex()).getAmount()));
                    priceTextField.setText(String.valueOf(dataTickets.getStockListData().get(row.getIndex()).getPrice()));
                    saveButton.setDisable(true);
                    checkButton = 2 ;
                    modelTextField.setText(dataTickets.getStockListData().get(row.getIndex()).getModel());
                }
            });
            return row;
        });

    }

    @FXML
    void SaveСhanges() {
        dataTickets.addNewElementWrite(modelTextField.getText(), nameTextField.getText(), Integer.valueOf(amountTextField.getText()), Integer.valueOf(priceTextField.getText()));
        editMenu.setDisable(true);
        tableStockList.getItems().clear();
        dataTickets.stockListDataRead();
        tableStockList.setItems(dataTickets.getStockListData());
        modelTextField.clear();
        nameTextField.clear();
        amountTextField.clear();
        priceTextField.clear();
    }


    @FXML
    void addNewElement() {
        editMenu.setDisable(false);
        updateButton.setDisable(true);
        saveButton.setDisable(true);
        modelTextField.clear();
        nameTextField.clear();
        amountTextField.clear();
        priceTextField.clear();
        search.clear();
        checkButton = 1;
    }


    @FXML
    void updateButton() {
        dataTickets.stockListWrite(dataTickets.getStockListData().get(selectIndex-1).getElementId(),modelTextField.getText(), nameTextField.getText(), Integer.valueOf(amountTextField.getText()), Integer.valueOf(priceTextField.getText()));
        tableStockList.getItems().clear();
        dataTickets.stockListDataRead();
        tableStockList.setItems(dataTickets.getStockListData());
    }

    private void checkStockNew(){
        if(amountTextField.getText().length() >0 && priceTextField.getText().length()>0 && nameTextField.getText().length()>0 && modelTextField.getText().length()>0){
            saveButton.setDisable(false);
        }else{
            saveButton.setDisable(true);
        }


    }
    private void checkStockUpdate(){
        if(amountTextField.getText().length() >0 && priceTextField.getText().length()>0 && nameTextField.getText().length()>0 && modelTextField.getText().length()>0){
            updateButton.setDisable(false);
        }else{
            updateButton.setDisable(true);
        }
    }
    private void check(){
        if (checkButton == 1){
            checkStockNew();
        }else if(checkButton == 2){
            checkStockUpdate();
        }
    }
    public ObservableList<StockList> searchModel(String text, int valid){
        int i=0;
        ObservableList<StockList> stockList = FXCollections.observableArrayList();
        if(valid == 1 ){
            for(String s: dataTickets.getModelList()){
            if(s.contains(text)){
                stockList.add(new StockList(dataTickets.getElementIdList().get(i), dataTickets.getModelList().get(i), dataTickets.getNameList().get(i), dataTickets.getAmountList().get(i), dataTickets.getPriceList().get(i)));
            }
            i++;}
        }else if(0 == valid){
            Set<String> set = new LinkedHashSet(dataTickets.getModelList());
                for(String s: set){
                    if(s.contains(text)){
                        stockList.add(new StockList(s));
                    }
                i++;
                }

            }
    return stockList;
    }

    private void search(){
        tableStockList.getItems().clear();
        tableStockList.setItems(searchModel(search.getText(), 1));
    }
}
