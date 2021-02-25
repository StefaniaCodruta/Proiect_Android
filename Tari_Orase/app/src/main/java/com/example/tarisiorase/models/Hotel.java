package com.example.tarisiorase.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Hotel implements Parcelable {
    private String name;
    private String  location;
    private String imgURL;
    private String stars;
    private String facilities;
    private double price;

    public Hotel(String name,String location, String imgURL, String stars, String facilities, double price) {
        this.name = name;
        this.location = location;
        this.imgURL = imgURL;
        this.stars = stars;
        this.facilities = facilities;
        this.price = price;
    }

    public Hotel(String name,String imgURL,String stars) {
        this.name = name;
        this.imgURL = imgURL;
        this.stars=stars;

    }

    public Hotel() {

    }

    protected Hotel(Parcel in) {
        name = in.readString();
        location = in.readString();
        imgURL = in.readString();
        stars = in.readString();
        facilities = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    public String getName() {
        return name;
    }


    public String getLocation() {
        return location;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getStars() {
        return stars;
    }

    public String getFacilities() {
        return facilities;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setLocation(String location) {
        this.location = location;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", stars='" + stars + '\'' +
                ", facilities='" + facilities + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(imgURL);
        dest.writeString(stars);
        dest.writeString(facilities);
        dest.writeDouble(price);
    }
}
