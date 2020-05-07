package sample.Model.DB_Read;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.DBProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListOfLogs {
    private ObservableList<sample.Model.TicketLogs> ticketLogs = FXCollections.observableArrayList();
    public ObservableList<sample.Model.TicketLogs> getTicketLogs() {
        return ticketLogs;
    }

    public void ticketLogsRead(int id) {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL sp_getTicketLogs("+id+");";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            int idLogs = 1;
            while (res.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String i = res.getString("date");
                LocalDateTime date1 = LocalDateTime.parse(i, formatter);
                DateTimeFormatter fr = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                String userLogin = res.getString("name");
                int idLog = res.getInt("id");
                ticketLogs.add(new sample.Model.TicketLogs(idLogs, date1.format(fr), idLog, userLogin));
                idLogs++;
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
