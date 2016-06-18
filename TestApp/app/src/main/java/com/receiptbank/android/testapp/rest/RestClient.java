package com.receiptbank.android.testapp.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.receiptbank.android.testapp.rest.services.AuthRestService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {
    // constants
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast";

    // variables
    private RestAdapter restAdapter;
    private AuthRestService authRestService;

    // constructors
    public RestClient() {
        Gson gson = new GsonBuilder()
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    public AuthRestService getAuthRestService() {
        if (authRestService == null) {
            authRestService = restAdapter.create(AuthRestService.class);
        }

        return authRestService;
    }
}