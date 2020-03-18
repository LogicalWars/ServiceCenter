package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class UserData {
    private SimpleIntegerProperty userId = new SimpleIntegerProperty();
    private SimpleStringProperty login = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty rules = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();

    public int getUserId() {
        return userId.get();
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getRules() {
        return rules.get();
    }

    public SimpleStringProperty rulesProperty() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules.set(rules);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    private String valid;

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public UserData(int userId, String login, String password, String rules, String name, int valid){
        this.userId = new SimpleIntegerProperty(userId);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.rules = new SimpleStringProperty(rules);
        this.name = new SimpleStringProperty(name);
        if (valid ==0){
            this.valid = "Блокирован";
        }else{
            this.valid = "Активный";
        }
    }
}
