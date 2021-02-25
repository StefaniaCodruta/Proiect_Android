package com.example.tarisiorase.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarisiorase.R;
import com.example.tarisiorase.models.Hotel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HotelAdapter extends BaseAdapter {
   public Context context ;
    public List<Hotel> hotelList;

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @Override
    public int getCount() {
        return hotelList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.hotel_item, parent, false);

            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvFacilities=convertView.findViewById(R.id.tvFacilities);
            viewHolder.tvLocation=convertView.findViewById(R.id.tvLocation);
            viewHolder.imgHotel = convertView.findViewById(R.id.imgHotel);
            viewHolder.imgStars = convertView.findViewById(R.id.imgStars);
            viewHolder.tvPrice=convertView.findViewById(R.id.tvPrice);
;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            final Hotel hotel = hotelList.get(position);

            viewHolder.tvName.setText(hotel.getName());
            viewHolder.tvLocation.setText(hotel.getLocation());
            viewHolder.tvFacilities.setText(hotel.getFacilities());
            viewHolder.tvPrice.setText(String.valueOf(hotel.getPrice()));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Drawable drawable = Drawable.
                                createFromStream((InputStream) new URL(hotel.getImgURL()).getContent(), "src");
                        viewHolder.imgHotel.post(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.imgHotel.setImageDrawable(drawable);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Drawable drawable = Drawable.
                            createFromStream((InputStream) new URL(hotel.getStars()).getContent(), "src");
                    viewHolder.imgStars.post(new Runnable() {
                        @Override
                        public void run() {
                            viewHolder.imgStars.setImageDrawable(drawable);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

            return convertView;
    }


    static class ViewHolder{
        TextView tvName,tvFacilities,tvLocation,tvPrice;
        ImageView imgStars, imgHotel;
    }
}
