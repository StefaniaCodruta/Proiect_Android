package com.example.tarisiorase.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tarisiorase.models.Place;

import java.util.List;

@Dao
public interface PlaceDao {
    @Query("select * from places")
    public List<Place> getAllPlaces();

    @Query("delete from places")
    public void deleteAll();

    @Query("select cityName from places")
    public List<String> getCategoriesNames();

    @Insert
    public void insertPlace(Place place);

    @Update
    public void updatePlace(Place place);

    @Delete
    public void deletePlace(Place place);
}
