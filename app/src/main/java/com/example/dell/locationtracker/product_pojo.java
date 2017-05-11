package com.example.dell.locationtracker;


public class product_pojo {

    String date;
    String latlon;
    String time;
    String address;


    public product_pojo(){}

    public product_pojo(String date, String latlon, String time, String address){
        this.date = date;
        this.latlon = latlon;
        this.time=time;
        this.address=address;

    }

    public product_pojo(String latlon, String time, String address){
        this.latlon = latlon;
        this.time=time;
        this.address=address;


    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatlon() {
        return latlon;
    }

    public void setLatlon(String latlon) {
        this.latlon = latlon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
