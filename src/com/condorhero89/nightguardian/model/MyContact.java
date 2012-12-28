package com.condorhero89.nightguardian.model;

public class MyContact {
    private String phoneNumber;
    private String name;
    
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

    @Override
    public String toString() {
        return "MyContact [phoneNumber=" + phoneNumber + ", name=" + name + "]";
    }
}
