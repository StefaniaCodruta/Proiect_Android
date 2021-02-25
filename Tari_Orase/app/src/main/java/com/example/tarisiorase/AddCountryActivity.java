package com.example.tarisiorase;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tarisiorase.database.DatabaseManager;
import com.example.tarisiorase.models.Country;

import java.util.Objects;

public class AddCountryActivity extends AppCompatActivity {
    private EditText etCountryName,etCpaital,etLanguage,etReligion,etPopulation;
    private Spinner spnContinent;
    private ArrayAdapter<CharSequence> adapterSpnContinent;
    private Button btn_AddCountry,btn_updateCountry;
    private Intent intent;
    public final static String ADD_COUNTRY_KEY="addCountry";
    public final static String UPDATE_COUNTRY_KEY="updateCountry";
    public final static int RESULT_DELETE_COUNTRY=300;
    DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_country);

        intent=getIntent();

        initVisualControllers();

        databaseManager=DatabaseManager.getInstance(getApplicationContext());

        adapterSpnContinent=ArrayAdapter.createFromResource(getApplicationContext(),R.array.continents,
                android.R.layout.simple_spinner_dropdown_item);
        spnContinent.setAdapter(adapterSpnContinent);


        btn_AddCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForms()){
                    Country country=createObjectFromViews();
                    intent.putExtra(ADD_COUNTRY_KEY,country);
                    setResult(RESULT_OK,intent);
                    finish();

                    //Toast.makeText(getApplicationContext(),country.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(intent.hasExtra(ShowCountriesActivity.UPDATE_COUNTRY)){
            final Country country=intent.getParcelableExtra(ShowCountriesActivity.UPDATE_COUNTRY);
            // assert country != null;
            btn_updateCountry.setVisibility(View.VISIBLE);
            btn_AddCountry.setVisibility(View.INVISIBLE);

            if (country != null) {
                getObjectValues(country);
            }
            //
            btn_updateCountry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  new UpdateCountry().execute(country);
                    assert country != null;
                    country.setName(etCountryName.getText().toString());
                    country.setCapital(etCpaital.getText().toString());
                    country.setContinent(spnContinent.getSelectedItem().toString());
                    country.setLanguage(etReligion.getText().toString());
                    country.setLanguage(etLanguage.getText().toString());
                    country.setPopulation(Long.parseLong(etPopulation.getText().toString()));
                    //  Toast.makeText(getApplicationContext(), country.toString(), Toast.LENGTH_SHORT).show();
                    intent.putExtra(UPDATE_COUNTRY_KEY,country);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            });

        }

    }

    private void initVisualControllers(){

        etCountryName=findViewById(R.id.etName);
        etCpaital=findViewById(R.id.etCapital);
        etLanguage=findViewById(R.id.etLanguage);
        etReligion=findViewById(R.id.etReligion);
        etPopulation=findViewById(R.id.etPopulation);
        spnContinent=findViewById(R.id.spnContinent);
        btn_AddCountry=findViewById(R.id.btn_addCountry);
        btn_updateCountry=findViewById(R.id.btn_updateCountry);

    }

    private boolean validateForms(){

        if(etCountryName.getText()==null){
            Toast.makeText(this, R.string.camp_obligatoriu, Toast.LENGTH_SHORT).show();
            return false;

        }
        if(etCpaital.getText()==null ){
            Toast.makeText(this, R.string.camp_obligatoriu, Toast.LENGTH_SHORT).show();
            return false;

        }
        if(etLanguage.getText()==null ){
            Toast.makeText(this, R.string.camp_obligatoriu, Toast.LENGTH_SHORT).show();
            return false;

        }
        if(etReligion.getText()==null ){
            Toast.makeText(this, R.string.camp_obligatoriu, Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
    }

    private Country createObjectFromViews(){

        String countryName=String.valueOf(etCountryName.getText());
        String capital=String.valueOf(etCpaital.getText());
        String language=String.valueOf(etLanguage.getText());
        String religion=String.valueOf(etReligion.getText());
        int population= Integer.parseInt(etPopulation.getText().toString());
        String continent=spnContinent.getSelectedItem().toString();

        return new Country(countryName,continent,language,capital,religion,population);
    }

    private void getObjectValues(Country country) {

        etCountryName.setText(country.getName());
        etCpaital.setText(country.getCapital());
        etLanguage.setText(country.getLanguage());
        etReligion.setText(country.getReligion());
        etPopulation.setText(String.valueOf(country.getPopulation()));

        ArrayAdapter adapter=(ArrayAdapter)spnContinent.getAdapter();
        for(int i=0;i<adapter.getCount();i++){
            if(adapter.getItem(i).equals(country.getContinent())){
                spnContinent.setSelection(i);
            }
        }

    }
}
