package com.apollo.andorid.apollosearch;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class SearchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
