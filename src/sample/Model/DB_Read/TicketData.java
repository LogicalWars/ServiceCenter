package sample.Model.DB_Read;

import sample.Model.DBProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TicketData {

    private int idTicket;
    private int numberTicket;
    private String phoneNumber;
    private String fullName;
    private String dateCreateTicket;
    private String dateCloseTicket;
    private String statusTicket;
    private String deviceTicket;
    private String defectTicket;
    private String modelTicket;
    private String noteTicket;
    private String conditionTicket;
    private String idStatusTicket;
    private String commentTicket;
    private String repairPriceTicket;
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

    public int getNumberTicket() {
        return numberTicket;
    }

    public String getNoteTicket() {
        return noteTicket;
    }

    public String getRepairPriceTicket() {
        return repairPriceTicket;
    }

    public String getConditionTicket() {
        return conditionTicket;
    }

    public void editTicketRead(int id) throws SQLException {
        DBProcessor dbProcessor = new DBProcessor();
        Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
        String query = "CALL `sp_getDataTicket`("+id+");";
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(query);
        while (res.next()) {
            idTicket = res.getInt("idTicket");
            numberTicket = res.getInt("numberTicket");
            phoneNumber = checkNull(res.getString("phoneNumber"));
            fullName = checkNull(res.getString("fullName"));
            LocalDate date = LocalDate.parse(res.getString("dateCreateTicket"));
            DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
            dateCreateTicket = checkNull(shortDateTime.format(date));
            statusTicket = checkNull(res.getString("status"));
            deviceTicket = checkNull(res.getString("device"));
            defectTicket = checkNull(res.getString("defect"));
            modelTicket = checkNull(res.getString("model"));
            noteTicket = checkNull(res.getString("note"));
            conditionTicket = checkNull(res.getString("condition"));
            idStatusTicket = checkNull(res.getString("status_id"));
            commentTicket = checkNull(res.getString("comment"));
            repairPriceTicket = checkNull(res.getString("repairPrice"));


            if(statusTicket.equals("Выдан")) {
                LocalDate dateClose = LocalDate.parse(res.getString("dateCloseTicket"));
                DateTimeFormatter shortDateClose = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
                dateCloseTicket = shortDateClose.format(dateClose);
            }
        }
    }

    private String checkNull(String field){
        if(field == null){
            return "";
        }else{return field;}
    }
}
