package com.receiptbank.android.testapp.rest.services;

import com.receiptbank.android.testapp.rest.model.WeatherResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface AuthRestService {
    @GET("/daily")
    void getDailyForecast(@Query("lat") Double lat,
                       @Query("lon") Double lng,
                       @Query("cnt") Integer period,
                       @Query("APPID") String appId,
                       Callback<WeatherResponse> response);
}