package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import sample.Model.DataTickets;
import sample.Model.Rules;
import sample.Model.UserData;

import java.util.ArrayList;

public class UserListController {
    @FXML
    private TableView<UserData> tableUserList;
    @FXML
    private TableColumn<UserData, Integer> userId;
    @FXML
    private TableColumn<UserData, String> name;
    @FXML
    private TableColumn<UserData, String> login;
    @FXML
    private TableColumn<UserData, String> password;
    @FXML
    private TableColumn<UserData, String> rules;
    @FXML
    private TableColumn<UserData, String> valid;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private VBox editMenu;
    @FXML
    private ComboBox<Rules> rulesComboBox;
    @FXML
    private CheckBox validCheckBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button updateButton;

    DataTickets dataTickets = new DataTickets();
    int selectIndex;
    int statusButton = 0;
    @FXML
    void initialize(){
        updateButton.setDisable(true);
        dataTickets.UserListDataRead();
        dataTickets.allRules();
        saveButton.setDisable(true);
        editMenu.setDisable(true);
        validCheckBox.setSelected(true);
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        rules.setCellValueFactory(new PropertyValueFactory<>("rules"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        valid.setCellValueFactory(new PropertyValueFactory<>("valid"));

        tableUserList.setItems(dataTickets.getUserListData());
        rulesComboBox.setItems(dataTickets.getAllRulesData());
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {test();});
        loginTextField.textProperty().addListener((observable, oldValue, newValue) -> {test();});
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {test();});
        rulesComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {test();});

        tableUserList.setRowFactory(tv -> {
            TableRow<UserData> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    statusButton = 1;
                    selectIndex = row.getIndex() + 1;
                    editMenu.setDisable(false);
                    loginTextField.setText(dataTickets.getUserListData().get(row.getIndex()).getLogin());
                    nameTextField.setText(dataTickets.getUserListData().get(row.getIndex()).getName());
                    passwordTextField.setText(dataTickets.getUserListData().get(row.getIndex()).getPassword());
                    Rules idR = null;
                    for(Rules s: dataTickets.getAllRulesData()){
                        String S = String.valueOf(s);
                        if(S.equals(dataTickets.getUserListData().get(row.getIndex()).getRules())){
                            idR = s;
                        }
                    }
                    rulesComboBox.setValue(idR);
                    boolean checkValid = true;
                    if(dataTickets.getUserListData().get(row.getIndex()).getValid().equals("Блокирован")) checkValid = false;
                    validCheckBox.setSelected(checkValid);
                }
            });
            return row;
        });

    }

    @FXML
    void saveСhanges() {
        int isValid = 0;
        if(validCheckBox.isSelected()) isValid = 1;
        dataTickets.addNewUser(nameTextField.getText(), loginTextField.getText(), passwordTextField.getText(), String.valueOf(rulesComboBox.getValue()), isValid);
        tableUserList.getItems().clear();
        dataTickets.UserListDataRead();
        tableUserList.setItems(dataTickets.getUserListData());
        editMenu.setDisable(true);
        nameTextField.clear();
        loginTextField.clear();
        passwordTextField.clear();
        rulesComboBox.getItems().clear();
        dataTickets.allRules();
        rulesComboBox.setItems(dataTickets.getAllRulesData());
        statusButton = 0;


    }

    @FXML
    void addNewUser() {
        statusButton = 2;
        editMenu.setDisable(false);
        nameTextField.clear();
        loginTextField.clear();
        passwordTextField.clear();
        rulesComboBox.getItems().clear();
        dataTickets.allRules();
        rulesComboBox.setItems(dataTickets.getAllRulesData());
        updateButton.setDisable(true);


    }
    @FXML
    private void userListWrite(){
        int isValid = 0;
        if(validCheckBox.isSelected()) isValid = 1;
        dataTickets.userListWrite(dataTickets.getUserListData().get(selectIndex-1).getUserId(),nameTextField.getText(),loginTextField.getText(), passwordTextField.getText(),String.valueOf(rulesComboBox.getValue()),isValid);
        tableUserList.getItems().clear();
        dataTickets.UserListDataRead();
        tableUserList.setItems(dataTickets.getUserListData());
        editMenu.setDisable(true);
        nameTextField.clear();
        loginTextField.clear();
        passwordTextField.clear();
        rulesComboBox.getItems().clear();
        dataTickets.allRules();
        rulesComboBox.setItems(dataTickets.getAllRulesData());
        statusButton = 0;
    }


    private void checkTextFields(){
        String rulesString = "null";
        int statusCheck = rulesString.compareTo(String.valueOf(rulesComboBox.getValue()));

        boolean checkLogin = true;
        for (String s: dataTickets.getLoginList()){
            if (s.equals(loginTextField.getText())){
                checkLogin = false;
            }
        }

        if(statusButton==2 && checkLogin && nameTextField.getText().length() >= 4 && loginTextField.getText().length() >=4 && passwordTextField.getText().length()>=6 && statusCheck != 0){
            saveButton.setDisable(false);
        }else{
            saveButton.setDisable(true);
        }
    }
    private void checkTextFieldUpdate(){

        if(nameTextField.getText().length() >= 4 && loginTextField.getText().length() >=4 && passwordTextField.getText().length()>=6){
            boolean checkLogin = true;
            String sDelete= null;
            ArrayList<String> loginListCheck = new ArrayList<>();
            loginListCheck.clear();
            for (String s:dataTickets.getLoginList()){
                loginListCheck.add(s);
            }

            for(String s:dataTickets.getLoginList() ){
                if(s.equals(dataTickets.getUserListData().get(selectIndex-1).getLogin())){
                    loginListCheck.remove(loginListCheck.indexOf(dataTickets.getUserListData().get(selectIndex-1).getLogin()));
                }
            }



                for (String s: loginListCheck){
                    if (s.equals(loginTextField.getText())){
                        checkLogin = false;
                    }
                }
                if(checkLogin){
                    updateButton.setDisable(false);
                }else{
                    updateButton.setDisable(true);
                }


        }else{
            updateButton.setDisable(true);
        }


    }
    private void test(){
        if (statusButton == 1){
           checkTextFieldUpdate();
        }else if(statusButton == 2){
            checkTextFields();
        }
    }
}
