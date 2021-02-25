package com.example.tarisiorase.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tarisiorase.R;
import com.example.tarisiorase.models.Place;

import java.util.List;

public class PlaceAdapter extends BaseAdapter {
    Context context;
    List<Place> placesList;

    public PlaceAdapter(Context context, List<Place> placesList) {
        this.context = context;
        this.placesList = placesList;
    }

    @Override
    public int getCount() {
        return placesList.size();
    }

    @Override
    public Object getItem(int position) {
        return placesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.place_item ,parent, false);

            viewHolder.tvIPlaceAd = convertView.findViewById(R.id.tvIPlaceAd);
            viewHolder.tvCityNameAd=convertView.findViewById(R.id.tvCityNameAd);
            viewHolder.tvCategoryName=convertView.findViewById(R.id.tvCategoryName);
            viewHolder.tvVisitDateAd=convertView.findViewById(R.id.tvVisitDateAd);
            viewHolder.tvRatingAd=convertView.findViewById(R.id.tvRatingAd);
            viewHolder.tvMemoriesAd=convertView.findViewById(R.id.tvMemoriesAd);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Place place = placesList.get(position);

        viewHolder.tvIPlaceAd.setText(String.valueOf(place.getIdPlace()));
        viewHolder.tvCityNameAd.setText(place.getCityName());
        viewHolder.tvCategoryName.setText(place.getCategory());
        viewHolder.tvVisitDateAd.setText(place.getVisitDate());
        viewHolder.tvRatingAd.setText(String.valueOf(place.getRating()));
        viewHolder.tvMemoriesAd.setText(place.getMemories());

        return convertView;
    }

    static class ViewHolder{
        TextView tvIPlaceAd, tvCityNameAd,tvCategoryName,tvVisitDateAd,tvRatingAd,tvMemoriesAd;
    }
}
