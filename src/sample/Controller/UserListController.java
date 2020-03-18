package sample.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import sample.Enum.User;
import sample.Model.DataTickets;
import sample.Model.Rules;
import sample.Model.UserData;

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

    DataTickets dataTickets = new DataTickets();

    @FXML
    void initialize(){
        dataTickets.UserListDataRead();
        dataTickets.allRules();

        editMenu.setDisable(true);

        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        rules.setCellValueFactory(new PropertyValueFactory<>("rules"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        valid.setCellValueFactory(new PropertyValueFactory<>("valid"));

        tableUserList.setItems(dataTickets.getUserListData());
        rulesComboBox.setItems(dataTickets.getAllRulesData());
    }

    @FXML
    void SaveСhanges() {
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
    }

    @FXML
    void addNewUser() {
        editMenu.setDisable(false);
    }

    @FXML
    void addValid(){
        dataTickets.setValidUser(tableUserList.getSelectionModel().getFocusedIndex() + 1);
    }

}
