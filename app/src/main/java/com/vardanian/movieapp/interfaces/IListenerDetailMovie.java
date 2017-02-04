package com.vardanian.movieapp.interfaces;

import com.vardanian.movieapp.model.Movie;

public interface IListenerDetailMovie {

    void onResult(Movie movie);
    void onError(Exception e);
}
