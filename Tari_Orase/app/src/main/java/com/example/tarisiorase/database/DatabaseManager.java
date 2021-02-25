package com.example.tarisiorase.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tarisiorase.database.dao.CountryDao;
import com.example.tarisiorase.database.dao.PlaceDao;
import com.example.tarisiorase.models.Country;
import com.example.tarisiorase.models.Place;

@Database(entities = {Country.class, Place.class}, version = 1)

public abstract class DatabaseManager extends RoomDatabase {
    static  DatabaseManager instance;
    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseManager.class, "database_travelling")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }

        return instance;
    }

    public abstract CountryDao getCountriesDao();
    public  abstract PlaceDao getPlacesDao();
}
