package sample.Model.DB_Read;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.DBProcessor;
import sample.Model.Status;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ListOfStatus {

    private ArrayList<String> allStatus = new ArrayList<>();
    public ArrayList<String> getAllStatus () {
        return allStatus;
    }

    private ObservableList<Status> statusUpload = FXCollections.observableArrayList();
    public ObservableList<Status> getStatusUpload() {
        return statusUpload;
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

    private String checkNull(String field){
        if(field == null){
            return "";
        }else{return field;}
    }
}
