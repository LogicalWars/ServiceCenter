package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TicketLogs {




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

    public void setIdLogFromDB(int idLogFromDB) {
        this.idLogFromDB = idLogFromDB;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    private SimpleIntegerProperty idLog;
    private SimpleStringProperty dateLog;

    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    private SimpleStringProperty user;

    private int idLogFromDB;
    private String oldValue;
    private String newValue;
    private String field;

    public TicketLogs(int idLog, String dateLog, int idTicket, String user) {
        this.idLog = new SimpleIntegerProperty(idLog);
        this.dateLog = new SimpleStringProperty(dateLog);
        this.idLogFromDB = idTicket;
        this.user = new SimpleStringProperty(user);
    }

    public TicketLogs(String field, String oldValue, String newValue) {
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
