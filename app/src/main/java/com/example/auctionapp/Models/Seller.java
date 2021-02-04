package com.example.auctionapp.Models;

public class Seller {
    String address,country,email,password,phoneNo,sId,sName;

    public Seller() {
    }

    public Seller(String address, String country, String email, String password, String phoneNo, String sId, String sName) {
        this.address = address;
        this.country = country;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.sId = sId;
        this.sName = sName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
}
