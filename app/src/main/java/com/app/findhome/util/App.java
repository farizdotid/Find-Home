package com.app.findhome.util;

import android.app.Application;

import com.app.findhome.util.database.DatabaseClient;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseClient.getInstance(this);
        SharedPrefManager.getInstance(this);
    }
}
