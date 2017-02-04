package com.vardanian.movieapp.presenter;

import android.content.Context;
import android.content.Intent;

import com.vardanian.movieapp.data.DataRepository;
import com.vardanian.movieapp.interfaces.MVPMovies;
import com.vardanian.movieapp.model.Movie;

public class DetailMoviePresenter implements MVPMovies.DetailMoviePresenter {

    private MVPMovies.DetailMovieView view;
    private final MVPMovies.MoviesModel model;

    public DetailMoviePresenter(Context context) {
        model = new DataRepository(context);
    }

    @Override
    public void setView(MVPMovies.DetailMovieView view) {
        this.view = view;
    }

    @Override
    public void loadData(Intent intent) {
        if (intent == null) {
            DetailMoviePresenter.this.view.onError("Can not load data");
        } else {
            Movie movie = intent.getParcelableExtra(
                    Movie.class.getName());
            DetailMoviePresenter.this.view.onMovieReceived(movie);
        }
    }
}
