package com.example.tarisiorase.models;

import android.os.AsyncTask;

import com.example.tarisiorase.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkAsyncUtil extends AsyncTask<URL,Void, ArrayList<Hotel>> {
    @Override
    protected ArrayList<Hotel> doInBackground(URL... urls) {

        String data="";
        try {
            HttpURLConnection httpURLConnection=(HttpURLConnection)urls[0].openConnection();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder stringBuilder=new StringBuilder();

            while((inputLine=bufferedReader.readLine())!=null){
                stringBuilder.append(inputLine);
            }
            data=stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getListOfHotels(data);
    }

    private ArrayList<Hotel> getListOfHotels(String string)  {
        ArrayList<Hotel> listHotels=new ArrayList<>();
        try {
            JSONObject  object = new JSONObject(string);
            JSONArray hotels=object.getJSONArray("hoteluri");
            for(int i=0;i<hotels.length();i++){
                JSONObject jsonHotel=(JSONObject)hotels.get(i);
                Hotel hotel=new Hotel();

                hotel.setPrice(jsonHotel.getDouble("pret"));
                hotel.setImgURL(jsonHotel.getString("imgURL"));
                hotel.setName(jsonHotel.getString("denumire"));
                hotel.setStars(jsonHotel.getString("urlStars"));
                hotel.setFacilities(jsonHotel.getString("facilitati"));
                hotel.setLocation(jsonHotel.getString("localizare"));

                listHotels.add(hotel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listHotels;
    }

}
