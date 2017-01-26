package com.vardanian.movieapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vardanian.movieapp.model.Movie;

import static com.vardanian.movieapp.db.MovieDbSchema.MovieTable;

public class MovieOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "movies.db";
    private static final int DB_VERSION = 1;

    public MovieOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MovieTable.NAME + "( " +
                " _ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieTable.Cols.MOVIE_ID + " TEXT NOT NULL, " +
                MovieTable.Cols.MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieTable.Cols.MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MovieTable.Cols.MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieTable.Cols.MOVIE_POPULARITY + " TEXT NOT NULL, " +
                MovieTable.Cols.MOVIE_POSTER_PATH + " TEXT NOT NULL, "
                + ") "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + MovieTable.NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
