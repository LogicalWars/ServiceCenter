package sample.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBProcessor {
    private static final String URL = "jdbc:mysql://h146169.s23.test-hf.su/h146169_mydbtest?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&verifyServerCertificate=false";
    private static final String USER = "h146169_root";
    private static final String PASS = "6B1c0B6y";

    public static String getURL() {
        return URL;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASS() {
        return PASS;
    }



    private Connection connection;
    public  Connection getConnection(String url, String name, String pass) throws SQLException {
        if(connection != null)
            return connection;
            connection = DriverManager.getConnection(url, name, pass);
            return connection;
    }
/**Проверка подключения к БД идет вывод в MainMenuController**/
    public String resultConnection(){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)){
            return "Соединение установленно!";
        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            return "Соединение не установленно! Причина: "+ e;
        }
    }

}
