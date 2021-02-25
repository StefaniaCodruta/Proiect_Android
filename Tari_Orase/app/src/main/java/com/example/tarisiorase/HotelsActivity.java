package com.example.tarisiorase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.example.tarisiorase.adapters.HotelAdapter;
import com.example.tarisiorase.models.Hotel;
import com.example.tarisiorase.models.NetworkAsyncUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HotelsActivity extends AppCompatActivity {
  HotelAdapter hotelAdapter;
 ListView lvHotel;
   ArrayList<Hotel> listHotels=new ArrayList<>();

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        listHotels= getIntent().getParcelableArrayListExtra(MainActivity.HOTELS_LIST);


            lvHotel=findViewById(R.id.lvHotels);
            hotelAdapter=new HotelAdapter(this,listHotels);
            lvHotel.setAdapter(hotelAdapter);


    }


}
