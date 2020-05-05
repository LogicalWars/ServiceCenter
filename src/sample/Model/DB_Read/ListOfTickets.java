package sample.Model.DB_Read;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.DBProcessor;
import sample.Model.Tickets;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class ListOfTickets {

    private ObservableList<Tickets> ticketsData = FXCollections.observableArrayList();
    public ObservableList<Tickets> getTicketsData() {
        return ticketsData;
    }

    private ArrayList<String> phoneSeachList = new ArrayList<>();
    public ArrayList<String> getPhoneSeachList() {
        return phoneSeachList;
    }

    private ArrayList<String> fullNameSeachList = new ArrayList<>();
    public ArrayList<String> getFullNameSeachList() {
        return fullNameSeachList;
    }

    private ArrayList<String> idSeachList = new ArrayList<>();
    public ArrayList<String> getIdSeachList() {
        return idSeachList;
    }

    private ArrayList<String> dateSeachList = new ArrayList<>();
    public ArrayList<String> getDateSeachList() {
        return dateSeachList;
    }

    private ArrayList<String> statusSeachList = new ArrayList<>();
    public ArrayList<String> getStatusSeachList() {
        return statusSeachList;
    }

    public void dataTicketsRead() {
        try {

            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL `sp_getTicketList`();";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("numberTicket");
                String p = res.getString("phoneNumber");
                String name = res.getString("fullName");
                LocalDate date1 = LocalDate.parse(res.getString("dateCreateTicket"));
                DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
                String status = res.getString("status");
                ticketsData.add(new Tickets(id, p, name, shortDateTime.format(date1), status));
                idSeachList.add(String.valueOf(id));
                phoneSeachList.add(p);
                fullNameSeachList.add(name);
                dateSeachList.add( shortDateTime.format(date1));
                statusSeachList.add(status);

            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
