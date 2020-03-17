package sample.Controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Model.DBProcessor;
import sample.Model.DataTickets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class NewTicketController {

    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    @FXML
    public TextField textPhone;
    @FXML
    private TextField textFullName;
    @FXML
    private TextField textDevice;
    @FXML
    private TextField textModel;
    @FXML
    private TextArea textDefect;
    @FXML
    private TextArea textNote;
    @FXML
    private TextArea textCondition;
    @FXML
    private Button buttonSave;

    @FXML
    public void createNewTicket() throws SQLException {
        if (textPhone.getLength() > 0) {
            if (textFullName.getLength() > 0) {
                new DataTickets().createNewTicketWrite(textPhone.getText(), textFullName.getText(), textDevice.getText(), textModel.getText(), textDefect.getText(), textNote.getText(), textCondition.getText());
                mainMenuController.ticketList();
            } else {
                textFullName.setPromptText("Заполните поле");
            }
        } else {
            textPhone.setPromptText("Заполните поле");
        }

    }

//    public static void addMask(final TextField tf, final String mask) {
//        tf.setText(mask);
//        // addTextLimiter(tf, mask.length());
//
////        tf.textProperty().addListener(new ChangeListener<String>() {
////            @Override
////            public void changed(final ObservableValue<? extends String> ov,
////                                final String oldValue, final String newValue) {
////
////            }
////        });
//
//        tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(final KeyEvent e) {
//                int caretPosition = tf.getCaretPosition();
//                String value = stripMask(tf.getText(), mask);
//                tf.setText(merge(value, mask));
//                if (caretPosition < mask.length() - 1
//                        && mask.charAt(caretPosition) != ' '
//                        && e.getCode() != KeyCode.BACK_SPACE
//                        && e.getCode() != KeyCode.LEFT) {
//                    tf.positionCaret(caretPosition + 1);
//                } else {
//                    tf.positionCaret(caretPosition);
//                }
//            }
//        });
//    }
//
//    static String stripMask(String text, final String mask) {
//        final Set<String> maskChars = new HashSet<>();
//        for (int i = 0; i < mask.length(); i++) {
//            char c = mask.charAt(i);
//            if (c != ' ') {
//                maskChars.add(String.valueOf(c));
//            }
//        }
//        for (String c : maskChars) {
//            text = text.replace(c, "");
//        }
//        return text;
//    }
//
//    static String merge(final String value, final String mask) {
//        final StringBuilder sb = new StringBuilder(mask);
//        System.out.println(value);
//        int k = 0;
//        for (int i = 0; i < mask.length(); i++) {
//            if (mask.charAt(i) == ' ' && k < value.length()) {
//                sb.setCharAt(i, value.charAt(k));
//                k++;
//            }
//        }
//        return sb.toString();
//    }
    private ArrayList<Integer> indexCharMask = new ArrayList<>();
    private ArrayList<String> charMask = new ArrayList<>();

    private void maskToTextField(TextField textField, String mask){
        indexMask(mask);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent e) {
                if(textField.getCaretPosition() == 0){
                    textField.setText(charMask.get(0) + charMask.get(1));
                    textField.positionCaret(indexCharMask.get(0));
                }
            }
        });
    }

    private void indexMask(String mask){
        for (int i=0; i<mask.length(); i++){
            if (mask.charAt(i)!=' '){
                charMask.add(String.valueOf(mask.charAt(i)));
            }else{
                indexCharMask.add(i);
            }
        }
    }


    @FXML
    public void initialize() {

        //maskToTextField(textPhone, "8(   )-   -  -  ");
    }


    /**
     * НЕОБХОДИМО ВВЕСТИ ДОБАВИТЬ В ЛОГИКУ ДАННЫЙ МЕТОД
     **/
    public void textColumns() {
        String inputPriceStr = textPhone.getText();
        int length = textPhone.getText().length();
        final int MAX = 10;
        if (inputPriceStr.matches("[0-9]*") && length < MAX) {
        }
    }
}
