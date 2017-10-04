package com.example.mateosixtos.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by mateosixtos on 6/30/17.
 */

public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));

    }
}
