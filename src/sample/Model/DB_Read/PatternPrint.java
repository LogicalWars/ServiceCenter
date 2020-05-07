package sample.Model.DB_Read;

import sample.Model.DBProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PatternPrint {
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
}
