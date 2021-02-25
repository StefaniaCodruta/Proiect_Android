package com.example.tarisiorase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarisiorase.models.Place;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPlaceActivity extends AppCompatActivity {
    private Button btn_addCity,btn_updatePlace;
    private Spinner spnCategory;
    private EditText etCityName, etIdCountryFK,etExperience;
    private TextView tvData, tvRating;
    private RatingBar rbNrStars;
    private ArrayAdapter<CharSequence> adapterSpnCategory;
    int year, month,dayOfMonth;
    private Intent intent;

    public final static String ADD_PLACE_KEY="addPlace";
    public final static String UPDATE_PLACE_KEY="updatePlace";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        initVisualControllers();

        intent = getIntent();
        final Calendar calendar = Calendar.getInstance();
        adapterSpnCategory = ArrayAdapter.createFromResource(this, R.array.categories,
                R.layout.support_simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapterSpnCategory);
        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        setCalendar(calendar, year, month, dayOfMonth);
                        tvData.setText(new SimpleDateFormat(getString(R.string.datePattern)).format(calendar.getTime()));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPlaceActivity.this, listener, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        // double nrStars=rbNrStars.getRating();
        rbNrStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvRating.setText(String.valueOf(rbNrStars.getRating()));
            }
        });

        btn_addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateControllers()) {
                    Place place = createFromVisualContollers();
                    Toast.makeText(AddPlaceActivity.this, place.toString(), Toast.LENGTH_LONG).show();
                    intent.putExtra(ADD_PLACE_KEY, place);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
        });

        if(intent.hasExtra(ShowPlacesActivity.UPDATE_PLACE)){
            final Place place=intent.getParcelableExtra(ShowPlacesActivity.UPDATE_PLACE);
            // assert country != null;
            btn_updatePlace.setVisibility(View.VISIBLE);
            btn_addCity.setVisibility(View.INVISIBLE);

            if (place != null) {
                getObjectValues(place);
            }

            btn_updatePlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  new UpdateCountry().execute(country);
                    assert place != null;
                    place.setCityName(etCityName.getText().toString());
                    place.setCategory(spnCategory.getSelectedItem().toString());
                    place.setMemories(etExperience.getText().toString());
                    place.setVisitDate(tvData.getText().toString());
                    place.setRating(Float.parseFloat(tvRating.getText().toString().trim()));
                    place.setIdCountry(Integer.parseInt(etIdCountryFK.getText().toString()));
                    //  Toast.makeText(getApplicationContext(), country.toString(), Toast.LENGTH_SHORT).show();
                    intent.putExtra(UPDATE_PLACE_KEY,place);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            });

        }

    }

    private void setCalendar(Calendar calendar,int y,int m, int d){
        m++;
        calendar.set(Calendar.YEAR,y);
        calendar.set(Calendar.MONTH,m);
        calendar.set(Calendar.DAY_OF_MONTH,d);
    }
    private void initVisualControllers(){
        btn_addCity=findViewById(R.id.btn_AddCity);
        btn_updatePlace=findViewById(R.id.btn_updatePlace);
        spnCategory=findViewById(R.id.spnCategory);
        etCityName=findViewById(R.id.etCityName);
        etIdCountryFK=findViewById(R.id.etIdCountryFK);
        etExperience=findViewById(R.id.etMemories);
        tvData=findViewById(R.id.tvVisitDate);
        tvRating=findViewById(R.id.tvRating);
        rbNrStars=findViewById(R.id.rbNrStarsForm);

    }

    private boolean validateControllers(){

        if(etCityName.getText()==null && etCityName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.camp_obligatoriu,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(etExperience.getText()==null && etExperience.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.camp_obligatoriu,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(etIdCountryFK.getText()==null && etIdCountryFK.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.camp_obligatoriu,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tvData.getText()==null && tvData.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.empty_data,Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private Place createFromVisualContollers(){

        String cityName=etCityName.getText().toString();
        String category=spnCategory.getSelectedItem().toString();
        String visitDate=tvData.getText().toString();
        String memories=etExperience.getText().toString();
        float rating=Float.valueOf(tvRating.getText().toString());
        int idCountry=Integer.parseInt(etIdCountryFK.getText().toString());

        return new Place(cityName,category,visitDate,rating,memories,idCountry);
    }

    private void getObjectValues(Place place) {

        etCityName.setText(place.getCityName());
        etExperience.setText(place.getMemories());
        tvData.setText(place.getVisitDate());
        etIdCountryFK.setText(String.valueOf(place.getIdCountry()));
        rbNrStars.setRating(place.getRating());
        ArrayAdapter adapter=(ArrayAdapter)spnCategory.getAdapter();
        for(int i=0;i<adapter.getCount();i++){
            if(adapter.getItem(i).equals(place.getCategory())){
                spnCategory.setSelection(i);
            }
        }

    }

}
