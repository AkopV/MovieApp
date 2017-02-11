package com.vardanian.movieapp;

import android.app.Application;

import com.vardanian.movieapp.dependency.component.ApplicationComponent;
import com.vardanian.movieapp.dependency.component.DaggerApplicationComponent;
import com.vardanian.movieapp.dependency.modules.ApplicationModule;

import dagger.internal.DaggerCollections;

public class MovieApp extends Application{

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component =  DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
