package com.tiaretdevgroup.openhackathon.java.models;

public class Disease {
    private String identifier;
    private String firstName;
    private String lastName;
    private String nMalady1;
    private String nMalady2;
    private String nMalady3;
    private String nMalady4;
    private String nMalady5;
    
    public Disease() {
        
    }
    
    public Disease(String identifier, String firstName, String lastName, String nMalady1, 
            String nMalady2, String nMalady3, String nMalady4, String nMalady5) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nMalady1 = nMalady1;
        this.nMalady2 = nMalady2;
        this.nMalady3 = nMalady3;
        this.nMalady4 = nMalady4;
        this.nMalady5 = nMalady5;
    }
    
    public String getIdentifier() {
        return identifier;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setMaladies(String nMalady1, 
            String nMalady2, String nMalady3, String nMalady4, String nMalady5) {
        this.nMalady1 = nMalady1;
        this.nMalady2 = nMalady2;
        this.nMalady3 = nMalady3;
        this.nMalady4 = nMalady4;
        this.nMalady5 = nMalady5;
    }
    
    public String getMalady1() {
        return this.nMalady1;
    }
    
    public String getMalady2() {
        return this.nMalady2;
    }
    
    public String getMalady3() {
        return this.nMalady3;
    }
    
    public String getMalady4() {
        return this.nMalady4;
    }
    
    public String getMalady5() {
        return this.nMalady5;
    }
    
}
