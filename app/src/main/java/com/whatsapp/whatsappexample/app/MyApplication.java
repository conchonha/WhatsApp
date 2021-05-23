package com.whatsapp.whatsappexample.app;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;


public class MyApplication extends Application {
    public static ViewModelProvider.Factory factory;
    @Override
    public void onCreate() {
        super.onCreate();
        factory = new ViewModelProvider.AndroidViewModelFactory(this);
    }

}
