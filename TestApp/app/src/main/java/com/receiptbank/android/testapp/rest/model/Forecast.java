package com.receiptbank.android.testapp.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast {

    // variables
    @SerializedName("dt")
    private Long date;
    @SerializedName("pressure")
    private Double pressure;
    @SerializedName("humidity")
    private Double humidity;
    @SerializedName("weather")
    private List<Weather> weatherList = new ArrayList<>();

    // constructor
    public Forecast() {
        // default
    }

    // methods
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}
