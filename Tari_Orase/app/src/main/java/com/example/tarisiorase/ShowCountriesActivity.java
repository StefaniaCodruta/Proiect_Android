package com.example.tarisiorase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tarisiorase.adapters.CountryAdapter;
import com.example.tarisiorase.database.DatabaseManager;
import com.example.tarisiorase.models.Country;

import java.util.ArrayList;
import java.util.List;

public class ShowCountriesActivity extends AppCompatActivity {
    private EditText etSearch,etSearchByPopulation;
    private Button btnSearch,btnSearchByPopulation;
    private ArrayAdapter<Country> countriesArrayAdapter;
    private CountryAdapter countryAdapter;
    List<Country> countriesList= new ArrayList<>();
    ArrayList<Country> resultCountriesList=new ArrayList<>();
    ArrayList<Country> rsListByPopulation=new ArrayList<>();
    public static Country countrySent;
    public static String RESULT_COUNTRIES_LIST="resultList";
    public static String RESULT_SEARCH_BY_POPULATION="searchByPopulation";

    // DatabaseDao databaseDao;
    public static String UPDATE_COUNTRY="updateCountry";
    public static int REQUEST_UPDATE_COUNTRY=200;
    public static int REQUEST_RESULT_LIST=300;
    public static int REQUEST_RS_pPOPULATION=301;
    int updateResult, deleteResult;
    static DatabaseManager databaseManager;
    ListView lvCountries;
    Intent intent;
    int selectedIndex=0;
    int selectedIndexUpdate=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_countries);

        databaseManager=DatabaseManager.getInstance(getApplicationContext());
        intent=getIntent();
        etSearch=findViewById(R.id.etSearchByContinent);
        etSearchByPopulation=findViewById(R.id.etSearchByPopulation);
        btnSearchByPopulation=findViewById(R.id.btnSearchByPopulation);
        btnSearch=findViewById(R.id.btnSearchByContinet);
        lvCountries=findViewById(R.id.lvVisitedCountries);
        countriesList=databaseManager.getCountriesDao().getAll();

//        countriesArrayAdapter= new ArrayAdapter<>(getApplicationContext(),
//                android.R.layout.simple_list_item_1, countriesList);
//
//        lvCountries.setAdapter(countriesArrayAdapter);

        countryAdapter=new CountryAdapter(getApplicationContext(),countriesList);
        lvCountries.setAdapter(countryAdapter);
       // countriesArrayAdapter.notifyDataSetChanged();
        countryAdapter.notifyDataSetChanged();

        lvCountries.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex=position;
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(ShowCountriesActivity.this);
                alertDialog.setMessage(R.string.deleteWarningQuestion).setTitle(R.string.deleteRecord)
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                countriesList.remove(selectedIndex);
                                countriesArrayAdapter.notifyDataSetChanged();

                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog=alertDialog.create();
                dialog.show();
                new DeleteCountry().execute(countriesList.get(selectedIndex));
                return true;
            }
        });


        lvCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ShowCountriesActivity.this,AddCountryActivity.class);
                Country country=countriesList.get(position);
                countrySent=country;
                selectedIndexUpdate=position;
                intent.putExtra(UPDATE_COUNTRY,country);
                startActivityForResult(intent,REQUEST_UPDATE_COUNTRY);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String continent=etSearch.getText().toString().trim();
                resultCountriesList=(ArrayList<Country>)databaseManager.getCountriesDao().getCountriesByContinent(continent);
                if(resultCountriesList!=null){
                    Intent intent=new Intent(ShowCountriesActivity.this,ShowCountriesByContinent.class);
                    intent.putParcelableArrayListExtra(RESULT_COUNTRIES_LIST,
                            resultCountriesList);
                    startActivityForResult(intent,REQUEST_RESULT_LIST);
                }
            }
        });

        btnSearchByPopulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long population=Long.parseLong(etSearchByPopulation.getText().toString().trim());
                rsListByPopulation=(ArrayList<Country>)databaseManager.getCountriesDao().getCountriesByPopulation(population);
                if(rsListByPopulation!=null){
                    Intent intent=new Intent(ShowCountriesActivity.this,ChartActivity.class);
                    intent.putParcelableArrayListExtra(RESULT_SEARCH_BY_POPULATION,
                            rsListByPopulation);
                    startActivityForResult(intent,REQUEST_RS_pPOPULATION);
                }

            }
        });
    }
    public class DeleteCountry extends AsyncTask<Country, Void, Void> {

        @Override
        protected Void doInBackground(Country... countries) {
            databaseManager.getCountriesDao().delete(countries[0]);
            return null;
        }

    }

    public class UpdateCountry extends AsyncTask<Country,Void,Void> {

        @Override
        protected Void doInBackground(Country... countries) {
            databaseManager.getCountriesDao().update(countries[0]);
            return  null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if(requestCode==REQUEST_UPDATE_COUNTRY && resultCode==RESULT_OK && data.hasExtra(AddCountryActivity.UPDATE_COUNTRY_KEY)){
            Country country=data.getParcelableExtra(AddCountryActivity.UPDATE_COUNTRY_KEY);
            //
            countriesList.set(selectedIndexUpdate,country);
            new UpdateCountry().execute(country);
            assert country != null;
            Toast.makeText(getApplicationContext(),country.toString(),Toast.LENGTH_SHORT).show();
            countriesArrayAdapter.notifyDataSetChanged();
        }

    }
}
