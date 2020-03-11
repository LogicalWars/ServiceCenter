package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

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
    private String dateCloseTicket;
    private String statusTicket;
    private String deviceTicket;
    private String defectTicket;
    private String modelTicket;
    private String markTicket;
    private String noteTicket;
    private String conditionTicket;
    private String idStatusTicket;
    private String commentTicket;

    public String getDateCloseTicket() {
        return dateCloseTicket;
    }
    public void setDateCloseTicket(String dateCloseTicket){
        this.dateCloseTicket = dateCloseTicket;
    }

    public String getCommentTicket() {return commentTicket;}

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
                    "SELECT ticket_data.*, status.`status` as `status` " +
                    "FROM ticket_data " +
                    "INNER JOIN status ON (ticket_data.`status_id` = status.`idStatus`) " +
                    "ORDER BY `ticket_data`.`idTicket` DESC;";
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
            String create = "INSERT INTO `ticket_data` (`phoneNumber`, `fullName`,`device`,`model`,`defect`,`note`,`condition`,`dateCreateTicket`, `mark`, `status_id`) " +
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
        String query = "SELECT ticket_data.*, status.`status` as `status` " +
                "FROM ticket_data " +
                "INNER JOIN status ON (ticket_data.`status_id` = status.`idStatus`) " +
                "WHERE ticket_data.`idTicket` =" + id;
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(query);
        while (res.next()) {
            idTicket = res.getInt("idTicket");
            phoneNumber = checkNull(res.getString("phoneNumber"));
            fullName = checkNull(res.getString("fullName"));
            LocalDate date = LocalDate.parse(res.getString("dateCreateTicket"));
            DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
            dateCreateTicket = checkNull(shortDateTime.format(date));
            statusTicket = checkNull(res.getString("status"));
            deviceTicket = checkNull(res.getString("device"));
            defectTicket = checkNull(res.getString("defect"));
            modelTicket = checkNull(res.getString("model"));
            markTicket = checkNull(res.getString("mark"));
            noteTicket = checkNull(res.getString("note"));
            conditionTicket = checkNull(res.getString("condition"));
            idStatusTicket = checkNull(res.getString("status_id"));
            commentTicket = checkNull(res.getString("comment"));

            if(statusTicket.equals("Выдан")) {
                LocalDate dateClose = LocalDate.parse(res.getString("dateCloseTicket"));
                DateTimeFormatter shortDateClose = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
                dateCloseTicket = shortDateClose.format(dateClose);
            }
        }
    }

    public void statusUploadRead() {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "SELECT * FROM `status`";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String status = checkNull(res.getString("status"));
                statusUpload.add(new Status(status));
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveEditTicketWrite(int id, int status, String phone, String fullName, String device, String model, String mark, String defect, String note, String condition, String comment) {
        try {
            LocalDate date = LocalDate.now();
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "UPDATE ticket_data " +
                    "SET `phoneNumber` = " + phone + ", " +
                    "`status_id` = " + status + ", " +
                    "`fullName` = '" + fullName + "', " +
                    "`device` ='" + device + "' , " +
                    "`model` = '" + model + "', " +
                    "`mark` = '" + mark + "', " +
                    "`defect` = '" + defect + "', " +
                    "`note` = '" + note + "', " +
                    "`condition` = '" + condition + "'," +
                    "`comment` = '" + comment + "'," +
                    "`dateCloseTicket` = '" + LocalDate.now() + "'" +
                    "WHERE `idTicket` = " + id;
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

    public void ticketLogsWrite(int idTicket,
                                String phoneNumberOld,
                                String phoneNumberNew,
                                String fullNameOld,
                                String fullNameNew,
                                String statusOld,
                                String statusNew,
                                String deviceOld,
                                String deviceNew,
                                String modelOld,
                                String modelNew,
                                String defectOld,
                                String defectNew,
                                String noteOld,
                                String noteNew,
                                String conditionOld,
                                String conditionNew,
                                String markOld,
                                String markNew,
                                String commentOld,
                                String commentNew) {
        try {
            LocalDateTime date = LocalDateTime.now();
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String create = "INSERT INTO `ticketLogs` (`date`," +
                    " `idTicket`," +
                    " `phoneNumberOld`," +
                    " `phoneNumberNew`," +
                    " `fullNameOld`," +
                    " `fullNameNew`, " +
                    " `statusOld`," +
                    " `statusNew`," +
                    " `deviceOld`," +
                    " `deviceNew`," +
                    " `modelOld`," +
                    " `modelNew`," +
                    " `defectOld`," +
                    " `defectNew`," +
                    " `noteOld`," +
                    " `noteNew`," +
                    " `conditionOld`," +
                    " `conditionNew`," +
                    " `markOld`," +
                    " `markNew`," +
                    " `commentOld`," +
                    " `commentNew`)" +
                    "VALUES ('" + date + "'," +
                    "'" + idTicket + "'," +
                    " '" + phoneNumberOld + "'," +
                    " '" + phoneNumberNew + "'," +
                    " '" + fullNameOld + "'," +
                    " '" + fullNameNew + "'," +
                    " '" + statusOld + "'," +
                    " '" + statusNew + "'," +
                    " '" + deviceOld + "'," +
                    " '" + deviceNew + "'," +
                    " '" + modelOld + "'," +
                    " '" + modelNew + "'," +
                    " '" + defectOld + "'," +
                    " '" + defectNew + "'," +
                    " '" + noteOld + "'," +
                    " '" + noteNew + "'," +
                    " '" + conditionOld + "'," +
                    " '" + conditionNew + "'," +
                    " '" + markOld + "'," +
                    " '" + markNew + "',"+
                    " '" + commentOld + "'," +
                    " '" + commentNew + "' )";
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

    public void ticketLogsRead(int id) {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "SELECT ticketLogs.* " +
                    "FROM ticketLogs " +
                    "INNER JOIN ticket_data ON (ticketLogs.`idTicket` = ticket_data.`idTicket`) " +
                    "WHERE ticket_data.`idTicket` = '" + id + "' ORDER BY `id`";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            int idLogs = 1;
            while (res.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String i = res.getString("date");
                LocalDateTime date1 = LocalDateTime.parse(i, formatter);
                DateTimeFormatter fr = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                int idLog = res.getInt("id");
                ticketLogs.add(new TicketLogs(idLogs, date1.format(fr), idLog));
                idLogs++;
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> fieldsTicket = new ArrayList<>();
    private ArrayList<String> oldValue = new ArrayList<>();
    private ArrayList<String> newValue = new ArrayList<>();
    private ObservableList<TicketLogs> ticketLogsData = FXCollections.observableArrayList();

    public ObservableList<TicketLogs> getTicketLogsData() {
        return ticketLogsData;
    }
    private void checkForLogs(String oldValueLog, String newValueLog, String textValueLog){
        if (oldValueLog.compareTo(newValueLog) != 0) {
            newValue.add(newValueLog);
            oldValue.add(oldValueLog);
            fieldsTicket.add(textValueLog);
        }
    }
    public void logDataRead(int id) {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "SELECT ticketLogs.* " +
                    "FROM ticketLogs " +
                    "INNER JOIN ticket_data ON (ticketLogs.`idTicket` = ticket_data.`idTicket`) " +
                    "WHERE ticketLogs.`id` =" + id;
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String phoneNumberOld = checkNull(res.getString("phoneNumberOld"));
                String phoneNumberNew = checkNull(res.getString("phoneNumberNew"));
                String fullNameOld = checkNull(res.getString("fullNameOld"));
                String fullNameNew = checkNull(res.getString("fullNameNew"));
                String statusOld = checkNull(res.getString("statusOld"));
                String statusNew = checkNull(res.getString("statusNew"));
                String deviceOld = checkNull(res.getString("deviceOld"));
                String deviceNew = checkNull(res.getString("deviceNew"));
                String modelOld = checkNull(res.getString("modelOld"));
                String modelNew = checkNull(res.getString("modelNew"));
                String defectOld = checkNull(res.getString("defectOld"));
                String defectNew = checkNull(res.getString("defectNew"));
                String noteOld = checkNull(res.getString("noteOld"));
                String noteNew = checkNull(res.getString("noteNew"));
                String conditionOld = checkNull(res.getString("conditionOld"));
                String conditionNew = checkNull(res.getString("conditionNew"));
                String markOld = checkNull(res.getString("markOld"));
                String markNew = checkNull(res.getString("markNew"));
                String commentOld = checkNull(res.getString("commentOld"));
                String commentNew = checkNull(res.getString("commentNew"));

                checkForLogs(phoneNumberOld,phoneNumberNew,"Телефон");
                checkForLogs(fullNameOld,fullNameNew,"ФИО");
                checkForLogs(statusOld,statusNew,"Статус");
                checkForLogs(deviceOld,deviceNew,"Устройство");
                checkForLogs(modelOld,modelNew,"Модель");
                checkForLogs(defectOld,defectNew,"Дефект");
                checkForLogs(noteOld,noteNew,"Примечание");
                checkForLogs(conditionOld,conditionNew,"Состояние");
                checkForLogs(markOld,markNew,"Марка");
                checkForLogs(commentOld,commentNew,"Комментарий");
            }
            int i = 0;
            if (fieldsTicket.size() != 0) {
                while (i != fieldsTicket.size()) {
                    ticketLogsData.add(new TicketLogs(fieldsTicket.get(i), oldValue.get(i), newValue.get(i)));
                    i++;
                }
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getDateTimeLogRead(int id) {
        String dateTime = null;
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "SELECT ticketLogs.date " +
                    "FROM ticketLogs " +
                    "WHERE `id` =" + id;
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String i = res.getString("date");
                LocalDateTime date1 = LocalDateTime.parse(i, formatter);
                DateTimeFormatter fr = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                dateTime = date1.format(fr);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    private String checkNull(String field){
        if(field == null){
            return "";
        }else{return field;}
    }

}
