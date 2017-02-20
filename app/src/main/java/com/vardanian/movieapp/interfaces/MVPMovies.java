package com.vardanian.movieapp.interfaces;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.vardanian.movieapp.model.Movie;

import java.util.List;

import rx.Observable;

public interface MVPMovies {

    interface MoviesView {
        void onMoviesReceived(List<Movie> movies);
        void onError(String error);
    }

    interface DetailMovieView {
        void onMovieReceived(Movie movie);
        void onError(String error);
    }

    interface MoviesPresenter {
        void setView(MoviesView view);

        void getMovies();
        void onMovieSelected(int id);
    }

    interface DetailMoviePresenter {
        void setView(DetailMovieView view);
        void loadData(Intent intent);
    }

    interface MoviesModel {
        Observable<List<Movie>> fetchMovies();
    }
}
