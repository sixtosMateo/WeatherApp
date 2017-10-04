package com.example.mateosixtos.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by mateosixtos on 6/30/17.
 */

public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
