package com.vardanian.movieapp.data.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.vardanian.movieapp.interfaces.Constants;

import java.io.IOException;

public class MovieItemTask extends AsyncTask<String, Void, String>{

    private static final String LOG_TAG = MovieItemTask.class.getSimpleName();
    private IResultListener listener;

    public MovieItemTask(IResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String json = null;
        try {
            json = new MovieFetchr().getUrlString(Constants.URL_FETCH_MOVIES);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null)
            listener.onResult(result);
        else listener.onError("Error");
    }

    public interface IResultListener {
        void onResult(@NonNull String result);
        void onError(String error);
    }
}
