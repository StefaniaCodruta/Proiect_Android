package com.example.tarisiorase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tarisiorase.database.DatabaseManager;
import com.example.tarisiorase.models.Country;
import com.example.tarisiorase.models.Place;

import java.util.ArrayList;
import java.util.List;

public class TravelActivity extends AppCompatActivity {

    private Button btnAddCountry,btnAddCity,btnShowCountries,btnShowPlaces;
    public final static int ADD_COUNTRY_CODE=100;
    public final static int ADD_PLACE_CODE=101;
    public final static int REQUEST_LIST_COUNTRIES=102;
    public final static String LIST_COUNTRIES="listCountries";

    public static DatabaseManager databaseManager;

    public List<Country> countryList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        initVisualControlllers();

        databaseManager=DatabaseManager.getInstance(this);

        btnAddCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TravelActivity.this, AddCountryActivity.class);
                startActivityForResult(intent,ADD_COUNTRY_CODE);
            }
        });
        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TravelActivity.this, AddPlaceActivity.class);
                startActivityForResult(intent,ADD_PLACE_CODE);
            }
        });

        btnShowCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TravelActivity.this, ShowCountriesActivity.class);
                // intent.putParcelableArrayListExtra(LIST_COUNTRIES,(ArrayList<Country>)countryList);
                startActivity(intent);
            }
        });

        btnShowPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TravelActivity.this, ShowPlacesActivity.class);
                startActivity(intent);
            }
        });
    }

    void initVisualControlllers(){
        btnAddCity=findViewById(R.id.btnAddCity);
        btnAddCountry=findViewById(R.id.btnAddCountry);
        btnShowCountries=findViewById(R.id.btnShowCountries);
        btnShowPlaces=findViewById(R.id.btnShowPlaces);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COUNTRY_CODE && resultCode == RESULT_OK && data != null
                && data.hasExtra(AddCountryActivity.ADD_COUNTRY_KEY)) {
            Country country = data.getParcelableExtra(AddCountryActivity.ADD_COUNTRY_KEY);
            new InsertCountry().execute(country);
            Toast.makeText(this, country.toString(), Toast.LENGTH_SHORT).show();
        }

        if(requestCode==ADD_PLACE_CODE && resultCode==RESULT_OK && data!=null
                && data.hasExtra(AddPlaceActivity.ADD_PLACE_KEY)){
            Place place=data.getParcelableExtra(AddPlaceActivity.ADD_PLACE_KEY);
            new InsertPlace().execute(place);
            Toast.makeText(this, place.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    @SuppressLint("StaticFieldLeak")
    public class InsertCountry extends AsyncTask<Country, Void, Void> {
        @Override
        protected Void doInBackground(Country... countries) {
            databaseManager.getCountriesDao().insert(countries[0]);
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class InsertPlace extends AsyncTask<Place, Void, Void> {
        @Override
        protected Void doInBackground(Place... places) {
            databaseManager.getPlacesDao().insertPlace(places[0]);
            return null;
        }
    }
}
