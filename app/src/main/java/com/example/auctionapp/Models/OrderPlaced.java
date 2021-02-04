package com.example.auctionapp.Models;

public class OrderPlaced {
        String pId,sId,uId,price,uName,startTime,endTime,pName,pDesc,pImageUrl;

        public OrderPlaced() {
        }

        public OrderPlaced(String pId, String sId, String uId, String price, String uName, String startTime, String endTime, String pName, String pDesc, String pImageUrl) {
                this.pId = pId;
                this.sId = sId;
                this.uId = uId;
                this.price = price;
                this.uName = uName;
                this.startTime = startTime;
                this.endTime = endTime;
                this.pName = pName;
                this.pDesc = pDesc;
                this.pImageUrl = pImageUrl;
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

        public String getuId() {
                return uId;
        }

        public void setuId(String uId) {
                this.uId = uId;
        }

        public String getPrice() {
                return price;
        }

        public void setPrice(String price) {
                this.price = price;
        }

        public String getuName() {
                return uName;
        }

        public void setuName(String uName) {
                this.uName = uName;
        }

        public String getStartTime() {
                return startTime;
        }

        public void setStartTime(String startTime) {
                this.startTime = startTime;
        }

        public String getEndTime() {
                return endTime;
        }

        public void setEndTime(String endTime) {
                this.endTime = endTime;
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

        public String getpImageUrl() {
                return pImageUrl;
        }

        public void setpImageUrl(String pImageUrl) {
                this.pImageUrl = pImageUrl;
        }
}
