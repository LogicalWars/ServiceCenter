package sample.Model.Print;

import java.io.*;

public class Print {

    public void createTXTFile(String text) {
        BufferedWriter dataFile = null;
        try {
            dataFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/sample/Model/Print/dataFile.txt"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (byte i = 0; i < 10; i++) {
            try {
                dataFile.write("Тестовая печать 000000000000000000000000000000000000000000000000000000000000000000000      " +text+ "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            dataFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
