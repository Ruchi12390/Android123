package com.example.first;

public class Buyer {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String upi;
    private String bankAccount;
    private String ifsc;
    private String gst;

    // Constructor
    public Buyer(String name, String email, String phone, String address,
                 String password, String upi, String bankAccount,
                 String ifsc, String gst) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.upi = upi;
        this.bankAccount = bankAccount;
        this.ifsc = ifsc;
        this.gst = gst;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getUpi() {
        return upi;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getIfsc() {
        return ifsc;
    }

    public String getGst() {
        return gst;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}

