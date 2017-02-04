package com.vardanian.movieapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    // keys for packing/unpacking intent
    // for url building
    public static final String WIDTH_154 = "w154";
    public static final String WIDTH_342 = "w342";
    public static final String WIDTH_500 = "w500";
    public static final String WIDTH_780 = "w780";

    private static final String URL_IMAGE_TMDB_DEFAULT = "http://image.tmdb.org/t/p/";
    public static final String KEY_TITLE = "original_title";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_RATE = "vote_average";
    public static final String KEY_RELEASE_DATE = "release_date";
    public static final String KEY_ID = "id";
    public static final String TABLE_MOVIE = "movies";

    public static String[] projection = {
            KEY_ID,
            KEY_TITLE,
            KEY_OVERVIEW,
            KEY_POSTER_PATH,
            KEY_RATE
    };

    public String id;
    public String title;
    public String overview;
    public String releaseDate;
    public String popularity;
    public String posterPath;

    public Movie(){}

    public Movie(String id, String title, String overview, String releaseDate, String popularity, String posterPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.posterPath = posterPath;
    }

    public static Movie getItemFromCursor(Cursor c) {
        Movie item = new Movie();
        item.id = c.getString(c.getColumnIndex(Movie.KEY_ID));
        item.title = c.getString(c.getColumnIndex(Movie.KEY_TITLE));
        item.overview = c.getString(c.getColumnIndex(Movie.KEY_OVERVIEW));
        item.posterPath = c.getString(c.getColumnIndex(Movie.KEY_POSTER_PATH));
        item.popularity = c.getString(c.getColumnIndex(Movie.KEY_RATE));

        return item;
    }
    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        popularity = in.readString();
        posterPath = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath(String preferedWidth) {
        StringBuilder sb = new StringBuilder();
        sb.append(URL_IMAGE_TMDB_DEFAULT);
        sb.append(preferedWidth);
        sb.append(posterPath);

        return sb.toString();
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return title + " " + id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(popularity);
        dest.writeString(posterPath);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
