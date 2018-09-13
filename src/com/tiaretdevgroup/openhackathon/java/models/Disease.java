package com.tiaretdevgroup.openhackathon.java.models;

public class Disease {
    private int identifier;
    private String firstName;
    private String lastName;
    private String produit;
    private String category;
    private String date;
    
    public Disease(int identifier, String firstName, String lastName, String produit, String category, String date) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.produit = produit;
        this.category = category;
        this.date = date;
    }
    
    public int getIdentifier() {
        return identifier;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getProduit() {
        return produit;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getDate() {
        return date;
    }
}
