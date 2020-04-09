package sample.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Enum.User;
import sample.Model.DataTickets;
import sample.Model.Print;
import sample.Model.Status;
import sample.Model.TicketLogs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditTicketController {

    public static int idLogs;

    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    @FXML
    private Label numberTicket;
    @FXML
    private TextField numberTicketText;
    @FXML
    private TextField phone;
    @FXML
    private TextField fullName;
    @FXML
    private TextField repairPrice;
    @FXML
    private Label totalPrice;
    @FXML
    private TextField device;
    @FXML
    private TextField model;
    @FXML
    private TextArea defect;
    @FXML
    private TextArea note;
    @FXML
    private TextArea condition;
    @FXML
    private TextArea commentText;
    @FXML
    private TextArea comment;
    @FXML
    private Button saveButton;
    @FXML
    private Label status;
    @FXML
    private Label date;
    @FXML
    private Label dateClose;
    @FXML
    private ComboBox<Status> statusComboBox;
    @FXML
    private TableView<TicketLogs> tableLogs;
    @FXML
    private TableColumn<TicketLogs, Integer> numberLog;
    @FXML
    private TableColumn<TicketLogs, String> dateLog;
    @FXML
    private TableColumn<TicketLogs, String> user;
    @FXML
    private SplitPane editTicketViewPane;
    @FXML
    private ComboBox<String> elementComboBox;
    @FXML
    private TextField elementTextField;
    @FXML
    private Button elementButton;
    @FXML
    private GridPane gridPane;

    DataTickets dataTickets = new DataTickets();
    @FXML
    public void initialize() {
        comment.setEditable(false);

        comment.setScrollTop(Double.MAX_VALUE);
        try {
            dataTickets.editTicketRead(TicketListController.idRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataTickets.statusUploadRead();
        dataTickets.stockListDataRead();
        dataTickets.ticketLogsRead(dataTickets.getIdTicket());

        numberLog.setCellValueFactory(new PropertyValueFactory<>("idLog"));
        dateLog.setCellValueFactory(new PropertyValueFactory<>("dateLog"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));

        numberTicket.setText(String.valueOf(dataTickets.getNumberTicket()));
        numberTicketText.setText(String.valueOf(dataTickets.getNumberTicket()));
        phone.setText(dataTickets.getPhoneNumber());
        fullName.setText(dataTickets.getFullName());
        device.setText(dataTickets.getDeviceTicket());
        model.setText(dataTickets.getModelTicket());
        defect.setText(dataTickets.getDefectTicket());
        note.setText(dataTickets.getNoteTicket());
        condition.setText(dataTickets.getConditionTicket());
        comment.setText(dataTickets.getCommentTicket());
        comment.appendText("");
        status.setText(dataTickets.getStatusTicket());
        date.setText(dataTickets.getDateCreateTicket());
        dateClose.setText(dataTickets.getDateCloseTicket());
        statusComboBox.setItems(dataTickets.getStatusUpload());
        tableLogs.setItems(dataTickets.getTicketLogs());

        saveButton.setDisable(true);

        numberTicketText.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){numberTicketText.setText(oldValue);}
            if(!dataTickets.allNumberTicket(StringToInt(numberTicketText.getText()))) {
                numberTicketText.setText(oldValue);
            }
            check();
        });
        phone.textProperty().addListener((observable, oldValue, newValue) -> check());
        fullName.textProperty().addListener((observable, oldValue, newValue) -> check());
        device.textProperty().addListener((observable, oldValue, newValue) -> check());
        model.textProperty().addListener((observable, oldValue, newValue) -> check());
        defect.textProperty().addListener((observable, oldValue, newValue) -> check());
        note.textProperty().addListener((observable, oldValue, newValue) -> check());
        condition.textProperty().addListener((observable, oldValue, newValue) -> check());
        comment.textProperty().addListener((observable, oldValue, newValue) -> check());
        statusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> check());

        tableLogs.setRowFactory(tv -> {
            TableRow<TicketLogs> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    int selectIndex = row.getIndex();
                    TicketLogs selectLog = tableLogs.getItems().get(selectIndex);
                    idLogs = selectLog.getIdLogFromDB();
                    mainMenuController.dialogLogs();
                }
            });
            return row;
        });



        KeyCodeCombination kComb = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHIFT_DOWN);
        commentText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (kComb.match(keyEvent)&&!commentText.getText().equals("")){
                    String chat;
                    chat = comment.getText() + "\n" + dataTickets.pressedComment(commentText.getText() + "\n");
                    comment.setText(chat);
                    comment.appendText("");

                    commentText.setText("");
                    dataTickets.saveComment(chat, Integer.parseInt(numberTicket.getText()));
                }
            }
        });



        switch (status.getText()){
            case "Диагностика":
                switch (User.USER){
                    case MASTER:  numberTicketText.setDisable(true);
                                  statusComboBox.getItems().remove(2,7);
                                  statusComboBox.getItems().remove(0);break;
                    case OPERATOR: numberTicketText.setDisable(true);
                                   statusComboBox.setDisable(true);break;
                };break;
            case "На согласовании":
                switch (User.USER){
                    case ADMIN:;break;
                    default: setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(5,7);
                        statusComboBox.getItems().remove(3);
                        statusComboBox.getItems().remove(0,2);break;

                };break;
            case "Согласованно":
                switch (User.USER){
                    case MASTER:  setEditableNumPhoNamDevModDefConNot();
                                  statusComboBox.getItems().remove(4,6);
                                  statusComboBox.getItems().remove(0,3);break;
                    case OPERATOR: setEditableNumPhoNamDevModDefConNot();
                                   statusComboBox.setDisable(true);break;
                };break;
            case "Готов":
                switch (User.USER){
                    case MASTER:  setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(0,6);
                        break;
                    case OPERATOR: setEditableNumPhoNamDevModDefConNot();break;
                };break;
            case "Отказ от ремонта":
                switch (User.USER){
                    case MASTER:  setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(4,6);
                        statusComboBox.getItems().remove(0,3);break;
                    case OPERATOR: setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.setDisable(true);break;
                };break;
            case "Готов к выдаче":
                switch (User.USER){
                    case ADMIN:;break;
                    default:setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(6);
                        statusComboBox.getItems().remove(0,5);break;
                };break;
            case "Выдан":
                switch (User.USER){
                    case ADMIN:;break;
                    default:setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.setDisable(true);break;
                };break;
        }
        int idNameModel=0;
        List<Integer> idModel = new ArrayList();
        for(String s: dataTickets.getModelList()) {
            if (s.equals(model.getText())){
                idModel.add(idNameModel);
                elementComboBox.setPromptText("Имеется запчасть");
                elementComboBox.getItems().add(dataTickets.getNameList().get(idNameModel));
            }
            idNameModel++;
        }

        elementComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
           elementTextField.setText(String.valueOf(idPrice(newValue,idModel)));

        });


    }


    private int idPrice(String text, List<Integer> idM){
        int id = 0;
        int idPrice = 0;
        for(String s: dataTickets.getNameList()) {
                for(int i: idM){
                    if(i == id){
                        if (s.equals(text)){
                            dataTickets.getNameList().indexOf(model.getText());
                            idPrice = dataTickets.getPriceList().get(id);
                        }
                    }
                }

            id++;
        }
        return idPrice;
    }

    @FXML
    private void printed() throws IOException {
        Print print = new Print();
        print.printed();
    }

    private void check() {
        int numberTicketC =String.valueOf(dataTickets.getNumberTicket()).compareTo(numberTicketText.getText());
        int phoneC = dataTickets.getPhoneNumber().compareTo(phone.getText());
        int conditionC = dataTickets.getConditionTicket().compareTo(condition.getText());
        int fullNameC = dataTickets.getFullName().compareTo(fullName.getText());
        int deviceC = dataTickets.getDeviceTicket().compareTo(device.getText());
        int modelC = dataTickets.getModelTicket().compareTo(model.getText());
        int defectC = dataTickets.getDefectTicket().compareTo(defect.getText());
        int noteC = dataTickets.getNoteTicket().compareTo(note.getText());
        int commentC = dataTickets.getCommentTicket().compareTo(comment.getText());
        String statusD = "null";
        int statusC = statusD.compareTo(String.valueOf(statusComboBox.getValue()));
        if (numberTicketC == 0 && phoneC == 0 && conditionC == 0 && fullNameC == 0 && deviceC == 0 && modelC == 0 && defectC == 0 && noteC == 0 && statusC == 0 && commentC == 0) {
            saveButton.setDisable(true);
        } else {
            saveButton.setDisable(false);
        }
    }
    private int StringToInt(String inputString){
        try{
            return Integer.parseInt(inputString);
        }catch(NumberFormatException ex){
            return 0; // при пустой строке вернем "0"
        }
    }

    int idStatus;

    @FXML
    public void saveEditTicket() {
        listComboBox.add(elementComboBox);
        listTextField.add(elementTextField);
        /**
         * Проверка id статуса из массива getAllStatus()
         */

        int idStatus = 1;
        int idStatusTrue = 0;
        for(String s: dataTickets.getAllStatus()){
            if (!s.equals(String.valueOf(statusComboBox.getValue()))){
                idStatus++;
            }else{
                idStatusTrue =idStatus;
            }
        }
            if (statusComboBox.getSelectionModel().getSelectedIndex() + 1 != 0) {
                dataTickets.saveEditTicketWrite(dataTickets.getIdTicket(), idStatusTrue, phone.getText(), fullName.getText(), device.getText(), model.getText(),
                        defect.getText(), note.getText(), condition.getText(), comment.getText(), Integer.parseInt(numberTicketText.getText()), listComboBox,listTextField);
            } else {
                dataTickets.saveEditTicketWrite(dataTickets.getIdTicket(), Integer.parseInt(dataTickets.getIdStatusTicket()), phone.getText(), fullName.getText(), device.getText(), model.getText(),
                        defect.getText(), note.getText(), condition.getText(), comment.getText(), Integer.parseInt(numberTicketText.getText()), listComboBox,listTextField);
            }
        String getStatusComboBox;

        if (statusComboBox.getValue() == null) {
            getStatusComboBox = dataTickets.getStatusTicket();
        } else {
            getStatusComboBox = String.valueOf(statusComboBox.getValue());
        }

        mainMenuController.ticketList();
        dataTickets.ticketLogsWrite(dataTickets.getIdTicket(),
                dataTickets.getPhoneNumber(),
                phone.getText(),
                dataTickets.getFullName(),
                fullName.getText(),
                dataTickets.getStatusTicket(),
                getStatusComboBox,
                dataTickets.getDeviceTicket(),
                device.getText(),
                dataTickets.getModelTicket(),
                model.getText(),
                dataTickets.getDefectTicket(),
                defect.getText(),
                dataTickets.getNoteTicket(),
                note.getText(),
                dataTickets.getConditionTicket(),
                condition.getText(),
                dataTickets.getCommentTicket(),
                comment.getText(),
                String.valueOf(dataTickets.getNumberTicket()),
                numberTicketText.getText(),
                User.USER.ordinal()+1);
    }
    @FXML
    public void printPreview(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/PrintPreview.fxml"));
            loader.setController(new PrintPreviewController());
            Parent pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setEditableNumPhoNamDevModDefConNot(){
        numberTicketText.setEditable(false);
        phone.setEditable(false);
        fullName.setEditable(false);
        device.setEditable(false);
        model.setEditable(false);
        defect.setEditable(false);
        condition.setEditable(false);
        note.setEditable(false);
        numberTicketText.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        phone.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        fullName.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        defect.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        device.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        model.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        condition.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
        note.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");

    }

    private int i = 3;
    List<ComboBox> listComboBox = new ArrayList<>();
    List<TextField> listTextField = new ArrayList<>();


    @FXML
    void elementButton() {
        HBox hBox = new HBox();
        Label label = new Label("Запчасть");
        ComboBox comboBox = new ComboBox();
        comboBox.setEditable(true);
        TextField textField = new TextField();
        hBox.getChildren().addAll(label, comboBox, textField);
        gridPane.add(hBox, 0, i);
        i++;

        listComboBox.add(comboBox);
        listTextField.add(textField);

        if("comboBox_" + i != null){
            int idNameModelTwo=0;
            List<Integer> idModel = new ArrayList();
            for(String s: dataTickets.getModelList()) {
                if (s.equals(model.getText())){
                    idModel.add(idNameModelTwo);
                    comboBox.setPromptText("Имеется запчасть");
                    comboBox.getItems().add(dataTickets.getNameList().get(idNameModelTwo));
                }
            idNameModelTwo++;
        }
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(String.valueOf(idPrice((String) newValue,idModel)));

        });

        }

    }

}
