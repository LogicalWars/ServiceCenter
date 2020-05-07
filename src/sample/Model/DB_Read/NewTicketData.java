package sample.Model.DB_Read;

import sample.Model.DBProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NewTicketData {
    private ArrayList<String> deviceList = new ArrayList<>();
    public ArrayList<String> getDeviceList() {
        return deviceList;
    }

    private ArrayList<String> fullNameList = new ArrayList<>();
    public ArrayList<String> getFullNameList() {
        return fullNameList;
    }

    private ArrayList<String> phoneNumberList = new ArrayList<>();
    public ArrayList<String> getPhoneNumberList() {
        return phoneNumberList;
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
}
