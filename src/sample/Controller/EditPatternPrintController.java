package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Model.DataTickets;

public class EditPatternPrintController {
    private MainMenuController mainMenuController;

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    @FXML
    private Button savePatternPrintButton;
    @FXML
    private HBox titleHBox;
    @FXML
    private Label titleLabel;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label orderLabel;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label noteLabel;
    @FXML
    private Label defectLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label deviceLabel;
    @FXML
    private Label modelLabel;
    @FXML
    private Label conditionLabel;
    @FXML
    private Label rulesLabel;
    @FXML
    private Label signAcceptLabel;
    @FXML
    private Label signPassedLabel;

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
        phoneLabel.setText(dataTickets.getPrintPatternData().get(6));
        noteLabel.setText(dataTickets.getPrintPatternData().get(7));
        dateLabel.setText(dataTickets.getPrintPatternData().get(8));
        deviceLabel.setText(dataTickets.getPrintPatternData().get(9));
        modelLabel.setText(dataTickets.getPrintPatternData().get(10));
        conditionLabel.setText(dataTickets.getPrintPatternData().get(11));
        defectLabel.setText(dataTickets.getPrintPatternData().get(12));

        Font font = new Font("Regular", 11);

        TextArea titleTextArea = new TextArea(titleLabel.getText());
        titleTextArea.setFont(font);
        HBox.setHgrow(titleTextArea, Priority.ALWAYS);
        titleTextArea.setWrapText(true);
        titleTextArea.setPrefHeight(25.0);
        titleTextArea.setMinHeight(25.0);

        TextField orderTextField = new TextField(orderLabel.getText());
        orderTextField.setFont(font);

        TextField fullNameTextField = new TextField(fullNameLabel.getText());
        fullNameTextField.setFont(font);

        TextField phoneTextField = new TextField(phoneLabel.getText());
        phoneTextField.setFont(font);

        TextField noteTextField = new TextField(noteLabel.getText());
        noteTextField.setFont(font);

        TextField defectTextField = new TextField(defectLabel.getText());
        defectTextField.setFont(font);

        TextField dateTextField = new TextField(dateLabel.getText());
        dateTextField.setFont(font);

        TextField deviceTextField = new TextField(deviceLabel.getText());
        deviceTextField.setFont(font);

        TextField modelTextField = new TextField(modelLabel.getText());
        modelTextField.setFont(font);

        TextField conditionTextField = new TextField(conditionLabel.getText());
        conditionTextField.setFont(font);

        TextField signAcceptTextField = new TextField(signAcceptLabel.getText());
        signAcceptTextField.setFont(font);

        TextField signPassedTextField = new TextField(signPassedLabel.getText());
        signPassedTextField.setFont(font);

        TextArea rulesTextArea = new TextArea(rulesLabel.getText());
        rulesTextArea.setFont(font);
        rulesTextArea.setWrapText(true);

        titleLabel.setOnMouseClicked(event ->  {
            titleHBox.getChildren().remove(titleLabel);
            titleHBox.getChildren().add(titleTextArea);
            titleTextArea.requestFocus();
        });
        titleTextArea.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                titleHBox.getChildren().remove(titleTextArea);
                titleHBox.getChildren().add(titleLabel);
                titleLabel.setVisible(true);
                titleLabel.setText(titleTextArea.getText());
            }
        });

        orderLabel.setOnMouseClicked(event ->  {
            orderLabel.setVisible(false);
            gridPane.add(orderTextField, 0, 0);
            orderTextField.requestFocus();
        });
        orderTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(orderTextField);
                orderLabel.setVisible(true);
                orderLabel.setText(orderTextField.getText());
            }
        });

        fullNameLabel.setOnMouseClicked(event ->  {
            fullNameLabel.setVisible(false);
            gridPane.add(fullNameTextField, 0, 1);
            fullNameTextField.requestFocus();
        });
        fullNameTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(fullNameTextField);
                fullNameLabel.setVisible(true);
                fullNameLabel.setText(fullNameTextField.getText());
            }
        });

        phoneLabel.setOnMouseClicked(event ->  {
            phoneLabel.setVisible(false);
            gridPane.add(phoneTextField, 0, 2);
            phoneTextField.requestFocus();
        });
        phoneTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(phoneTextField);
                phoneLabel.setVisible(true);
                phoneLabel.setText(phoneTextField.getText());
            }
        });

        noteLabel.setOnMouseClicked(event ->  {
            noteLabel.setVisible(false);
            gridPane.add(noteTextField, 0, 3);
            noteTextField.requestFocus();
        });
        noteTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(noteTextField);
                noteLabel.setVisible(true);
                noteLabel.setText(noteTextField.getText());
            }
        });

        defectLabel.setOnMouseClicked(event ->  {
            defectLabel.setVisible(false);
            gridPane.add(defectTextField, 0, 4);
            defectTextField.requestFocus();
        });
        defectTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(defectTextField);
                defectLabel.setVisible(true);
                defectLabel.setText(defectTextField.getText());
            }
        });

        dateLabel.setOnMouseClicked(event ->  {
            dateLabel.setVisible(false);
            gridPane.add(dateTextField, 2, 0);
            dateTextField.requestFocus();
        });
        dateTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(dateTextField);
                dateLabel.setVisible(true);
                dateLabel.setText(dateTextField.getText());
            }
        });

        deviceLabel.setOnMouseClicked(event ->  {
            deviceLabel.setVisible(false);
            gridPane.add(deviceTextField, 2, 1);
            deviceTextField.requestFocus();
        });
        deviceTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(deviceTextField);
                deviceLabel.setVisible(true);
                deviceLabel.setText(deviceTextField.getText());
            }
        });

        modelLabel.setOnMouseClicked(event ->  {
            modelLabel.setVisible(false);
            gridPane.add(modelTextField, 2, 2);
            modelTextField.requestFocus();
        });
        modelTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(modelTextField);
                modelLabel.setVisible(true);
                modelLabel.setText(modelTextField.getText());
            }
        });

        conditionLabel.setOnMouseClicked(event ->  {
            conditionLabel.setVisible(false);
            gridPane.add(conditionTextField, 2, 3);
            conditionTextField.requestFocus();
        });
        conditionTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(conditionTextField);
                conditionLabel.setVisible(true);
                conditionLabel.setText(conditionTextField.getText());
            }
        });

        signAcceptLabel.setOnMouseClicked(event ->  {
            signAcceptLabel.setVisible(false);
            gridPane.add(signAcceptTextField, 1, 6);
            signAcceptTextField.requestFocus();
        });
        signAcceptTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(signAcceptTextField);
                signAcceptLabel.setVisible(true);
                signAcceptLabel.setText(signAcceptTextField.getText());
            }
        });

        signPassedLabel.setOnMouseClicked(event ->  {
            signPassedLabel.setVisible(false);
            gridPane.add(signPassedTextField, 3, 6);
            signPassedTextField.requestFocus();
        });
        signPassedTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(signPassedTextField);
                signPassedLabel.setVisible(true);
                signPassedLabel.setText(signPassedTextField.getText());
            }
        });

        rulesLabel.setOnMouseClicked(event ->  {
            rulesLabel.setVisible(false);
            gridPane.add(rulesTextArea, 0, 5, 4, 1);
            rulesTextArea.requestFocus();
        });
        rulesTextArea.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue!=true)
            {
                gridPane.getChildren().remove(rulesTextArea);
                rulesLabel.setVisible(true);
                rulesLabel.setText(rulesTextArea.getText());
            }
        });

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
