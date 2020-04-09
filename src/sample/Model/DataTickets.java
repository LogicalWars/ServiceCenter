package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Enum.User;

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

    public int getNumberTicket() {
        return numberTicket;
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
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

            if(statusTicket.equals("Выдан")) {
                LocalDate dateClose = LocalDate.parse(res.getString("dateCloseTicket"));
                DateTimeFormatter shortDateClose = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
                dateCloseTicket = shortDateClose.format(dateClose);
            }
        }
    }

    private ArrayList<String> allStatus = new ArrayList<>();

    public ArrayList<String> getAllStatus () {
        return allStatus;
    }

    public void statusUploadRead() {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL `sp_getAllStatus`();";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String status = checkNull(res.getString("status"));
                statusUpload.add(new Status(status));
                allStatus.add(status);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveEditTicketWrite(int id, int status, String phone, String fullName, String device, String model, String defect, String note, String condition, String comment, int numberTicket, List<ComboBox> nameSpare, List<TextField> price) {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "CALL `sp_saveEditTicket`("+id+","+status+",'"+phone+"','"+fullName+"','"+device+"','"+model+"','"+defect+"','"+note+"','"+condition+"','"+comment+"',"+numberTicket+", '"+LocalDate.now()+"')";
            String delete = "CALL `sp_getDeleteSpare`("+id+");";

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(update);
                stmt.execute(delete);
                for(int a=0; a<nameSpare.size();a++){
                    System.out.println(nameSpare.get(a).getValue());
                    stmt.execute("CALL `sp_getSaveSpare`("+id+",'"+nameSpare.get(a).getValue()+"',"+price.get(a).getText()+");");
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

    public void ticketLogsRead(int id) {
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL sp_getTicketLogs("+id+");";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            int idLogs = 1;
            while (res.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String i = res.getString("date");
                LocalDateTime date1 = LocalDateTime.parse(i, formatter);
                DateTimeFormatter fr = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                String userLogin = res.getString("login");
                int idLog = res.getInt("id");
                ticketLogs.add(new TicketLogs(idLogs, date1.format(fr), idLog, userLogin));
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

    private String checkNull(String field){
        if(field == null){
            return "";
        }else{return field;}
    }

    public ArrayList<String> getPrintPatternData() { return printPatternData; }
    private ArrayList<String> printPatternData = new ArrayList<>();
    /** labelTitle          0
     *  labelRules          1
     *  labelSignAccept     2
     *  labelSignPassed     3
     *  labelOrderTicket    4
     *  labelFullName       5
     *  labelPhone          6
     *  labelNote           7
     *  labelDate           8
     *  labelDevice         9
     *  labelModel          10
     *  labelCondition      11
     *  labelDefect         12
     * */


    public void editPatternPrintRead(){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL sp_getDataPatternPrint;";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                printPatternData.add(res.getString("labelTitle"));
                printPatternData.add(res.getString("labelRules"));
                printPatternData.add(res.getString("labelSignAccept"));
                printPatternData.add(res.getString("labelSignPassed"));
                printPatternData.add(res.getString("labelOrderTicket"));
                printPatternData.add(res.getString("labelFullName"));
                printPatternData.add(res.getString("labelPhone"));
                printPatternData.add(res.getString("labelNote"));
                printPatternData.add(res.getString("labelDate"));
                printPatternData.add(res.getString("labelDevice"));
                printPatternData.add(res.getString("labelModel"));
                printPatternData.add(res.getString("labelCondition"));
                printPatternData.add(res.getString("labelDefect"));
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public ObservableList<UserData> getUserListData() {
        return userListData;
    }

    private ObservableList<UserData> userListData = FXCollections.observableArrayList();

    public ArrayList<String> getLoginList() {
        return loginList;
    }

    private ArrayList<String> loginList = new ArrayList<>();


    public void UserListDataRead(){
        try {
            loginList.clear();
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL sp_getUserList();";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("userId");
                String login = res.getString("login");
                String password = res.getString("password");
                String rules = res.getString("rules");
                String name = res.getString("name");
                int valid = res.getInt("valid");
                userListData.add(new UserData(id, login, password, rules, name, valid));
                loginList.add(login);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Rules> getAllRulesData() {
        return allRulesData;
    }

    ObservableList<Rules> allRulesData = FXCollections.observableArrayList();

    public void allRules(){
        allRulesData.add(new Rules(User.ADMIN.name()));
        allRulesData.add(new Rules(User.MASTER.name()));
        allRulesData.add(new Rules(User.OPERATOR.name()));
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


    /**ДЛЯ ЧЕГО ЭТОТ МЕТОД??*/
    public void setValidUser(int userId){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "UPDATE rules " +
                    "SET `valid` = '" + 0 + "'" +
                    "WHERE `userId` = " + userId;
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

    public ObservableList<StockList> getStockListData() {
        return stockListData;
    }

    private ObservableList<StockList> stockListData = FXCollections.observableArrayList();

    public ObservableList<StockList> getStockListSearch() {
        return stockListSearch;
    }

    private ObservableList<StockList> stockListSearch = FXCollections.observableArrayList();

    private ArrayList<Integer> elementIdList = new ArrayList<>();
    private ArrayList<String> modelList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<Integer> amountList = new ArrayList<>();
    private ArrayList<Integer> priceList = new ArrayList<>();
    private ArrayList<String> fullNameList = new ArrayList<>();
    private ArrayList<String> phoneNumberList = new ArrayList<>();
    private ArrayList<String> deviceList = new ArrayList<>();

    public ArrayList<String> getDeviceList() {
        return deviceList;
    }

    public ArrayList<String> getFullNameList() {
        return fullNameList;
    }

    public ArrayList<String> getPhoneNumberList() {
        return phoneNumberList;
    }

    public ArrayList<String> getModelList() {
        return modelList;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public ArrayList<Integer> getAmountList() {
        return amountList;
    }

    public ArrayList<Integer> getPriceList() {
        return priceList;
    }

    public ArrayList<Integer> getElementIdList() {
        return elementIdList;
    }

    public void stockListDataRead(){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL sp_getStockList();";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                int elementId = res.getInt("idElement");
                String model = res.getString("model");
                String name = res.getString("name");
                int amount = res.getInt("amount");
                int price = res.getInt("price");
                stockListData.add(new StockList(elementId, model, name, amount, price));
                elementIdList.add(elementId);
                modelList.add(model);
                nameList.add(name);
                amountList.add(amount);
                priceList.add(price);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newTicketListRead(){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String query = "CALL sp_getClientsData();";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                long phoneNumber = res.getLong("phoneNumber");
                String name = res.getString("fullName");
                String device = res.getString("device");
                fullNameList.add(name);
                phoneNumberList.add(String.valueOf(phoneNumber));
                deviceList.add(String.valueOf(device));
            }
            stmt.close();
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

}
