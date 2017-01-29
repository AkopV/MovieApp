package com.vardanian.movieapp.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vardanian.movieapp.db.MovieCursorWrapper;
import com.vardanian.movieapp.db.MovieOpenHelper;
import com.vardanian.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.vardanian.movieapp.db.MovieDbSchema.*;

public class MoviesDAOImpl implements MoviesDAO {

    private static final String TAG = "MoviesDAOImpl";
    private Context context;
    private MovieOpenHelper dbHelper;
    private SQLiteDatabase db;


    public MoviesDAOImpl(Context context) {
        this.context = context.getApplicationContext();
        dbHelper = new MovieOpenHelper(context);
    }

    private static ContentValues getContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieTable.Cols.MOVIE_ID, movie.getId());
        values.put(MovieTable.Cols.MOVIE_TITLE, movie.getTitle());
        values.put(MovieTable.Cols.MOVIE_OVERVIEW, movie.getOverview());
        values.put(MovieTable.Cols.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieTable.Cols.MOVIE_POPULARITY, movie.getPopularity());
        values.put(MovieTable.Cols.MOVIE_POSTER_PATH, movie.getPosterPath());

        return values;
    }

    private MovieCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = db.query(
                MovieTable.NAME,
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
        long _id = db.insert(MovieTable.NAME, null, values);
        Log.i(TAG, "Insert movie to database");
        db.close();

        return _id;
    }

    @Override
    public Movie getMovie(int id) {
        String[] whereArgs = new String[]{String.valueOf(id)};
        MovieCursorWrapper cursor = queryCrimes(
                MovieTable.Cols.MOVIE_ID + " = ? ",
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
    public List<Movie> getAllMovies() {
        db = dbHelper.getWritableDatabase();
        List<Movie> movies = new ArrayList<>();
        MovieCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                movies.add(cursor.getMovie());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
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

        db.update(MovieTable.NAME, values, MovieTable.Cols.MOVIE_ID + " = ?", new String[] {id});
    }

    @Override
    public boolean deleteMovie(Movie movie) {
        boolean deleteSuccessfull = false;

        db = dbHelper.getWritableDatabase();
        deleteSuccessfull = db.delete(
                MovieTable.NAME,
                MovieTable.Cols.MOVIE_ID + " = " + movie.getId(),
                null) > 0;
        db.close();

        return deleteSuccessfull;
    }
}
