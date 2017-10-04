package com.example.mateosixtos.weatherapp.service;

import com.example.mateosixtos.weatherapp.data.Channel;

/**
 * Created by mateosixtos on 6/30/17.
 */

public interface WeatherServiceCallBack {

    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
