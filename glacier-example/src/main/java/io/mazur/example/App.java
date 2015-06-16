package io.mazur.example;

import android.app.Application;

import io.mazur.glacier.Glacier;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Glacier.init(getApplicationContext());
    }
}
