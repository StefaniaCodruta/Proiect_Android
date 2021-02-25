package com.example.tarisiorase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingActivtiy extends AppCompatActivity {

    private Switch swDarkMode;

    public static final String MY_PREFS="nightMode";
    public static final String IS_NIGHT_MODE="isNight";
    SharedPreferences shPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_activtiy);
        shPreferences=getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);

        swDarkMode=findViewById(R.id.swDarkMode);
        checkNightModeActivated();
        swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveNightModeState(true);
                    recreate();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveNightModeState(false);
                    recreate();
                }
            }


        });
    }

    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor=shPreferences.edit();
        editor.putBoolean(IS_NIGHT_MODE,nightMode);

        editor.apply();

    }

    public void checkNightModeActivated(){
        if(shPreferences.getBoolean(IS_NIGHT_MODE,false)){
            swDarkMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            swDarkMode.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
