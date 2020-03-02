package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TicketLogs {

    private SimpleIntegerProperty idLog;
    private SimpleStringProperty dateLog;

    public TicketLogs (int idLog, String dateLog){
        this.idLog = new SimpleIntegerProperty(idLog);
        this.dateLog = new SimpleStringProperty(dateLog);
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
}
