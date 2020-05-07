package sample.Model.DB_Read;

import sample.Model.DBProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListOfSpareParts {
    private List<String> listNameSpareParts = new ArrayList<>();
    public List<String> getListNameSpareParts() {
        return listNameSpareParts;
    }
    public void setListNameSpareParts(List<String> listNameSpareParts) {
        this.listNameSpareParts = listNameSpareParts;
    }

    private List<String> listPriceSpareParts = new ArrayList<>();
    public List<String> getListPriceSpareParts() {
        return listPriceSpareParts;
    }

    private List<Integer> listValidSpareParts = new ArrayList<>();
    public List<Integer> getListValidSpareParts() {
        return listValidSpareParts;
    }

    public void getAllSparePartsRead(int id){
        try {
            DBProcessor dbProcessor = new DBProcessor();
            Connection conn = dbProcessor.getConnection(DBProcessor.getURL(), DBProcessor.getUSER(), DBProcessor.getPASS());
            String update = "CALL sp_getAllSpareParts("+id+");";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(update);
            while (res.next()) {
                String name = res.getString("name");
                String price = res.getString("price");
                int valid = res.getInt("validate");
                listNameSpareParts.add(name);
                listPriceSpareParts.add(price);
                listValidSpareParts.add(valid);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
