package com.vardanian.movieapp.data;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.support.annotation.NonNull;

import com.vardanian.movieapp.data.db.dao.DatabaseStorage;
import com.vardanian.movieapp.data.network.MovieFetchr;
import com.vardanian.movieapp.data.network.MovieItemTask;
import com.vardanian.movieapp.data.network.NetworkStorage;
import com.vardanian.movieapp.interfaces.IListener;
import com.vardanian.movieapp.interfaces.MVPMovies;
import com.vardanian.movieapp.model.Movie;
import com.vardanian.movieapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.MissingResourceException;

public class DataRepository implements MVPMovies.MoviesModel {

    private final Context context;
    private final DatabaseStorage db;
    private final NetworkStorage rest;

    public DataRepository(Context context) {
        this.context = context;
        db = new DatabaseStorage(context);
        rest = new NetworkStorage(context);
    }

    @Override
    public void fetchMovies(final IListener presenterListener) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            List<Movie> dbMovies = db.getAllMovies();
            if (dbMovies != null) {
                presenterListener.onResult(dbMovies);
            } else {
                presenterListener.onError(new MissingResourceException(null, null, null));
            }
        } else {
            rest.getMoviesApi(new MovieItemTask.IResultListener() {
                @Override
                public void onResult(@NonNull String result) {
                    List<Movie> fetchedMovies = null;
                    try {
                        fetchedMovies = MovieFetchr.parseItems(result);

                        db.saveAllMovies(fetchedMovies);
                        presenterListener.onResult(fetchedMovies);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        presenterListener.onError(e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        presenterListener.onError(e);
                    }
                }

                @Override
                public void onError(String error) {
                    presenterListener.onError(new NetworkErrorException(error));
                }
            });
        }
    }
}
