package com.vardanian.movieapp.db;

public class MovieDbSchema {

    public static final class MovieTable {
        public static final String NAME = "movies";

        public static final class Cols {
            public static final String MOVIE_ID = "id";
            public static final String MOVIE_TITLE = "title";
            public static final String MOVIE_OVERVIEW = "overview";
            public static final String MOVIE_RELEASE_DATE = "release_date";
            public static final String MOVIE_POPULARITY = "popularity";
            public static final String MOVIE_POSTER_PATH = "poster_path";
        }
    }
}
