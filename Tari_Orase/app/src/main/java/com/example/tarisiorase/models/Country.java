package com.example.tarisiorase.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "countries")
public class Country implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idCountry")
    int idCountry;
    @ColumnInfo(name="name")
    String name;
    @ColumnInfo(name="continent")
    String continent;
    @ColumnInfo(name="language")
    String language;
    @ColumnInfo(name="capital")
    String capital;
    @ColumnInfo(name="religion")
    String religion;
    @ColumnInfo(name="population")
    long population;


    public Country(String name, String continent,String language,String capital, String religion, long population) {
        this.name = name;
        this.continent=continent;
        this.language=language;
        this.capital = capital;
        this.religion = religion;
        this.population = population;
    }


    @Ignore
    protected Country(Parcel in) {
        idCountry = in.readInt();
        name = in.readString();
        continent=in.readString();
        language=in.readString();
        capital = in.readString();
        religion = in.readString();
        population = in.readLong();
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Country: " +
                " " + idCountry +
                "\nname: '" + name + '\'' +
                "\ncontinent:'" + continent + '\'' +
                "\nlanguage:'" + language + '\'' +
                "\ncapital:'" + capital + '\'' +
                "\nreligion: '" + religion + '\'' +
                "\npopulation: " + population +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCountry);
        dest.writeString(name);
        dest.writeString(continent);
        dest.writeString(language);
        dest.writeString(capital);
        dest.writeString(religion);
        dest.writeLong(population);
    }
}
