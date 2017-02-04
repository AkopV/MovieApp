package com.vardanian.movieapp.interfaces;

import com.vardanian.movieapp.BuildConfig;

public class Constants {

    public static final int MOVIE_COUNT = 20;
    public static final String URL_FETCH_MOVIES =
            "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + BuildConfig.OPEN_TMDB_API_KEY;
}
