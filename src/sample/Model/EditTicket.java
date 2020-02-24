package sample.Model;

import javafx.beans.property.SimpleStringProperty;

public class EditTicket {
    private int idTicket;
    private int phoneNumber;
    private String fullName;
    private String  dateCreateTicket;
    private String statusTicket;
    private String deviceTicket;
    private String defectTicket;
    private String modelTicket;
    private String noteTicket;
    private String conditionTicket;

    public EditTicket(int idTicket, int phoneNumber, String fullName){
        this.idTicket = idTicket;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
    }

    public int getIdTicket() {return idTicket;}
    public void setIdTicket(int idTicket) {this.idTicket = idTicket;}

    public int getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(int phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}

    public String getDateCreateTicket() {return dateCreateTicket;}
    public void setDateCreateTicket(String dateCreateTicket) {this.dateCreateTicket = dateCreateTicket;}

    public String getStatusTicket() {return statusTicket;}
    public void setStatusTicket(String statusTicket) {this.statusTicket = statusTicket;}

    public String getDeviceTicket() {return deviceTicket;}
    public void setDeviceTicket(String deviceTicket) {this.deviceTicket = deviceTicket;}

    public String getDefectTicket() {return defectTicket;}
    public void setDefectTicket(String defectTicket) {this.defectTicket = defectTicket;}

    public String getModelTicket() {return modelTicket;}
    public void setModelTicket(String modelTicket) {this.modelTicket = modelTicket;}

    public String getNoteTicket() {return noteTicket;}
    public void setNoteTicket(String noteTicket) {this.noteTicket = noteTicket;}

    public String getConditionTicket() {return conditionTicket;}
    public void setConditionTicket(String conditionTicket) {this.conditionTicket = conditionTicket;}
}
