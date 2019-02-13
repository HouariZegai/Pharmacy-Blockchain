package com.tiaretdevgroup.openhackathon.java.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DiseaseTable extends RecursiveTreeObject<DiseaseTable> {
    private StringProperty identifier;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty produit;
    private StringProperty category;
    private StringProperty date;
    
    public DiseaseTable(int identifier, String firstName, String lastName, String produit, String category, String date) {
        this.identifier = new SimpleStringProperty(String.valueOf(identifier));
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.produit = new SimpleStringProperty(produit);
        this.category = new SimpleStringProperty(category);
        this.date = new SimpleStringProperty(date);
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
    
    public StringProperty getProduit() {
        return produit;
    }
    
    public StringProperty getCategory() {
        return category;
    }
    
    public StringProperty getDate() {
        return date;
    }
    
}
