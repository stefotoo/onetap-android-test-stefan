package com.receiptbank.android.testapp;

import android.app.Application;

import com.receiptbank.android.testapp.rest.RestClient;

public class TestApp extends Application {
    private static RestClient restClient;

    @Override
    public void onCreate()
    {
        super.onCreate();

        restClient = new RestClient();
    }

    public static RestClient getRestClient() {
        return restClient;
    }
}
