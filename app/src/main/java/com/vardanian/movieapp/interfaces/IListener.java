package com.vardanian.movieapp.interfaces;

import com.vardanian.movieapp.model.Movie;

import java.util.List;

public interface IListener {

    void onResult(List<Movie> movies);
    void onError(Exception e);
}
