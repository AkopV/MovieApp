package com.vardanian.movieapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    public static final String WIDTH_154 = "w154";
    public static final String WIDTH_342 = "w342";
    public static final String WIDTH_500 = "w500";
    public static final String WIDTH_780 = "w780";

    public static final String URL_IMAGE_TMDB_DEFAULT = "http://image.tmdb.org/t/p/";
    public static final String KEY_TITLE = "title";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_BACKDROP_PATH = "backdrop_path";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_RATE = "vote_average";
    public static final String KEY_POPULARITY = "popularity";
    public static final String KEY_RELEASE_DATE = "release_date";
    public static final String KEY_ID = "id";
    public static final String KEY_ADULT = "adult";
    public static final String KEY_ORIGINAL_LANGUAGE = "original_language";
    public static final String KEY_VOTE_COUNT = "vote_count";
    public static final String KEY_VIDEO = "video";
    public static final String TABLE_MOVIE = "movies";

    public static String[] projection = {
            KEY_ID,
            KEY_TITLE,
            KEY_OVERVIEW,
            KEY_POSTER_PATH,
            KEY_BACKDROP_PATH,
            KEY_RELEASE_DATE,
            KEY_POPULARITY,
            KEY_RATE,
            KEY_ADULT,
            KEY_ORIGINAL_LANGUAGE,
            KEY_VOTE_COUNT,
            KEY_VIDEO
    };

    @SerializedName(KEY_ID)
    public String id;
    @SerializedName(KEY_TITLE)
    public String title;
    @SerializedName(KEY_OVERVIEW)
    public String overview;
    @SerializedName(KEY_RELEASE_DATE)
    public String releaseDate;
    @SerializedName(KEY_POPULARITY)
    public String popularity;
    @SerializedName(KEY_POSTER_PATH)
    public String posterPath;
    @SerializedName(KEY_BACKDROP_PATH)
    public String backdropPath;
    @SerializedName(KEY_RATE)
    public String voteAverage;
    @SerializedName(KEY_ADULT)
    public String adult;
    @SerializedName(KEY_ORIGINAL_LANGUAGE)
    public String originalLanguage;
    @SerializedName(KEY_VOTE_COUNT)
    public String voteCount;
    @SerializedName(KEY_VIDEO)
    public String video;

    public Movie(){}

    public Movie(String id, String title, String overview, String releaseDate, String popularity, String posterPath,
                 String voteAverage, String backdropPath, String adult, String originalLanguage, String originalTitle,
                 String voteCount, String video) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.adult = adult;
        this.originalLanguage = originalLanguage;
        this.voteCount = voteCount;
        this.video = video;
    }

    public static Movie getItemFromCursor(Cursor c) {
        Movie item = new Movie();
        item.id = c.getString(c.getColumnIndex(Movie.KEY_ID));
        item.title = c.getString(c.getColumnIndex(Movie.KEY_TITLE));
        item.overview = c.getString(c.getColumnIndex(Movie.KEY_OVERVIEW));
        item.posterPath = c.getString(c.getColumnIndex(Movie.KEY_POSTER_PATH));
        item.popularity = c.getString(c.getColumnIndex(Movie.KEY_POPULARITY));
        item.backdropPath = c.getString(c.getColumnIndex(Movie.KEY_BACKDROP_PATH));
        item.voteAverage = c.getString(c.getColumnIndex(Movie.KEY_RATE));
        item.adult = c.getString(c.getColumnIndex(Movie.KEY_ADULT));
        item.originalLanguage = c.getString(c.getColumnIndex(Movie.KEY_ORIGINAL_LANGUAGE));
        item.voteCount = c.getString(c.getColumnIndex(Movie.KEY_VOTE_COUNT));
        item.video = c.getString(c.getColumnIndex(Movie.KEY_VIDEO));

        return item;
    }
    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        popularity = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        voteAverage = in.readString();
        adult = in.readString();
        originalLanguage = in.readString();
        voteCount = in.readString();
        video = in.readString();
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

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
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

    public String getBackdropPath(String preferedWidth) {
        StringBuilder sb = new StringBuilder();
        sb.append(URL_IMAGE_TMDB_DEFAULT);
        sb.append(preferedWidth);
        sb.append(backdropPath);
        return sb.toString();
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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
        dest.writeString(backdropPath);
        dest.writeString(voteAverage);
        dest.writeString(adult);
        dest.writeString(originalLanguage);
        dest.writeString(voteCount);
        dest.writeString(video);
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
