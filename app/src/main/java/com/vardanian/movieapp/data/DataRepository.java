package com.vardanian.movieapp.data;

import android.content.Context;

import com.vardanian.movieapp.data.db.dao.DatabaseStorage;
import com.vardanian.movieapp.data.network.NetworkStorage;
import com.vardanian.movieapp.interfaces.MVPMovies;
import com.vardanian.movieapp.model.Movie;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

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
    public Observable<List<Movie>> fetchMovies() {
        return db.getAllMovies().switchIfEmpty(
                rest.getMovies()
                .doOnNext(new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> movies) {
                        if(movies != null) {
                            db.saveAllMovies(movies);
                        }
                    }
                }));
    }
}
