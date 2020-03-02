package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controller.EditTicketController;
import sample.Controller.NewTicketController;
import sample.Controller.TicketListController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DataTickets {

    private ObservableList<Tickets> ticketsData = FXCollections.observableArrayList();

    public ObservableList<Tickets> getTicketsData() {
        return ticketsData;
    }

    private ObservableList<Status> statusUpload = FXCollections.observableArrayList();

    public ObservableList<Status> getStatusUpload() {
        return statusUpload;
    }

    private int idTicket;
    private String phoneNumber;
    private String fullName;
    private String dateCreateTicket;
    private String statusTicket;
    private String deviceTicket;
    private String defectTicket;
    private String modelTicket;
    private String markTicket;
    private String noteTicket;
    private String conditionTicket;

    public int getIdTicket() {
        return idTicket;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateCreateTicket() {
        return dateCreateTicket;
    }

    public String getStatusTicket() {
        return statusTicket;
    }

    public String getDeviceTicket() {
        return deviceTicket;
    }

    public String getDefectTicket() {
        return defectTicket;
    }

    public String getModelTicket() {
        return modelTicket;
    }

    public String getMarkTicket() {
        return markTicket;
    }

    public String getNoteTicket() {
        return noteTicket;
    }

    public String getConditionTicket() {
        return conditionTicket;
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
//                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
//                String date = sdf.format(date1);
                String status = res.getString("status");
                ticketsData.add(new Tickets(id, p, name, date2, status));
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createNewTicketWrite(String textPhone, String textFullName, String textDevice, String textModel, String textDefect, String textNode, String textCondition, String textMark) {
        try {
            LocalDate date = LocalDate.now();
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String create = "INSERT INTO `table_test` (`phoneNumber`, `fullName`,`device`,`model`,`defect`,`note`,`condition`,`dateCreateTicket`, `mark`, `status_id`) " +
                    "VALUES ('" + textPhone + "'," +
                    " '" + textFullName + "'," +
                    " '" + textDevice + "'," +
                    " '" + textModel + "'," +
                    " '" + textDefect + "'," +
                    " '" + textNode + "'," +
                    " '" + textCondition + "'," +
                    " '" + date + "'," +
                    " '" + textMark + "'," +
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

    public void editTicketRead(int id) throws SQLException {
        DBProcessor dbProcessor = new DBProcessor();
        Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
        String query =  "SELECT table_test.*, status.`status` as `status` " +
                        "FROM table_test " +
                        "INNER JOIN status ON (table_test.`status_id` = status.`idStatus`) " +
                        "WHERE table_test.`idTicket` =" + id;
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(query);
        while (res.next()) {
            idTicket = res.getInt("idTicket");
            phoneNumber = res.getString("phoneNumber");
            fullName = res.getString("fullName");
            Date date = res.getDate("dateCreateTicket");
            dateCreateTicket = String.valueOf(date);
            statusTicket = res.getString("status");
            deviceTicket = res.getString("device");
            defectTicket = res.getString("defect");
            modelTicket = res.getString("model");
            markTicket = res.getString("mark");
            noteTicket = res.getString("note");
            conditionTicket = res.getString("condition");

        }
    }

    String status;

    public void statusUploadRead(){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "SELECT * FROM `status`";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                status = res.getString("status");
                statusUpload.add(new Status(status.toString()));
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
