package sample.Model;

import javafx.beans.property.SimpleStringProperty;

public class Rules {
    String rule;

    public Rules(String rule){
        this.rule = rule;
    }

    @Override
    public String toString() {
        return rule;
    }
}
