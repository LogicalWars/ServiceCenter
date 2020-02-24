package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Tickets {
    private SimpleIntegerProperty idTicket;             //№ заявки
    private SimpleStringProperty phoneNumber;           //Номер телефона
    private SimpleStringProperty fullName;              //ФИО
    private SimpleStringProperty dateCreateTicket;      //Дата создания
    private SimpleStringProperty statusTicket;          //Статус заявки

    public Tickets(int idTicket, String phoneNumber, String fullName, String dateCreateTicket, String statusTicket){
        this.idTicket = new SimpleIntegerProperty(idTicket);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.fullName = new SimpleStringProperty(fullName);
        this.dateCreateTicket = new SimpleStringProperty(dateCreateTicket);
        this.statusTicket = new SimpleStringProperty(statusTicket);
    }
    public Tickets(){}

    public int getIdTicket(){return idTicket.get();}
    public void setIdTicket(int value){idTicket.set(value);}

    public String getPhoneNumber(){return phoneNumber.get();}
    public void setPhoneNumber(String value){phoneNumber.set(value);}

    public String getFullName(){return fullName.get();}
    public void setFullName(String value){fullName.set(value);}

    public String getDateCreateTicket(){return dateCreateTicket.get();}
    public void setDateCreateTicket(String value){dateCreateTicket.set(value);}

    public String getStatusTicket(){return statusTicket.get();}
    public void setStatus(String value){statusTicket.set(value);}

}
