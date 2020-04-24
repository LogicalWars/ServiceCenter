package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Tickets implements Comparable<Tickets> {
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
    public Tickets(int id, String p, String name, Date date1, String status){}

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

    @Override
    public boolean equals(Object o) {
        // 1
        if (this == o) {
            return true;
        }

        // 2
        if (o == null || getClass() != o.getClass()) {
            return false;

        }

        // 3
        Tickets t = (Tickets) o;
        return idTicket.getValue() == t.idTicket.getValue() &&
               phoneNumber.getValue().equals(t.phoneNumber.getValue()) &&
               fullName.getValue().equals(t.fullName.getValue())&&
                dateCreateTicket.getValue().equals(dateCreateTicket.getValue())&&
                statusTicket.getValue().equals(statusTicket.getValue());
    }
    @Override
    public int hashCode() {
        return (545353534);
    }

    @Override
    public int compareTo(Tickets tickets) {
        return tickets.getIdTicket() - this.getIdTicket();
    }
}
