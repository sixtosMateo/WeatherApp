package com.example.mateosixtos.weatherapp.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.mateosixtos.weatherapp.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mateosixtos on 6/30/17.
 */

public class YahooWeatherService {
    private WeatherServiceCallBack callBack;
    private String location;

    private Exception error;

    public YahooWeatherService(WeatherServiceCallBack callBack){
        this.callBack = callBack;
    }

    public  String getLocation(){
        return location;
    }

    public void refreshWeather( String l){
        this.location = l;

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                //YQL YAHOO QUERY LANGUAGE
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")  ",strings[0]);
                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    //opening the connection from the url
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    //starts reading and storing the data in result till readerline is null
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line =reader.readLine())!= null){
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {

                    error = e;
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
               //checks whether the do it in the background had an error
                if (s == null & error!=null ){
                    callBack.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);


                    JSONObject queryResults = data.optJSONObject("query");
                    int count = queryResults.optInt("count");

                    //check if city inserted is not found gy getting count variable from
                    // the json data of yahoo api
                    if (count==0){
                        callBack.serviceFailure(new LocationWeatherException("No weather information found for "+location));
                        return;
                    }

                    //no error found and everything from the variable result within channel is populate
                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));
                    callBack.serviceSuccess(channel);

                } catch (JSONException e) {
                    callBack.serviceFailure(e);
                }
            }
        }.execute(location);
    }

    public class LocationWeatherException extends Exception{
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }

}
