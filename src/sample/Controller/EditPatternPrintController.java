package sample.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Model.DataTickets;

public class EditPatternPrintController {
    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
    @FXML
    private Label titleLabel;

    @FXML
    private TextArea titleTextArea;

    @FXML
    private Label orderLabel;

    @FXML
    private TextField orderTextField;

    @FXML
    private Label fullNameLabel;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Label noteLabel;

    @FXML
    private TextField noteTextField;

    @FXML
    private Label defectLabel;

    @FXML
    private TextField defectTextField;

    @FXML
    private Label dateLabel;

    @FXML
    private TextField dateTextField;

    @FXML
    private Label deviceLabel;

    @FXML
    private TextField deviceTextField;

    @FXML
    private Label modelLabel;

    @FXML
    private TextField modelTextField;

    @FXML
    private Label conditionLabel;

    @FXML
    private TextField conditionTextField;

    @FXML
    private Label rulesLabel;

    @FXML
    private TextArea rulesTextArea;

    @FXML
    private Label signAcceptLabel;

    @FXML
    private TextField signAcceptTextField;

    @FXML
    private Label signPassedLabel;

    @FXML
    private TextField signPassedTextField;

    @FXML
    private Button savePatternPrintButton;

    @FXML
    void conditionContextHidden() { conditionLabel.setText(conditionTextField.getText());}

    @FXML
    void dateContextHidden() { dateLabel.setText(dateTextField.getText());}

    @FXML
    void defectContextHidden() {defectLabel.setText(defectTextField.getText()); }

    @FXML
    void deviceContextHidden() {deviceLabel.setText(deviceTextField.getText()); }

    @FXML
    void fullNameContextHidden() { fullNameLabel.setText(fullNameTextField.getText());}

    @FXML
    void modelContextHidden() { modelLabel.setText(modelTextField.getText());}

    @FXML
    void noteContextHidden() { noteLabel.setText(noteTextField.getText());}

    @FXML
    void orderContextHidden() { orderLabel.setText(orderTextField.getText());}

    @FXML
    void phoneContextHidden() { phoneLabel.setText(phoneTextField.getText());}

    @FXML
    void rulesContextHidden() {rulesLabel.setText(rulesTextArea.getText()); }

    @FXML
    void signAcceptContextHidden() { signAcceptLabel.setText(signAcceptTextField.getText());}

    @FXML
    void signPassedContextHidden() { signPassedLabel.setText(signPassedTextField.getText()); }

    @FXML
    void titleContextHidden() { titleLabel.setText(titleTextArea.getText()); }

    DataTickets dataTickets = new DataTickets();
    @FXML
    public void initialize() {

        dataTickets.editPatternPrintRead();
        titleLabel.setText(dataTickets.getPrintPatternData().get(0));
        rulesLabel.setText(dataTickets.getPrintPatternData().get(1));
        signAcceptLabel.setText(dataTickets.getPrintPatternData().get(2));
        signPassedLabel.setText(dataTickets.getPrintPatternData().get(3));
        orderLabel.setText(dataTickets.getPrintPatternData().get(4));
        fullNameLabel.setText(dataTickets.getPrintPatternData().get(5));
        noteLabel.setText(dataTickets.getPrintPatternData().get(6));
        dateLabel.setText(dataTickets.getPrintPatternData().get(7));
        deviceLabel.setText(dataTickets.getPrintPatternData().get(8));
        modelLabel.setText(dataTickets.getPrintPatternData().get(9));
        conditionLabel.setText(dataTickets.getPrintPatternData().get(10));
        defectLabel.setText(dataTickets.getPrintPatternData().get(11));

        titleTextArea.setText(titleLabel.getText());
        orderTextField.setText(orderLabel.getText());
        fullNameTextField.setText(fullNameLabel.getText());
        phoneTextField.setText(phoneLabel.getText());
        noteTextField.setText(noteLabel.getText());
        defectTextField.setText(defectLabel.getText());
        dateTextField.setText(dateLabel.getText());
        deviceTextField.setText(deviceLabel.getText());
        modelTextField.setText(modelLabel.getText());
        conditionTextField.setText(conditionLabel.getText());
        rulesTextArea.setText(rulesLabel.getText());
        signAcceptTextField.setText(signAcceptLabel.getText());
        signPassedTextField.setText(signPassedLabel.getText());
    }

    @FXML
    public void savePatternPrint(){
        dataTickets.editPatternPrintWrite(titleLabel.getText(), rulesLabel.getText(), signAcceptLabel.getText(), signPassedLabel.getText(),
                orderLabel.getText(), fullNameLabel.getText(), phoneLabel.getText(), noteLabel.getText(), dateLabel.getText(), deviceLabel.getText(),
                modelLabel.getText(), conditionLabel.getText(), defectLabel.getText());
        Stage stage = (Stage) savePatternPrintButton.getScene().getWindow();
        stage.close();
    }
}
