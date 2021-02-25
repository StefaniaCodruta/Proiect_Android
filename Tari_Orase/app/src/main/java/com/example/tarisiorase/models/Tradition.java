package com.example.tarisiorase.models;

import java.io.Serializable;

public class Tradition implements Serializable {
    private String idTradition;
    private String location;
    private String moment;
    private String shortDescription;

    public Tradition() {
    }

    public Tradition(String idTradition, String location, String moment, String shortDescription) {
        this.idTradition = idTradition;
        this.location = location;
        this.moment = moment;
        this.shortDescription = shortDescription;
    }

    public Tradition(String location, String moment, String shortDescription) {
        this.location = location;
        this.moment = moment;
        this.shortDescription = shortDescription;
    }

    public String getIdTradition() {
        return idTradition;
    }

    public void setIdTradition(String idTradition) {
        this.idTradition = idTradition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
