package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controller.NewTicketController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

public class DataTickets {

    private ObservableList<Tickets> ticketsData = FXCollections.observableArrayList();

    public ObservableList<Tickets> getTicketsData() {
        return ticketsData;
    }

    public void dataTicketsRead() {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "" +
                    "SELECT table_test.*, status.`status` as `status` " +
                    "FROM table_test " +
                    "INNER JOIN status ON (table_test.`status_id` = status.`idStatus`) " +
                    "ORDER BY `table_test`.`idTicket` DESC;";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("idTicket");
                String p = res.getString("phoneNumber");
                String name = res.getString("fullName");
                Date date1 = res.getDate("dateCreateTicket");
                String date2 = String.valueOf(date1);
                String status = res.getString("status");
                ticketsData.add(new Tickets(id, p, name, date2, status));
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createNewTicketWrite(String textPhone, String textFullName, String textDevice, String textModel, String textDefect, String textNode, String textCondition) {
        try {
            LocalDate date = LocalDate.now();
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String create = "INSERT INTO `table_test` (`phoneNumber`, `fullName`,`device`,`model`,`defect`,`note`,`condition`,`dateCreateTicket`, `status_id`) " +
                    "VALUES ('" + textPhone + "'," +
                    " '" + textFullName + "'," +
                    " '" + textDevice + "'," +
                    " '" + textModel + "'," +
                    " '" + textDefect + "'," +
                    " '" + textNode + "'," +
                    " '" + textCondition + "'," +
                    " '" + date + "'," +
                    " '" + 1 + "')";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(create);
            } catch (SQLException e) {
                System.out.println(e);
                e.getErrorCode();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
