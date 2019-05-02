package com.boucinho.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {

    private String mTitle;
    private String mDetails;
    private Date mDate;
    private String mLocation = "";

    public Event() {
    }

    public Event(String title, String details, Date date, String location) {
        mTitle = title;
        mDetails = details;
        mDate = date;
        mLocation = location;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getFriendlyDate(){
        if(null != this.mDate){
            return new SimpleDateFormat("yyyy/MM/dd, E HH'h'mm", Locale.FRANCE).format(this.mDate);
        } else {
            return "Unknown";
        }
    }
}