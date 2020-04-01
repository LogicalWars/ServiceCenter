package sample.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StockList {
    private SimpleStringProperty model = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty elementId = new SimpleIntegerProperty();
    private String modelString;

    public String getModelString() {
        return modelString;
    }

    public void setModelString(String modelString) {
        this.modelString = modelString;
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getElementId() {
        return elementId.get();
    }

    public SimpleIntegerProperty elementIdProperty() {
        return elementId;
    }

    public void setElementId(int elementId) {
        this.elementId.set(elementId);
    }

    public int getAmount() {
        return amount.get();
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    private SimpleIntegerProperty amount = new SimpleIntegerProperty();
    private SimpleIntegerProperty price = new SimpleIntegerProperty();

    public StockList(int elementId, String model, String name, int amount, int price){
        this.elementId = new SimpleIntegerProperty(elementId);
        this.amount = new SimpleIntegerProperty(amount);
        this.price = new SimpleIntegerProperty(price);
        this.model = new SimpleStringProperty(model);
        this.name = new SimpleStringProperty(name);
    }

    public StockList (String model){
        this.modelString = model;
    }




    @Override
    public String toString() {
        return modelString;
    }

}
