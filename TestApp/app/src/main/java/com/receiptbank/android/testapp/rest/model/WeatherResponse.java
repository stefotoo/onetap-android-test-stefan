package com.receiptbank.android.testapp.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {

    // variables
    @SerializedName("city")
    private City city;
    @SerializedName("cnt")
    private int forecastCount;
    @SerializedName("list")
    private List<Forecast> forecastList = new ArrayList<>();

    // constructor
    public WeatherResponse() {
        // default
    }

    // methods
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getForecastCount() {
        return forecastCount;
    }

    public void setForecastCount(int forecastCount) {
        this.forecastCount = forecastCount;
    }

    public List<Forecast> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }
}
