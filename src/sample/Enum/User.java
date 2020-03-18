package sample.Enum;

import sample.Model.DBProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public enum User {
    ADMIN,
    MASTER,
    OPERATOR;

    public static User USER;
    public static String loginUser;

    public static void getUserRole(String login, String password){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "SELECT `rulesId`, `login` " +
                    "FROM `rules` WHERE " +
                    "`login` = '" + login + "'" +
                    "AND " +
                    "`password` = '" + password + "'";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String rulesString = res.getString("rulesId");
                loginUser = res.getString("login");

                if(rulesString.equals(ADMIN.name())){
                    USER = ADMIN;
                }
                if(rulesString.equals(MASTER.name())){
                    USER = MASTER;
                }
                if(rulesString.equals(OPERATOR.name())){
                    USER = OPERATOR;
                }
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
