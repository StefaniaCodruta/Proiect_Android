package com.example.tarisiorase.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "places",foreignKeys = @ForeignKey(entity=Country.class,
        parentColumns = "idCountry",
        childColumns = "idCountry",
        onDelete = ForeignKey.CASCADE))
public class Place implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idPlace")
    int idPlace;
    @ColumnInfo(name = "cityName")
    String cityName;
    @ColumnInfo(name = "category")
    String category;
    @ColumnInfo(name = "visitDate")
    String visitDate;
    @ColumnInfo(name = "rating")
    float rating;
    @ColumnInfo(name = "memories")
    String memories;
    @ColumnInfo(name = "idCountry")
    int idCountry;


    public Place(String cityName,String category, String visitDate, float rating, String memories,int idCountry) {
        this.cityName=cityName;
        this.category = category;
        this.visitDate = visitDate;
        this.rating = rating;
        this.memories = memories;
        this.idCountry=idCountry;
    }

    @Ignore
    protected Place(Parcel in) {
        idPlace = in.readInt();
        cityName = in.readString();
        category = in.readString();
        visitDate = in.readString();
        rating = in.readFloat();
        memories = in.readString();
        idCountry = in.readInt();
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getMemories() {
        return memories;
    }

    public void setMemories(String memories) {
        this.memories = memories;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPlace);
        dest.writeString(cityName);
        dest.writeString(category);
        dest.writeString(visitDate);
        dest.writeFloat(rating);
        dest.writeString(memories);
        dest.writeInt(idCountry);
    }

    @Override
    public String toString() {
        return "Place: " +
                " " + idPlace +
                "\ncityName:'" + cityName + '\'' +
                "\ncategory:'" + category + '\'' +
                "\nvisitDate:'" + visitDate + '\'' +
                "\nrating:" + rating +
                "\nmemories:'" + memories + '\'' +
                "\nidCountry:" + idCountry +
                '}';
    }

}
