package com.condorhero89.nightguardian.model;

public class MyContact {
    private String phoneNumber;
    private String name;
    private boolean important;
    
    public MyContact(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    @Override
    public String toString() {
        return "MyContact [phoneNumber=" + phoneNumber + ", name=" + name + "]";
    }
}
