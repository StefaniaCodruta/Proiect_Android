package com.example.tarisiorase.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tarisiorase.models.Country;

import java.util.List;

@Dao
public interface CountryDao {
    @Delete
    public void delete(Country country);

    @Query("delete from countries")
    public void deleteAll();

    @Query("select * from countries")
    public List<Country> getAll();

    @Query("select * from countries where continent like :continent")
    public List<Country> getCountriesByContinent(String continent);

    @Query("select * from countries where population >:nrPopulation")
    public List<Country> getCountriesByPopulation(long nrPopulation);

    @Insert
    public void insert(Country country);

    @Update
    public void update(Country country);

}
