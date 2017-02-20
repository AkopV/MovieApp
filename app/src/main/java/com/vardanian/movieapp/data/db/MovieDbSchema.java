package com.vardanian.movieapp.data.db;

public class MovieDbSchema {

    public static final class MovieTable {
        public static final String NAME = "movies";

        public static final class Cols {
            public static final String MOVIE_ID = "id";
            public static final String MOVIE_TITLE = "original_title";
            public static final String MOVIE_OVERVIEW = "overview";
            public static final String MOVIE_RELEASE_DATE = "release_date";
            public static final String MOVIE_POPULARITY = "popularity";
            public static final String MOVIE_POSTER_PATH = "poster_path";
            public static final String MOVIE_BACKDROP_PATH = "backdrop_path";
            public static final String MOVIE_VOTE_AVERAGE = "vote_average";
        }
    }
}
