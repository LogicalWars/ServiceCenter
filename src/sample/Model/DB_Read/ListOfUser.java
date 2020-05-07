package sample.Model.DB_Read;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Enum.User;
import sample.Model.DBProcessor;
import sample.Model.Rules;
import sample.Model.UserData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ListOfUser {
    //Коллекция для заполнения таблицы пользователей
    private ObservableList<UserData> userListData = FXCollections.observableArrayList();
    public ObservableList<UserData> getUserListData() {
        return userListData;
    }

    //Коллекция для проверки на уникальность логина
    private ArrayList<String> loginList = new ArrayList<>();
    public ArrayList<String> getLoginList() {
        return loginList;
    }

    //Коллекция для ComboBox, для выбора роли при создании нового пользователя
    ObservableList<Rules> allRulesData = FXCollections.observableArrayList();
    public ObservableList<Rules> getAllRulesData() {
        return allRulesData;
    }



    public void UserListDataRead(){
        try {
            loginList.clear();
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL sp_getUserList();";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("userId");
                String login = res.getString("login");
                String password = res.getString("password");
                String rules = res.getString("rules");
                String name = res.getString("name");
                int valid = res.getInt("valid");
                userListData.add(new UserData(id, login, password, rules, name, valid));
                loginList.add(login);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void allRules(){
        allRulesData.add(new Rules(User.ADMIN.name()));
        allRulesData.add(new Rules(User.MASTER.name()));
        allRulesData.add(new Rules(User.OPERATOR.name()));
    }
}
