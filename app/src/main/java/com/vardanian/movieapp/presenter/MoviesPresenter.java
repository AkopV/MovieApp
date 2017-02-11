package com.vardanian.movieapp.presenter;

import android.content.Context;

import com.vardanian.movieapp.data.DataRepository;
import com.vardanian.movieapp.interfaces.IListener;
import com.vardanian.movieapp.interfaces.MVPMovies;
import com.vardanian.movieapp.model.Movie;

import java.util.List;

public class MoviesPresenter implements MVPMovies.MoviesPresenter {

    private MVPMovies.MoviesView view;
    private final MVPMovies.MoviesModel model;

    public MoviesPresenter(DataRepository dataRepository) {
        this.model = dataRepository;
    }

    @Override
    public void setView(MVPMovies.MoviesView view) {
        this.view = view;
    }

    @Override
    public void getMovies() {
        model.fetchMovies(new IListener() {
            @Override
            public void onResult(List<Movie> movies) {
                if (MoviesPresenter.this.view != null){
                    MoviesPresenter.this.view.onMoviesReceived(movies);
                }
            }

            @Override
            public void onError(Exception e) {
                if (MoviesPresenter.this.view != null) {
                    MoviesPresenter.this.view.onError(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void onMovieSelected(int id) {

    }
}
