package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;
import sample.Model.DB_Read.ListOfStock;
import sample.Model.DataTickets;
import sample.Model.StockList;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
    ListOfStock listOfStock = new ListOfStock();
    int selectIndex;
    int checkButton = 0;

    @FXML
    private void initialize(){

        editMenu.setDisable(true);
        listOfStock.stockListDataRead();
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
        search.textProperty().addListener((observable, oldValue, newValue) -> {search();});

        elementId.setCellValueFactory(new PropertyValueFactory<>("elementId"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableStockList.setItems(listOfStock.getStockListData());

        tableStockList.setRowFactory(tv -> {
            TableRow<StockList> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    selectIndex = row.getIndex() + 1;
                    editMenu.setDisable(false);
                    nameTextField.setText(tableStockList.getSelectionModel().getSelectedItem().getName());
                    amountTextField.setText(String.valueOf(tableStockList.getSelectionModel().getSelectedItem().getAmount()));
                    priceTextField.setText(String.valueOf(tableStockList.getSelectionModel().getSelectedItem().getPrice()));
                    saveButton.setDisable(true);
                    checkButton = 2 ;
                    modelTextField.setText(tableStockList.getSelectionModel().getSelectedItem().getModel());
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
        listOfStock.stockListDataRead();
        tableStockList.setItems(listOfStock.getStockListData());
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
        listOfStock.stockListDataRead();
        dataTickets.stockListWrite(listOfStock.getStockListData().get(selectIndex-1).getElementId(),modelTextField.getText(), nameTextField.getText(), Integer.valueOf(amountTextField.getText()), Integer.valueOf(priceTextField.getText()));
        tableStockList.getItems().clear();
        listOfStock.stockListDataRead();
        tableStockList.setItems(listOfStock.getStockListData());
        editMenu.setDisable(true);
        modelTextField.clear();
        nameTextField.clear();
        amountTextField.clear();
        priceTextField.clear();
    }
//Проверка для включения кнопок Сохранить(valid = 0)/Обновить
    private  boolean checkButton(int valid){
    List<String> nameList = new ArrayList<>();
    int i=0;
    boolean checkButton = true;
//заполнение листа именами
    for(String m: listOfStock.getModelList()){
        if(modelTextField.getText().equals(m)){
            nameList.add(listOfStock.getNameList().get(i));
        }
        i++;
    }
    if(valid == 0){
//проверка уникального имени
    for(String name: nameList){
        if(name.equals(nameTextField.getText())){
            checkButton = false;
        }

    }
    }else{
//проверка уникального имени без того, которое собираемся обновить
    nameList.remove(tableStockList.getSelectionModel().getSelectedItem().getName());
        for(String name: nameList){
            if(name.equals(nameTextField.getText())){
                checkButton = false;
            }

        }
    }

    return checkButton;
}

    private void checkStockNew(){
        if(amountTextField.getText().length() >0 && priceTextField.getText().length()>0 && nameTextField.getText().length()>0 && modelTextField.getText().length()>0){
            if(checkButton(0)){
                saveButton.setDisable(false);
            }else{
                saveButton.setDisable(true);
            }
        }else{
            saveButton.setDisable(true);
        }
    }
    private void checkStockUpdate(){
        if(amountTextField.getText().length() >0 && priceTextField.getText().length()>0 && nameTextField.getText().length()>0 && modelTextField.getText().length()>0){
            if(checkButton(1)){
                updateButton.setDisable(false);
            }else{
                updateButton.setDisable(true);
            }
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
            for(String s: listOfStock.getModelList()){
            if(s.contains(text)){
                stockList.add(new StockList(listOfStock.getElementIdList().get(i), listOfStock.getModelList().get(i), listOfStock.getNameList().get(i), listOfStock.getAmountList().get(i), listOfStock.getPriceList().get(i)));

            }
            i++;
            }
            return stockList;
        }else if(0 == valid){
            System.out.println("valid = 0");
            Set<String> set = new LinkedHashSet(listOfStock.getModelList());
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
