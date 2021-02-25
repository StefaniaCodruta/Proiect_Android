package com.example.tarisiorase.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tarisiorase.R;
import com.example.tarisiorase.models.Country;
import com.example.tarisiorase.models.Tradition;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    Context context;
    List<Country> countriesList;

    public CountryAdapter(Context context, List<Country> countriesList) {
        this.context = context;
        this.countriesList = countriesList;
    }

    @Override
    public int getCount() {
        return countriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return countriesList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);

            viewHolder.tvCountryIdAd = convertView.findViewById(R.id.tvCountryIdAd);
            viewHolder.tvNameCountryAd=convertView.findViewById(R.id.tvNameCountryAd);
            viewHolder.tvContinentAd=convertView.findViewById(R.id.tvContinentAd);
            viewHolder.tvLanguageAd=convertView.findViewById(R.id.tvLanguageAd);
            viewHolder.tvCapitalAd=convertView.findViewById(R.id.tvCapitalAd);
            viewHolder.tvReligionAd=convertView.findViewById(R.id.tvReligionAd);
            viewHolder.tvPopulationAd=convertView.findViewById(R.id.tvPopulationAd);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Country country = countriesList.get(position);

        viewHolder.tvCountryIdAd.setText(context.getString(R.string.id)+country.getIdCountry());
        viewHolder.tvNameCountryAd.setText(context.getString(R.string.den)+country.getName());
        viewHolder.tvContinentAd.setText(context.getString(R.string.continent)+country.getContinent());
        viewHolder.tvLanguageAd.setText(context.getString(R.string.limba)+country.getLanguage());
        viewHolder.tvCapitalAd.setText(context.getString(R.string.cap)+country.getCapital());
        viewHolder.tvReligionAd.setText(context.getString(R.string.religie) +country.getReligion());
        viewHolder.tvPopulationAd.setText(context.getString(R.string.populatia) +country.getPopulation());

        return convertView;
    }

    static class ViewHolder{
        TextView tvCountryIdAd, tvNameCountryAd,tvContinentAd,tvLanguageAd,tvCapitalAd,tvReligionAd,tvPopulationAd;

    }
}
