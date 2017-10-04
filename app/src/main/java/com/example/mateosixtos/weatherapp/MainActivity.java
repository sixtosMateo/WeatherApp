package com.example.mateosixtos.weatherapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateosixtos.weatherapp.data.Channel;
import com.example.mateosixtos.weatherapp.data.Item;
import com.example.mateosixtos.weatherapp.service.WeatherServiceCallBack;
import com.example.mateosixtos.weatherapp.service.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallBack {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWeatherService service;
    private AlertDialog.Builder dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImageView = (ImageView)findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView)findViewById(R.id.tempatureTextView);
        conditionTextView = (TextView)findViewById(R.id.conditionTextView);
        locationTextView = (TextView)findViewById(R.id.locationTextView);

        service = new YahooWeatherService(this);

//        dialog = new AlertDialog.Builder(this);
//        dialog.setMessage("Loading...");
//        dialog.show();

        service.refreshWeather("Salinas,California");


    }

    @Override
    public void serviceSuccess(Channel channel) {
//        dialog.setCancelable(true);

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_"+ item.getCondition().getCode(),null,getPackageName());
        Drawable weatherIconDrawable = getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0"+ channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());



    }

    @Override
    public void serviceFailure(Exception exception) {
//        dialog.setCancelable(true);
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
    }
}
