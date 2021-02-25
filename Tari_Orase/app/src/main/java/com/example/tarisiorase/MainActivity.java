package com.example.tarisiorase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tarisiorase.models.Hotel;
import com.example.tarisiorase.models.NetworkAsyncUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
CardView cardPlaces, cardSettings, cardHotels, cardTravels;
public  ArrayList<Hotel> listHotels=new ArrayList<>();
    public final static String HOTELS_LIST="listaHoteluri";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardPlaces=findViewById(R.id.idPlaces);
        cardHotels=findViewById(R.id.idHotels);
        cardTravels=findViewById(R.id.idTravels);
        cardSettings=findViewById(R.id.idSettings);

        cardPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TraditionsActivity.class));
            }
        });

        cardHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String address="https://api.mocki.io/v1/0b25a26e";
                try {
                    URL url=new URL(address);
                    @SuppressLint("StaticFieldLeak") NetworkAsyncUtil netAsyncUtil = new NetworkAsyncUtil(){
                        @Override
                        protected void onPostExecute(ArrayList<Hotel> hotels) {
                            listHotels.addAll(hotels);
                            Intent intent=new Intent(MainActivity.this,HotelsActivity.class);
                            intent.putParcelableArrayListExtra(HOTELS_LIST, listHotels);
                            for(Hotel elem:listHotels){
                                Log.e("Hoteluri ",elem.toString()); }
                              startActivity(intent);
                        }
                    };
                    netAsyncUtil.execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        cardTravels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TravelActivity.class);
                startActivity(intent);
            }
        });

        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SettingActivtiy.class);
                startActivity(intent);
            }
        });
    }
}
