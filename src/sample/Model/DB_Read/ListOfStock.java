package sample.Model.DB_Read;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.DBProcessor;
import sample.Model.StockList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ListOfStock {

    private ObservableList<StockList> stockListData = FXCollections.observableArrayList();
    public ObservableList<StockList> getStockListData() {
        return stockListData;
    }

//    private ObservableList<StockList> stockListSearch = FXCollections.observableArrayList();
//    public ObservableList<StockList> getStockListSearch() {
//        return stockListSearch;
//    }

    private ArrayList<Integer> elementIdList = new ArrayList<>();
    public ArrayList<Integer> getElementIdList() {
        return elementIdList;
    }

    private ArrayList<String> modelList = new ArrayList<>();
    public ArrayList<String> getModelList() {
        return modelList;
    }

    private ArrayList<String> nameList = new ArrayList<>();
    public ArrayList<String> getNameList() {
        return nameList;
    }

    private ArrayList<Integer> amountList = new ArrayList<>();
    public ArrayList<Integer> getAmountList() {
        return amountList;
    }

    private ArrayList<Integer> priceList = new ArrayList<>();
    public ArrayList<Integer> getPriceList() {
        return priceList;
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
}
