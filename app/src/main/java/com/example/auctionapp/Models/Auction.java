package com.example.auctionapp.Models;

public class Auction {
    String uId,uName,price,pId,sId;

    public Auction() {
    }

    public Auction(String uId, String uName, String price, String pId, String sId) {
        this.uId = uId;
        this.uName = uName;
        this.price = price;
        this.pId = pId;
        this.sId = sId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }
}
