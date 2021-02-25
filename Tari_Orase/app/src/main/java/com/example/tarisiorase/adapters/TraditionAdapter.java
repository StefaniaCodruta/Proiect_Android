package com.example.tarisiorase.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarisiorase.R;
import com.example.tarisiorase.models.Tradition;

import java.util.List;

public class TraditionAdapter extends BaseAdapter {
    Context context;
    List<Tradition> traditionsList;

    public TraditionAdapter(Context context, List<Tradition> traditionsList) {
        this.context = context;
        this.traditionsList = traditionsList;
    }

    @Override
    public int getCount() {
        return traditionsList.size();
    }

    @Override
    public Object getItem(int position) {
        return traditionsList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.tradition_item, parent, false);

            viewHolder.tvLocationAd = convertView.findViewById(R.id.tvLocationAd);
            viewHolder.tvMomentAd=convertView.findViewById(R.id.tvMomentAd);
            viewHolder.tvShortDescriptionAd=convertView.findViewById(R.id.tvShortDescriptionAd);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Tradition tradition = traditionsList.get(position);

        viewHolder.tvLocationAd.setText("Locatie: "+tradition.getLocation());
        viewHolder.tvMomentAd.setText("Moment: "+tradition.getMoment());
        viewHolder.tvShortDescriptionAd.setText("Descriere: "+tradition.getShortDescription());

        return convertView;
    }

    static class ViewHolder{
        TextView tvLocationAd,tvMomentAd,tvShortDescriptionAd;

    }
}
