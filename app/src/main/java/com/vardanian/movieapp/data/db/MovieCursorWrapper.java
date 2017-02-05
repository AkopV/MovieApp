package com.vardanian.movieapp.data.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.vardanian.movieapp.model.Movie;

import static com.vardanian.movieapp.data.db.MovieDbSchema.*;

public class MovieCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MovieCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Movie getMovie() {
        String id = getString(getColumnIndex(MovieTable.Cols.MOVIE_ID));
        String title = getString(getColumnIndex(MovieTable.Cols.MOVIE_TITLE));
        String overview = getString(getColumnIndex(MovieTable.Cols.MOVIE_OVERVIEW));
        String releaseDate = getString(getColumnIndex(MovieTable.Cols.MOVIE_RELEASE_DATE));
        String popularity = getString(getColumnIndex(MovieTable.Cols.MOVIE_POPULARITY));
        String posterPath = getString(getColumnIndex(MovieTable.Cols.MOVIE_POSTER_PATH));
        String backdropPath = getString(getColumnIndex(MovieTable.Cols.MOVIE_BACKDROP_PATH));
        String voteAverage = getString(getColumnIndex(MovieTable.Cols.MOVIE_VOTE_AVERAGE));

        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setOverview(overview);
        movie.setReleaseDate(releaseDate);
        movie.setPopularity(popularity);
        movie.setPosterPath(posterPath);
        movie.setBackdropPath(backdropPath);
        movie.setVoteAverage(voteAverage);

        return movie;

    }
}
