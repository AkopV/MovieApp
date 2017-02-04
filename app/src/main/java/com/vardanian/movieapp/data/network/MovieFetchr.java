package com.vardanian.movieapp.data.network;

import android.net.Uri;
import android.util.Log;

import com.vardanian.movieapp.BuildConfig;
import com.vardanian.movieapp.interfaces.Constants;
import com.vardanian.movieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieFetchr {

    private static final String TAG = "MovieFetchr";
    private static final String API_KEY = BuildConfig.OPEN_TMDB_API_KEY;
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_POPULARITY = "popularity";
    private static final String MOVIE_POSTER_PATH = "poster_path";

    public static byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public static String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public static List<Movie> movieItems() {

        List<Movie> movies = new ArrayList<>();

        try {
            String url = Uri.parse("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&")
                    .buildUpon()
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("language", "en-US")
                    .appendQueryParameter("page", "1")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            parseItems(jsonString);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to movie movies", ioe);
        }
        return movies;
    }

    public static List<Movie> parseItems(String jsonObject) throws IOException, JSONException {

        JSONObject moviesJsonObject = new JSONObject(jsonObject);
        JSONArray movieJsonArray = moviesJsonObject.getJSONArray("results");

        List<Movie> movies = new ArrayList<>(Constants.MOVIE_COUNT);
        int length = movieJsonArray.length() < Constants.MOVIE_COUNT ? movieJsonArray.length() : Constants.MOVIE_COUNT;
        for (int i = 0; i < Constants.MOVIE_COUNT; i++) {
            JSONObject movieJsonObject = movieJsonArray.getJSONObject(i);
            Movie movie = new Movie();
            movie.setId(movieJsonObject.getString(MOVIE_ID));
            movie.setTitle(movieJsonObject.getString(MOVIE_TITLE));
            movie.setOverview(movieJsonObject.getString(MOVIE_OVERVIEW));
            movie.setReleaseDate(movieJsonObject.getString(MOVIE_RELEASE_DATE));
            movie.setPopularity(movieJsonObject.getString(MOVIE_POPULARITY));
            movie.setPosterPath(movieJsonObject.getString(MOVIE_POSTER_PATH));

            movies.add(movie);
        }
        return movies;
    }
}