package com.vardanian.movieapp.data.db.dao;

import com.vardanian.movieapp.model.Movie;

import java.util.List;

import rx.Observable;

public interface MoviesDAO {

    public long addMovie(Movie movie);
    public Movie getMovie(int id);
    public Observable<List<Movie>> getAllMovies();
    public void saveAllMovies(List<Movie> movies);
    public void updateMovie(Movie movie);
    public boolean deleteMovie(Movie movie);
}
