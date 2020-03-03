package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TicketLogs {

    private SimpleIntegerProperty idLog;
    private SimpleStringProperty dateLog;

    private int idLogFromDB;
    private String phoneNumberOld;
    private String phoneNumberNew;

    public TicketLogs (int idLog, String dateLog, int idTicket){
        this.idLog = new SimpleIntegerProperty(idLog);
        this.dateLog = new SimpleStringProperty(dateLog);
        this.idLogFromDB = idTicket;
    }

    public TicketLogs (String phoneNumberOld, String phoneNumberNew){
        this.phoneNumberOld = phoneNumberOld;
        this. phoneNumberNew = phoneNumberNew;
    }

    public TicketLogs (int idLog){
        this.idLog = new SimpleIntegerProperty(idLog);
    }

    public int getIdLog() {
        return idLog.get();
    }

    public SimpleIntegerProperty idLogProperty() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog.set(idLog);
    }

    public String getDateLog() {
        return dateLog.get();
    }

    public SimpleStringProperty dateLogProperty() {
        return dateLog;
    }

    public void setDateLog(String dateLog) {
        this.dateLog.set(dateLog);
    }

    public int getIdLogFromDB() {
        return idLogFromDB;
    }
}
