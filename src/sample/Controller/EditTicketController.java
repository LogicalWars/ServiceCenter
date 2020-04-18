package sample.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Enum.User;
import sample.Model.DataTickets;
import sample.Model.Print;
import sample.Model.Status;
import sample.Model.TicketLogs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

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
    List<ComboBox> listComboBox = new ArrayList<>();
    List<TextField> listTextField = new ArrayList<>();
    List<Integer> listValid = new ArrayList<>();
    List<String> listNameSpare = new ArrayList<>();
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
        dataTickets.getAllSparePartsRead(dataTickets.getIdTicket());

        if(dataTickets.getListValidSpareParts().size()!=0) {
            for (int in : dataTickets.getListValidSpareParts()) {
                listValid.add(in);
            }
        }else{
            listValid.add(0);
        }

        if(dataTickets.getListNameSpareParts().size()!=0) {
            for (String s: dataTickets.getListNameSpareParts()){
                listNameSpare.add(s);
            }
        }else{
            listNameSpare.add("");
        }



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
        repairPrice.setText(dataTickets.getRepairPriceTicket());
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
        repairPrice.textProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue.matches("\\d*")){repairPrice.setText(oldValue);}});

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

        /**************************************************************************************************************/

        /**ЧАТ*/

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

        /***************************************************************************************************************/



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
        /****************************************************************************************/

        /**Работа с запчастями*/

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
           listNameSpare.set(0,String.valueOf(newValue));
        });
        elementTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue.matches("\\d*")){elementTextField.setText(oldValue);}
            checkPrice(listTextField,repairPrice.getText());
        });
        repairPrice.textProperty().addListener((observable, oldValue, newValue) ->{
            checkPrice(listTextField,repairPrice.getText());
        });


        listComboBox.add(elementComboBox);
        listTextField.add(elementTextField);

        if(dataTickets.getListNameSpareParts().size()>0) {
            stockRead(dataTickets.getListNameSpareParts(), dataTickets.getListPriceSpareParts() , dataTickets.getListValidSpareParts());
        }
        checkPrice(listTextField,repairPrice.getText());
        /********************************************************************************************/

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

    private void checkPrice(List<TextField> listSpare, String repairPrice){
        int sp = 0;
        int rp = 0;
        for(int i=0; i<listSpare.size(); i++){
            if(!listSpare.get(i).getText().equals("")){sp = sp + Integer.parseInt(listSpare.get(i).getText());}
        }


        if(!repairPrice.equals("")){rp = Integer.parseInt(repairPrice);}
        int sum = sp + rp;
        totalPrice.setText(String.valueOf(sum));
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
        updateValid(dataTickets.getListNameSpareParts(),listNameSpare,listValid);
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
                        defect.getText(), note.getText(), condition.getText(), comment.getText(), Integer.parseInt(numberTicketText.getText()), listComboBox,listTextField, Integer.parseInt(repairPrice.getText()), listValid);
            } else {
                dataTickets.saveEditTicketWrite(dataTickets.getIdTicket(), Integer.parseInt(dataTickets.getIdStatusTicket()), phone.getText(), fullName.getText(), device.getText(), model.getText(),
                        defect.getText(), note.getText(), condition.getText(), comment.getText(), Integer.parseInt(numberTicketText.getText()), listComboBox,listTextField, Integer.parseInt(repairPrice.getText()), listValid);
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

    private int i = 4;


    /**ДОБАВИТЬ ЗАПЧАСТЬ*/
    @FXML
    void elementButton() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label("Запчасть");
        label.setPrefWidth(100.0);
        label.setMinWidth(100.0);
        hBox.setMargin(label, new Insets(0, 10, 0, 10));
        ComboBox comboBox = new ComboBox();
        comboBox.setEditable(true);
        comboBox.setMaxWidth(1.7976931348623157E308);
        hBox.setHgrow(comboBox, Priority.ALWAYS);
        TextField textField = new TextField();
        textField.setPrefWidth(70.0);
        textField.setMaxWidth(Region.USE_PREF_SIZE);
        textField.setMinWidth(Region.USE_PREF_SIZE);
        hBox.setMargin(textField, new Insets(0, 5, 0, 5));
        Button button = new Button();
        button.setText("-");
        button.setPrefWidth(25.0);
        button.setMinWidth(Region.USE_PREF_SIZE);

        hBox.getChildren().addAll(label, comboBox, textField, button);
        gridPane.add(hBox, 0, i);
        i++;
        listComboBox.add(comboBox);
        listTextField.add(textField);
        listValid.add(0);
        listNameSpare.add("");
        button.setOnMouseClicked(event -> {
            deleteRow(gridPane, gridPane.getRowIndex(hBox));
            i--;
            listComboBox.remove(gridPane.getRowIndex(hBox)-3);
            listTextField.remove(gridPane.getRowIndex(hBox)-3);
            listValid.remove(gridPane.getRowIndex(hBox)-3);
            listNameSpare.remove(gridPane.getRowIndex(hBox)-3);
            checkPrice(listTextField, repairPrice.getText());
        }
    );
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPrice(listTextField, repairPrice.getText());
        });

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
            listNameSpare.set(gridPane.getRowIndex(hBox)-3, String.valueOf(newValue));
        });
        }
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
        });


    }
    private int searchNameSpareId(List<String> name, String text){
        int i = 0;
        int id =0;
        for (String s:name){
            if(s.equals(text)){ id = i;}
            i++;
        }
        return id;
    }

    private void deleteRow(GridPane grid, final int row) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            // get index from child
            Integer rowIndex = GridPane.getRowIndex(child);
            // handle null values for index=0
            int r = rowIndex == null ? 0 : rowIndex;
            if (r > row) {
                // decrement rows for rows after the deleted row
                GridPane.setRowIndex(child, r-1);
            } else if (r == row) {
                // collect matching rows for deletion
                deleteNodes.add(child);
            }
        }
        // remove nodes from row
        grid.getChildren().removeAll(deleteNodes);
        if(dataTickets.getListNameSpareParts().size()> row-3){
            if(listValid.get(row-3) != 0){
                dataTickets.setUpdateAmountSpare(model.getText(), dataTickets.getListNameSpareParts().get(row-3));
                dataTickets.getListNameSpareParts().remove(row-3);
            }
        }
    }

    private void stockRead(List<String> name, List<String> price, List<Integer> valid){
        elementComboBox.setValue(name.get(0));
        elementTextField.setText(price.get(0));
     for(int idR = 1; idR<name.size(); idR++){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label("Запчасть");
        label.setPrefWidth(100.0);
        label.setMinWidth(100.0);
        hBox.setMargin(label, new Insets(0, 10, 0, 10));
        ComboBox comboBox = new ComboBox();
        comboBox.setEditable(true);
        comboBox.setMaxWidth(1.7976931348623157E308);
        comboBox.setValue(name.get(idR));
        hBox.setHgrow(comboBox, Priority.ALWAYS);
        TextField textField = new TextField();
        textField.setPrefWidth(70.0);
        textField.setMaxWidth(Region.USE_PREF_SIZE);
        textField.setMinWidth(Region.USE_PREF_SIZE);
        textField.setText(price.get(idR));
        hBox.setMargin(textField, new Insets(0, 5, 0, 5));
        Button button = new Button();
        button.setText("-");
        button.setPrefWidth(25.0);
        button.setMinWidth(Region.USE_PREF_SIZE);
        button.setOnMouseClicked(event -> {
                deleteRow(gridPane, gridPane.getRowIndex(hBox));
                i--;
                listComboBox.remove(gridPane.getRowIndex(hBox)-3);
                listTextField.remove(gridPane.getRowIndex(hBox)-3);
                listValid.remove(gridPane.getRowIndex(hBox)-3);
                listNameSpare.remove(gridPane.getRowIndex(hBox)-3);
                checkPrice(listTextField,repairPrice.getText());

        });
        hBox.getChildren().addAll(label, comboBox, textField, button);
        gridPane.add(hBox, 0, i);
        i++;
         listComboBox.add(comboBox);
         listTextField.add(textField);
         textField.textProperty().addListener((observable, oldValue, newValue) -> {
             checkPrice(listTextField, repairPrice.getText());
         });
         if("comboBox_" + i != null){
             int idNameModelTwo=0;
             List<Integer> idModel = new ArrayList();
             for(String s: dataTickets.getModelList()) {
                 if (s.equals(model.getText())){
                     idModel.add(idNameModelTwo);
                     comboBox.setPromptText("Имеется запчасть");
                     comboBox.getItems().add(dataTickets.getNameList().get(idNameModelTwo));
                 }
                 idNameModelTwo++;}
             comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                 textField.setText(String.valueOf(idPrice((String) newValue,idModel)));
                 listNameSpare.set(gridPane.getRowIndex(hBox)-3, String.valueOf(newValue));
             });
         }
         textField.textProperty().addListener((observable, oldValue, newValue) -> {

         });

     }

    }
    private void updateValid(List<String> name_DB, List<String> name_API, List<Integer> listValid_API){
        Set<String> uniqueName = new LinkedHashSet<>(); //Коллекция уникальных значений в списке name_API
        ArrayList<ArrayList<Integer>> listNameNumbers = new ArrayList<>();
        /*Заполнение мнимых коллекций*/
        int i=0;
        for (String d: name_API) {
            if(uniqueName.add(d)){
                listNameNumbers.add(new ArrayList<>());
                int y=0;
                for (String s: uniqueName) {
                    if(s.equals(d)){
                        listNameNumbers.get(y).add(i);
                    }
                    y++;
                }
            }else{
                int y=0;
                for (String s: uniqueName) {
                    if(s.equals(d)){
                        listNameNumbers.get(y).add(i);
                    }
                    y++;
                }
            }
            i++;
        }
        /*Корректировка списка валидации*/
        int k=0;
        for (String s: uniqueName){
            int amount = amountInList(name_DB, s);
            if(amount!=0) {
                int l=0;
                for (int j: listNameNumbers.get(k)) {
                    if(amount>l){
                        listValid_API.set(j, 1);
                    }else{
                        listValid_API.set(j, 0);
                    }
                    l++;
                }
            }else{
                for(int j: listNameNumbers.get(k)) {
                    listValid_API.set(j, 0);
                }
            }
            k++;
        }
    }

    private int amountInList(List<String> name, String text){
        int i=0;
        for(String s:name){
            if(s.equals(text))i++;
        }
        return i;
    }



}
