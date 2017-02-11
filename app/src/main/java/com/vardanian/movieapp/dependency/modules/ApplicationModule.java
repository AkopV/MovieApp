package com.vardanian.movieapp.dependency.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app.getApplicationContext();
    }
}
