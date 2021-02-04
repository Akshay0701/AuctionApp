package com.example.auctionapp.Models;

import java.io.Serializable;

public class Product implements Serializable {
        String pId,sId,maxPrice,pImageUrl,pName,pDesc,minPrice,report;
        long startTime,endTime;

    public Product() {
    }

    public Product(String pId, String sId, String maxPrice, String pImageUrl, String pName, String pDesc, String minPrice, String report, long startTime, long endTime) {
        this.pId = pId;
        this.sId = sId;
        this.maxPrice = maxPrice;
        this.pImageUrl = pImageUrl;
        this.pName = pName;
        this.pDesc = pDesc;
        this.minPrice = minPrice;
        this.report = report;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
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

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getpImageUrl() {
        return pImageUrl;
    }

    public void setpImageUrl(String pImageUrl) {
        this.pImageUrl = pImageUrl;
    }


    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
