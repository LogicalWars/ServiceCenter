package sample.Controller;

import javafx.event.Event;
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
import sample.Model.*;
import sample.Model.DB_Read.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class EditTicketController extends HotKeys{

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
    @FXML
    private Button addSparePart;
    @FXML
    public Button printButton;

    DataTickets dataTickets = new DataTickets();
    ListOfStatus listOfStatus = new ListOfStatus();
    ListOfLogs listOfLogs = new ListOfLogs();

    private List<ComboBox> listComboBox = new ArrayList<>();
    private List<TextField> listTextField = new ArrayList<>();
    private List<Integer> listValid = new ArrayList<>();
    private List<String> listNameSpare = new ArrayList<>();
    private int idRowGridPane = 4;
    TicketData ticketData = new TicketData();
    ListOfStock listOfStock = new ListOfStock();
    ListOfSpareParts listOfSpareParts = new ListOfSpareParts();

    @FXML
    public void initialize() {
        //Работа с слушателем горячих клавиш




    try {
            ticketData.editTicketRead(TicketListController.idRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listOfStatus.statusUploadRead();
        listOfStock.stockListDataRead();
        listOfLogs.ticketLogsRead(ticketData.getIdTicket());
        listOfSpareParts.getAllSparePartsRead(ticketData.getIdTicket());

        /*Заполнение данными API*/

        numberLog.setCellValueFactory(new PropertyValueFactory<>("idLog"));
        dateLog.setCellValueFactory(new PropertyValueFactory<>("dateLog"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));

        numberTicket.setText(String.valueOf(ticketData.getNumberTicket()));
        numberTicketText.setText(String.valueOf(ticketData.getNumberTicket()));
        phone.setText(ticketData.getPhoneNumber());
        fullName.setText(ticketData.getFullName());
        device.setText(ticketData.getDeviceTicket());
        model.setText(ticketData.getModelTicket());
        defect.setText(ticketData.getDefectTicket());
        note.setText(ticketData.getNoteTicket());
        condition.setText(ticketData.getConditionTicket());
        comment.setText(ticketData.getCommentTicket());
        comment.appendText("");
        status.setText(ticketData.getStatusTicket());
        date.setText(ticketData.getDateCreateTicket());
        dateClose.setText(dataTickets.getDateCloseTicket());
        statusComboBox.setItems(listOfStatus.getStatusUpload());
        repairPrice.setText(ticketData.getRepairPriceTicket());
        tableLogs.setItems(new ListOfLogs().getTicketLogs());

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
        if(User.USER == User.OPERATOR){
            repairPrice.setEditable(false);
            repairPrice.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
            addSparePart.setDisable(true);

        }

        /*Работа с запчастями*/

        if(listOfSpareParts.getListNameSpareParts().size()>0) {
            stockRead(listOfSpareParts.getListNameSpareParts(), listOfSpareParts.getListPriceSpareParts() , listOfSpareParts.getListValidSpareParts());
        }
        repairPrice.textProperty().addListener((observableValue, oldValue, newValue) -> {
            calculationPrice(listTextField,repairPrice.getText());
            if(oldValue == null){
                oldValue = " ";
                if(!oldValue.equals(newValue)){saveButton.setDisable(false);}
            }else{if(!oldValue.equals(newValue)){saveButton.setDisable(false);}}
        });
        calculationPrice(listTextField,repairPrice.getText());

        changeModel();

    }

    /**Метод отрабатывает при нажатии на кнопку Save*/

    @FXML
    public void saveEditTicket() {

        updateValid(listOfSpareParts.getListNameSpareParts(),listNameSpare,listValid);

        int idStatus = 1;
        int idStatusTrue = 0;
        for(String s: listOfStatus.getAllStatus()){
            if (!s.equals(String.valueOf(statusComboBox.getValue()))){
                idStatus++;
            }else{
                idStatusTrue =idStatus;
            }
        }
        if (statusComboBox.getSelectionModel().getSelectedIndex() + 1 != 0) {
            dataTickets.saveEditTicketWrite(ticketData.getIdTicket(), idStatusTrue, phone.getText(), fullName.getText(), device.getText(), model.getText(),
                    defect.getText(), note.getText(), condition.getText(), comment.getText(), Integer.parseInt(numberTicketText.getText()), listComboBox,listTextField, Integer.parseInt(repairPrice.getText()), listValid);
        } else {
            dataTickets.saveEditTicketWrite(ticketData.getIdTicket(), Integer.parseInt(ticketData.getIdStatusTicket()), phone.getText(), fullName.getText(), device.getText(), model.getText(),
                    defect.getText(), note.getText(), condition.getText(), comment.getText(), Integer.parseInt(numberTicketText.getText()), listComboBox,listTextField, Integer.parseInt(repairPrice.getText()), listValid);
        }
        String getStatusComboBox;

        if (statusComboBox.getValue() == null) {
            getStatusComboBox = ticketData.getStatusTicket();
        } else {
            getStatusComboBox = String.valueOf(statusComboBox.getValue());
        }

        mainMenuController.ticketList();
        dataTickets.ticketLogsWrite(ticketData.getIdTicket(),
                ticketData.getPhoneNumber(),
                phone.getText(),
                ticketData.getFullName(),
                fullName.getText(),
                ticketData.getStatusTicket(),
                getStatusComboBox,
                ticketData.getDeviceTicket(),
                device.getText(),
                ticketData.getModelTicket(),
                model.getText(),
                ticketData.getDefectTicket(),
                defect.getText(),
                ticketData.getNoteTicket(),
                note.getText(),
                ticketData.getConditionTicket(),
                condition.getText(),
                ticketData.getCommentTicket(),
                comment.getText(),
                String.valueOf(ticketData.getNumberTicket()),
                numberTicketText.getText(),
                User.userID);
    }

    /**ДОБАВИТЬ ЗАПЧАСТЬ*/

    @FXML
    public void addSparePart() {
        addNewSparePart(gridPane, listComboBox, listTextField, listValid, listNameSpare, listOfSpareParts.getListPriceSpareParts(), 0, false);
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
    public void printed() throws IOException {
        Print print = new Print();
        print.printed();
    }


    /**Определяет idЦены, возвращает int
     * @param text - наименование запчасти
     * @param idM - массив цен*/

    private int idPrice(String text, List<Integer> idM){
        int id = 0;
        int idPrice = 0;
        for(String s: listOfStock.getNameList()) {
                for(int i: idM){
                    if(i == id){
                        if (s.equals(text)){
                            listOfStock.getNameList().indexOf(model.getText());
                            idPrice = listOfStock.getPriceList().get(id);
                        }
                    }
                }

            id++;
        }
        return idPrice;
    }

    /**Проверка полей для включения кнопки Save*/

    public boolean check() {
        int numberTicketC =String.valueOf(ticketData.getNumberTicket()).compareTo(numberTicketText.getText());
        int phoneC = ticketData.getPhoneNumber().compareTo(phone.getText());
        int conditionC = ticketData.getConditionTicket().compareTo(condition.getText());
        int fullNameC = ticketData.getFullName().compareTo(fullName.getText());
        int deviceC = ticketData.getDeviceTicket().compareTo(device.getText());
        int modelC = ticketData.getModelTicket().compareTo(model.getText());
        int defectC = ticketData.getDefectTicket().compareTo(defect.getText());
        int noteC = ticketData.getNoteTicket().compareTo(note.getText());
        int commentC = ticketData.getCommentTicket().compareTo(comment.getText());
        String statusD = "null";
        int statusC = statusD.compareTo(String.valueOf(statusComboBox.getValue()));
        if (numberTicketC == 0 && phoneC == 0 && conditionC == 0 && fullNameC == 0 && deviceC == 0 && modelC == 0 && defectC == 0 && noteC == 0 && statusC == 0 && commentC == 0) {
            saveButton.setDisable(true);
            } else {
            saveButton.setDisable(false);
            return true;
        }
        return false;
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
        if(listOfSpareParts.getListNameSpareParts().size()> row-4){
            if(listValid.get(row-4) != 0){
                dataTickets.setUpdateAmountSpare(model.getText(), listOfSpareParts.getListNameSpareParts().get(row-4));
                listOfSpareParts.getListNameSpareParts().remove(row-4);
            }
        }
    }

    /**Отрисовка запчастей при загрузки с базы
     * @param name - коллекция наименования запчастей
     * @param price - коллекция цен запчастей
     * @param valid - коллекция валидности запчастей*/

    private void stockRead(List<String> name, List<String> price, List<Integer> valid){


        listValid.addAll(listOfSpareParts.getListValidSpareParts());
        listNameSpare.addAll(listOfSpareParts.getListNameSpareParts());
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
        for(String s: listOfStock.getModelList()) {
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
                    saveButton.setDisable(false);
                }
        );
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            calculationPrice(listTextField, repairPrice.getText());
            if(oldValue == null){
                oldValue =" ";
                if(!oldValue.equals(newValue)){saveButton.setDisable(false);}
            }else{if(!oldValue.equals(newValue)){saveButton.setDisable(false);}}

        });

        int idNameModelTwo=0;
        List<Integer> idModel = new ArrayList();
        for(String s: listOfStock.getModelList()) {
            if (s.equals(model.getText())){
                idModel.add(idNameModelTwo);
                comboBox.getItems().add(listOfStock.getNameList().get(idNameModelTwo));
            }
            idNameModelTwo++;
        }
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(String.valueOf(idPrice((String) newValue,idModel)));
            listNameSpare.set(grid.getRowIndex(hBox)-4, String.valueOf(newValue));
            if(oldValue == null){
                oldValue = " ";
                if(!oldValue.equals(newValue)){saveButton.setDisable(false);}
            }else{if(!oldValue.equals(newValue)){saveButton.setDisable(false);}}

        });

        if(User.USER == User.OPERATOR){
            comboBox.setEditable(false);
            comboBox.setDisable(true);
            comboBox.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
            textField.setEditable(false);
            textField.setDisable(true);
            textField.setStyle("-fx-text-fill: rgba(116,32,0,0.91)");
            button.setDisable(true);
        }
    }

}
