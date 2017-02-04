package com.vardanian.movieapp.data.network;

import android.content.Context;

import com.vardanian.movieapp.interfaces.Constants;

public class NetworkStorage {

    private final Context context;

    public NetworkStorage(Context context) {
        this.context = context;
    }

    public void getMoviesApi(MovieItemTask.IResultListener listener) {
        new MovieItemTask(listener).execute(Constants.URL_FETCH_MOVIES);
    }
}
