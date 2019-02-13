package com.tiaretdevgroup.openhackathon.java.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DiseaseTable extends RecursiveTreeObject<DiseaseTable> {
    private StringProperty identifier;
    private StringProperty firstName;
    private StringProperty lastName;
    
    public DiseaseTable(String identifier, String firstName, String lastName) {
        this.identifier = new SimpleStringProperty(String.valueOf(identifier));
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }
    
    public StringProperty getIdentifier() {
        return identifier;
    }
    
    public StringProperty getFirstName() {
        return firstName;
    }
    
    public StringProperty getLastName() {
        return lastName;
    }
    
}
