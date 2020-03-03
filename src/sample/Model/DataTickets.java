package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controller.EditTicketController;
import sample.Controller.NewTicketController;
import sample.Controller.TicketListController;

import javax.swing.text.DateFormatter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    private ObservableList<TicketLogs> ticketLogs = FXCollections.observableArrayList();

    public ObservableList<TicketLogs> getTicketLogs() {
        return ticketLogs;
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
    private String idStatusTicket;
    private int year;
    private int month;
    private int day;

    public String getIdStatusTicket() {
        return idStatusTicket;
    }

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
                LocalDate date1 = LocalDate.parse(res.getString("dateCreateTicket"));
                DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
                String status = res.getString("status");
                ticketsData.add(new Tickets(id, p, name, shortDateTime.format(date1), status));
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
            LocalDate date = LocalDate.parse(res.getString("dateCreateTicket"));
            DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
            dateCreateTicket = shortDateTime.format(date);
            statusTicket = res.getString("status");
            deviceTicket = res.getString("device");
            defectTicket = res.getString("defect");
            modelTicket = res.getString("model");
            markTicket = res.getString("mark");
            noteTicket = res.getString("note");
            conditionTicket = res.getString("condition");
            idStatusTicket = res.getString("status_id");

        }
    }

    public void statusUploadRead(){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "SELECT * FROM `status`";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String status = res.getString("status");
                statusUpload.add(new Status(status));
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveEditTicketWrite(int id, int status, String phone, String fullName, String device, String model, String mark, String defect, String note, String condition){
        try {
//            LocalDate date = LocalDate.now();
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "UPDATE table_test " +
                    "SET `phoneNumber` = "+ phone +", " +
                    "`status_id` = "+ status +", " +
                    "`fullName` = '"+ fullName +"', " +
                    "`device` ='"+ device +"' , " +
                    "`model` = '"+ model +"', " +
                    "`mark` = '"+ mark +"', " +
                    "`defect` = '"+ defect +"', " +
                    "`note` = '"+ note +"', " +
                    "`condition` = '"+ condition + "'" +
                    "WHERE `idTicket` = "+ id;
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(update);
            } catch (SQLException e) {
                System.out.println(e);
                e.getErrorCode();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ticketLogsWrite(int idTicket){
        try {
            LocalDateTime date = LocalDateTime.now();
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String create = "INSERT INTO `ticketLogs` (`date`, `idTicket`) " +
                    "VALUES ('" + date + "'," +
                    " '" + idTicket + "')";
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

    public void ticketLogsRead(int id){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "SELECT ticketLogs.* " +
                    "FROM ticketLogs " +
                    "INNER JOIN table_test ON (ticketLogs.`idTicket` = table_test.`idTicket`) " +
                    "WHERE table_test.`idTicket` =" + id;
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            int idLog = 1;
            while (res.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String i = res.getString("date");
                LocalDateTime date1 = LocalDateTime.parse(i,formatter);
                DateTimeFormatter fr = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                ticketLogs.add(new TicketLogs(idLog, date1.format(fr)));
                idLog++;
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
