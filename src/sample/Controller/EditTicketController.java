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
    private GridPane gridPane;
    @FXML
    private Label haveSpareLabel;

    DataTickets dataTickets = new DataTickets();
    private List<ComboBox> listComboBox = new ArrayList<>();
    private List<TextField> listTextField = new ArrayList<>();
    private List<Integer> listValid = new ArrayList<>();
    private List<String> listNameSpare = new ArrayList<>();
    private int idRowGridPane = 4;


    @FXML
    public void initialize() {

        try {
            dataTickets.editTicketRead(TicketListController.idRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataTickets.statusUploadRead();
        dataTickets.stockListDataRead();
        dataTickets.ticketLogsRead(dataTickets.getIdTicket());
        dataTickets.getAllSparePartsRead(dataTickets.getIdTicket());

        /*Заполнение данными API*/

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

        /*Слушатель на изменение № заявки (только цифры и уникальность)*/

        numberTicketText.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){numberTicketText.setText(oldValue);}
            if(!dataTickets.allNumberTicket(StringToInt(numberTicketText.getText()))) {
                numberTicketText.setText(oldValue);
            }
            check();
        });

        /*Слушатели с проверками на элементы API*/

        phone.textProperty().addListener((observable, oldValue, newValue) -> check());
        fullName.textProperty().addListener((observable, oldValue, newValue) -> check());
        device.textProperty().addListener((observable, oldValue, newValue) -> check());
        model.textProperty().addListener((observable, oldValue, newValue) -> {check();changeModel();});
        defect.textProperty().addListener((observable, oldValue, newValue) -> check());
        note.textProperty().addListener((observable, oldValue, newValue) -> check());
        condition.textProperty().addListener((observable, oldValue, newValue) -> check());
        comment.textProperty().addListener((observable, oldValue, newValue) -> check());
        statusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> check());
        repairPrice.textProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue.matches("\\d*")){repairPrice.setText(oldValue);}});

        /*Слушатель на открытие логов*/

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

        /*ЧАТ*/

        comment.setEditable(false);
        comment.setScrollTop(Double.MAX_VALUE);

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

        /*Ограничения доступа по Ролям*/

        switch (status.getText()){
            case "Диагностика":
                switch (User.USER){
                    case MASTER:  numberTicketText.setDisable(true);
                                  statusComboBox.getItems().remove(2,7);
                                  statusComboBox.getItems().remove(0);break;
                    case OPERATOR: numberTicketText.setDisable(true);
                                   statusComboBox.setDisable(true);break;
                }break;
            case "На согласовании":
                switch (User.USER){
                    case ADMIN:;break;
                    default: setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(5,7);
                        statusComboBox.getItems().remove(3);
                        statusComboBox.getItems().remove(0,2);break;

                }break;
            case "Согласованно":
            case "Отказ от ремонта":
                switch (User.USER){
                    case MASTER:  setEditableNumPhoNamDevModDefConNot();
                                  statusComboBox.getItems().remove(4,6);
                                  statusComboBox.getItems().remove(0,3);break;
                    case OPERATOR: setEditableNumPhoNamDevModDefConNot();
                                   statusComboBox.setDisable(true);break;
                }break;
            case "Готов":
                switch (User.USER){
                    case MASTER:  setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(0,6);
                        break;
                    case OPERATOR: setEditableNumPhoNamDevModDefConNot();break;
                }break;
            case "Готов к выдаче":
                switch (User.USER){
                    case ADMIN:;break;
                    default:setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.getItems().remove(6);
                        statusComboBox.getItems().remove(0,5);break;
                }break;
            case "Выдан":
                switch (User.USER){
                    case ADMIN:;break;
                    default:setEditableNumPhoNamDevModDefConNot();
                        statusComboBox.setDisable(true);break;
                }break;
        }

        /*Работа с запчастями*/

        if(dataTickets.getListNameSpareParts().size()>0) {
            stockRead(dataTickets.getListNameSpareParts(), dataTickets.getListPriceSpareParts() , dataTickets.getListValidSpareParts());
        }
        calculationPrice(listTextField,repairPrice.getText());

        changeModel();
    }

    /**Метод отрабатывает при нажатии на кнопку Save*/

    @FXML
    public void saveEditTicket() {

        updateValid(dataTickets.getListNameSpareParts(),listNameSpare,listValid);

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

    /**ДОБАВИТЬ ЗАПЧАСТЬ*/

    @FXML
    public void addSparePart() {
        addNewSparePart(gridPane, listComboBox, listTextField, listValid, listNameSpare, dataTickets.getListPriceSpareParts(), 0, false);
    }

    /**Метод отрабатывает при нажатии на кнопку Предварительный просмотр*/

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

    /**Вывод на печать*/

    @FXML
    private void printed() throws IOException {
        Print print = new Print();
        print.printed();
    }

    /**Определяет idЦены, возвращает int
     * @param text - наименование запчасти
     * @param idM - массив цен*/

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

    /**Проверка полей для включения кнопки Save*/

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

    /**Расчет обшей цены
     * @param listSpare - коллекция цен запчастей
     * @param repairPrice - цена ремонта*/

    private void calculationPrice(List<TextField> listSpare, String repairPrice){
        int sp = 0;
        int rp = 0;
        for(int i=0; i<listSpare.size(); i++){
            if(!listSpare.get(i).getText().equals("")){sp = sp + Integer.parseInt(listSpare.get(i).getText());}
        }


        if(!repairPrice.equals("")){rp = Integer.parseInt(repairPrice);}
        int sum = sp + rp;
        totalPrice.setText(String.valueOf(sum));
    }

    /**При пустой строке возвращает 0, иначе число
     * @param inputString - строка*/
    private int StringToInt(String inputString){
        try{
            return Integer.parseInt(inputString);
        }catch(NumberFormatException ex){
            return 0; // при пустой строке вернем "0"
        }
    }

    /**Метод выключает элементы и изменяет стилистику*/

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

    /**Метод удаления запчасти
     * @param grid - объект gridPane
     * @param row - id строки grid*/

    private void deleteRow(GridPane grid, final int row) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(child);
            int r = rowIndex == null ? 0 : rowIndex;
            if (r > row) {
                GridPane.setRowIndex(child, r-1);
            } else if (r == row) {
                deleteNodes.add(child);
            }
        }
        grid.getChildren().removeAll(deleteNodes);
        if(dataTickets.getListNameSpareParts().size()> row-4){
            if(listValid.get(row-4) != 0){
                dataTickets.setUpdateAmountSpare(model.getText(), dataTickets.getListNameSpareParts().get(row-4));
                dataTickets.getListNameSpareParts().remove(row-4);
            }
        }
    }

    /**Отрисовка запчастей при загрузки с базы
     * @param name - коллекция наименования запчастей
     * @param price - коллекция цен запчастей
     * @param valid - коллекция валидности запчастей*/

    private void stockRead(List<String> name, List<String> price, List<Integer> valid){


        listValid.addAll(dataTickets.getListValidSpareParts());
        listNameSpare.addAll(dataTickets.getListNameSpareParts());
        for(int idR = 0; idR<name.size(); idR++) {
            addNewSparePart(gridPane, listComboBox, listTextField, listValid, listNameSpare, price, idR, true);
        }

    }


    /**Метод изменяет валидность запчастей, сравнивая с данными из BD
     * @param name_DB - коллекция наименования запчастей из BD*
     * @param name_API - коллекция наименования запчастей с API*
     * @param listValid_API - валидация запчастей с API*/


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

    /**Подсчет кол-ва совпадний в коллекции, возвращает int
     * @param name - коллекция наименования запчастей*
     * @param text - наименование запчасти*/

    private int amountInList(List<String> name, String text){
        int i=0;
        for(String s:name){
            if(s.equals(text))i++;
        }
        return i;
    }

    /**Меняет Label на наличие запчастей*/

    private void changeModel(){
        for(String s: dataTickets.getModelList()) {
            if (s.equals(model.getText())){
                haveSpareLabel.setText("Имеется запчасть");
                break;
            }else{
                haveSpareLabel.setText("");
            }
        }
    }

    private void addNewSparePart(GridPane grid, List<ComboBox> listComboBox, List<TextField> listTextField, List<Integer> listValid, List<String> listNameSpare, List<String> price, int idR, boolean readFromDB){

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
        grid.add(hBox, 0, idRowGridPane);
        idRowGridPane++;


        if(readFromDB){
            comboBox.setValue(listNameSpare.get(idR));
            textField.setText(price.get(idR));
            listComboBox.add(comboBox);
            listTextField.add(textField);
        }else{
            listComboBox.add(comboBox);
            listTextField.add(textField);
            listValid.add(0);
            listNameSpare.add("");
        }




        /*слушатель на кнопку "удалить запчасть"*/

        button.setOnMouseClicked(event -> {
                    deleteRow(grid, grid.getRowIndex(hBox));
                    idRowGridPane--;
                    listComboBox.remove(grid.getRowIndex(hBox)-4);

                    listTextField.remove(grid.getRowIndex(hBox)-4);
                    listValid.remove(grid.getRowIndex(hBox)-4);
                    listNameSpare.remove(grid.getRowIndex(hBox)-4);
                    calculationPrice(listTextField, repairPrice.getText());
                }
        );
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            calculationPrice(listTextField, repairPrice.getText());
        });

        int idNameModelTwo=0;
        List<Integer> idModel = new ArrayList();
        for(String s: dataTickets.getModelList()) {
            if (s.equals(model.getText())){
                idModel.add(idNameModelTwo);
                comboBox.getItems().add(dataTickets.getNameList().get(idNameModelTwo));
            }
            idNameModelTwo++;
        }
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(String.valueOf(idPrice((String) newValue,idModel)));
            listNameSpare.set(grid.getRowIndex(hBox)-4, String.valueOf(newValue));
        });
    }

}
