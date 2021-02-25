package com.example.tarisiorase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tarisiorase.models.Country;

import java.util.ArrayList;

public class ShowCountriesByContinent extends AppCompatActivity {

    private ListView lvResulList;
    private ArrayList<Country> resultList=new ArrayList<>();
    private ArrayAdapter<Country> countriesAdapter;
    public Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_countries_by_continent);

        lvResulList=findViewById(R.id.lvResultList);

        if(getIntent().getParcelableArrayListExtra(ShowCountriesActivity.RESULT_COUNTRIES_LIST)!=null){
            resultList=getIntent().getParcelableArrayListExtra(ShowCountriesActivity.RESULT_COUNTRIES_LIST);
            Toast.makeText(getApplicationContext(),resultList.toString(),Toast.LENGTH_SHORT).show();
        }

        countriesAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,resultList);
        lvResulList.setAdapter(countriesAdapter);

    }
}
