package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Enum.User;

import javax.naming.Context;
import java.io.IOException;
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;


public class DataTickets {
    private String dateCloseTicket;
    public String getDateCloseTicket() {
        return dateCloseTicket;
    }
    public void setDateCloseTicket(String dateCloseTicket){
        this.dateCloseTicket = dateCloseTicket;
    }
    public void createNewTicketWrite(String textPhone, String textFullName, String textDevice, String textModel, String textDefect, String textNode, String textCondition) {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String create = "CALL `sp_getCreateNewTicketWrite`("+textPhone+ ", '"+textFullName+"', '" +textDevice+"', '"+textModel+"', '"+textDefect+"', '"+textNode+"', '"+textCondition+"', '"+LocalDate.now()+"');";

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
    public void saveEditTicketWrite(int id, int status, String phone, String fullName, String device, String model, String defect, String note, String condition, String comment, int numberTicket, List<ComboBox> nameSpare, List<TextField> price , int repairPrice, List<Integer> validSpare) {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "CALL `sp_saveEditTicket`("+id+","+status+",'"+phone+"','"+fullName+"','"+device+"','"+model+"','"+defect+"','"+note+"','"+condition+"','"+comment+"',"+numberTicket+", '"+LocalDate.now()+"', "+repairPrice+" )";

           String delete = "CALL `sp_getDeleteSpare`("+id+");";

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(update);
                stmt.execute(delete);
                for(int a=0; a<nameSpare.size();a++){
                    stmt.execute("CALL `sp_getSaveSpare`("+id+",'"+nameSpare.get(a).getValue()+"',"+price.get(a).getText()+", "+validSpare.get(a)+");");
                    stmt.execute("CALL `sp_updateAmount`();");
                }
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
                                String commentOld,
                                String commentNew,
                                String numberTicketOld,
                                String numberTicketNew,
                                int userId) {
       if(User.OPERATOR == User.USER){
           try {
               DBProcessor dbProcessor = new DBProcessor();
               Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());

               String create = "CALL sp_saveEditTicketLogs (" + idTicket + "," +
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
                       " '" + commentOld + "'," +
                       " '" + commentNew + "'," +
                       " '" + numberTicketOld + "'," +
                       " '" + numberTicketNew + "'," +
                       " "  + userId + "," +
                       "'"  + LocalDateTime.now() + "');";
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
    }
    public void editPatternPrintWrite(String labelTitle,
                                      String labelRules,
                                      String labelSignAccept,
                                      String labelSignPassed,
                                      String labelOrderTicket,
                                      String labelFullName,
                                      String labelPhone,
                                      String labelNote,
                                      String labelDate,
                                      String labelDevice,
                                      String labelModel,
                                      String labelCondition,
                                      String labelDefect){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "CALL sp_saveEditPatternPrint('"+labelTitle+"','"+labelRules+"', '"+labelSignAccept+"'," +
                    "'"+labelSignPassed+"','"+labelOrderTicket+"', '"+labelFullName+"', '"+labelPhone+"', '"+labelNote+"'," +
                    "'"+labelDate+"', '"+labelDevice+"', '"+labelModel+"', '"+labelCondition+"', '"+labelDefect+"', "+1+");";
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

    public Boolean allNumberTicket(int newNumberTicket) {
        Boolean b = true;
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL sp_getAllNumberTicket();";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                int i = res.getInt("numberTicket");
                arrayList.add(i);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int a : arrayList) {
            if (a == newNumberTicket) {
                b = false;
            }
        }
        return b;
    }

        public void addNewUser(String name, String login, String password, String rules, int valid){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String create = "CALL sp_addNewUser('"+name+"', '"+login+"', '"+password+"', '"+rules+"', "+valid+");";
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


        public void userListWrite(int userId, String name, String login, String password, String rules, int valid){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "CALL sp_saveEditUser("+userId+", '"+name+"', '"+login+"', '"+password+"', '"+rules+"', "+valid+");";
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

    public String pressedComment(String text){
        String name;
        String time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime date1 = LocalDateTime.now();
        time = date1.format(formatter);
        name = time + "   " + User.loginUser + "\n" + text;
        return name;
    }
    public void saveComment(String text,int id){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "CALL sp_saveComment('"+text+"', "+id+");";
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

    public void addNewElementWrite(String model, String name, int amount, int price){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String create = "CALL sp_addNewElementStocks('"+model+"', '"+name+"', '"+amount+"', '"+price+"');";
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

    public void stockListWrite(int id, String model, String name, int amount, int price){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "CALL sp_saveEditElementStocks('"+model+"', '"+name+"', '"+amount+"', '"+price+"', "+id+");";
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

    public void setUpdateAmountSpare(String model, String name){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CALL sp_editAmountStock('"+model+"', '"+name+"');");
            } catch (SQLException e) {
                System.out.println(e);
                e.getErrorCode();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
