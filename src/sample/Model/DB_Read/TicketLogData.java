package sample.Model.DB_Read;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.DBProcessor;
import sample.Model.TicketLogs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TicketLogData {
    private ArrayList<String> fieldsTicket = new ArrayList<>();
    private ArrayList<String> oldValue = new ArrayList<>();
    private ArrayList<String> newValue = new ArrayList<>();
    private ObservableList<TicketLogs> ticketLogsData = FXCollections.observableArrayList();

    public ObservableList<sample.Model.TicketLogs> getTicketLogsData() {
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
            String query = "CALL sp_getLogData("+id+");";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String numberTicketOld = checkNull(res.getString("numberTicketOld"));
                String numberTicketNew = checkNull(res.getString("numberTicketNew"));
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
                String commentOld = checkNull(res.getString("commentOld"));
                String commentNew = checkNull(res.getString("commentNew"));

                checkForLogs(numberTicketOld,numberTicketNew,"№ заявки");
                checkForLogs(phoneNumberOld,phoneNumberNew,"Телефон");
                checkForLogs(fullNameOld,fullNameNew,"ФИО");
                checkForLogs(statusOld,statusNew,"Статус");
                checkForLogs(deviceOld,deviceNew,"Устройство");
                checkForLogs(modelOld,modelNew,"Модель");
                checkForLogs(defectOld,defectNew,"Дефект");
                checkForLogs(noteOld,noteNew,"Примечание");
                checkForLogs(conditionOld,conditionNew,"Состояние");
                checkForLogs(commentOld,commentNew,"Комментарий");
            }
            int i = 0;
            if (fieldsTicket.size() != 0) {
                while (i != fieldsTicket.size()) {
                    ticketLogsData.add(new sample.Model.TicketLogs(fieldsTicket.get(i), oldValue.get(i), newValue.get(i)));
                    i++;
                }
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String checkNull(String field){
        if(field == null){
            return "";
        }else{return field;}
    }


    public String getDateTimeLogRead(int id) {
        String dateTime = null;
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL sp_getDateTimeLog("+id+");";
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
}
