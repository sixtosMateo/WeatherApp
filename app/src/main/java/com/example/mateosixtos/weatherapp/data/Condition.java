package com.example.mateosixtos.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by mateosixtos on 6/30/17.
 */

public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String description;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void populate(JSONObject data) {
        code= data.optInt("code");
        temperature = data.optInt("temp");
        description = data.optString("text");
    }
}
