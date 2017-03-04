package com.vardanian.movieapp.data.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vardanian.movieapp.data.db.MovieCursorWrapper;
import com.vardanian.movieapp.data.db.MovieOpenHelper;
import com.vardanian.movieapp.exceptions.CursorIsNullException;
import com.vardanian.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

import static com.vardanian.movieapp.data.db.MovieDbSchema.*;
import static com.vardanian.movieapp.data.db.MovieDbSchema.MovieTable.*;

public class DatabaseStorage implements MoviesDAO {

    private static final String TAG = "DatabaseStorage";
    private static final long STATE_MS = 10 * 1000;
    private long timestamp;
    private Context context;
    private MovieOpenHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseStorage(Context context) {
        this.context = context.getApplicationContext();
        dbHelper = new MovieOpenHelper(context);
    }

    private static ContentValues getContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(Cols.MOVIE_ID, movie.getId());
        values.put(Cols.MOVIE_OVERVIEW, movie.getOverview());
        values.put(Cols.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(Cols.MOVIE_POPULARITY, movie.getPopularity());
        values.put(Cols.MOVIE_POSTER_PATH, movie.posterPath);
        values.put(Cols.MOVIE_BACKDROP_PATH, movie.backdropPath);
        values.put(Cols.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(Cols.MOVIE_ADULT, movie.getAdult());
        values.put(Cols.MOVIE_LANGUAGE, movie.getOriginalLanguage());
        values.put(Cols.MOVIE_TITLE, movie.getTitle());
        values.put(Cols.MOVIE_VOTE_COUNT, movie.getVoteCount());
        values.put(Cols.MOVIE_VIDEO, movie.getVideo());

        return values;
    }

    private MovieCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = db.query(
                NAME,
                null, // Columns - null select all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new MovieCursorWrapper(cursor);
    }

    @Override
    public long addMovie(Movie movie) {
        ContentValues values = getContentValues(movie);
        long _id = db.insert(NAME, null, values);
        Log.i(TAG, "Insert movie to database");
        db.close();

        return _id;
    }

    @Override
    public Movie getMovie(int id) {
        String[] whereArgs = new String[]{String.valueOf(id)};
        MovieCursorWrapper cursor = queryCrimes(
                Cols.MOVIE_ID + " = ? ",
                whereArgs
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            Log.i(TAG, "Get movie from database");
            return cursor.getMovie();
        } finally {
            cursor.close();
        }
    }

    @Override
    public Observable<List<Movie>> getAllMovies() {
        if (!isUpToUpdate()) {
            this.timestamp = System.currentTimeMillis();
            return Observable.empty();
        }
        try {
            final List<Movie> movies = readAllMoviesFromDb();

            if (movies == null || movies.size() == 0) {
                this.timestamp = System.currentTimeMillis();
                return Observable.empty();
            } else {
                return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
                    @Override
                    public void call(Subscriber<? super List<Movie>> subscriber) {
                        try {
                            subscriber.onNext(movies);
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        } catch (CursorIsNullException e) {
            return Observable.error(e);
        }
    }

    private boolean isUpToUpdate() {
        return System.currentTimeMillis() - timestamp < STATE_MS;
    }

    private List<Movie> readAllMoviesFromDb() throws CursorIsNullException {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(Movie.TABLE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor == null) {
            throw new CursorIsNullException();
        }

        List<Movie> movies = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Movie movie  = Movie.getItemFromCursor(cursor);
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return movies;
    }

    @Override
    public void saveAllMovies(List<Movie> movies) {
        db = dbHelper.getWritableDatabase();
        for (Movie movie : movies){
            ContentValues values = getContentValues(movie);
            long id = db.insert(MovieTable.NAME, null, values);
            Log.i(TAG, "Inserted id=" + id);
        }
        db.close();
    }

    @Override
    public void updateMovie(Movie movie) {
        String id = movie.getId().toString();
        ContentValues values = getContentValues(movie);

        db.update(NAME, values, Cols.MOVIE_ID + " = ?", new String[] {id});
    }

    @Override
    public boolean deleteMovie(Movie movie) {
        boolean deleteSuccessfull = false;

        db = dbHelper.getWritableDatabase();
        deleteSuccessfull = db.delete(
                NAME,
                Cols.MOVIE_ID + " = " + movie.getId(),
                null) > 0;
        db.close();

        return deleteSuccessfull;
    }
}
