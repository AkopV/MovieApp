package com.vardanian.movieapp.network;

import android.net.Uri;
import android.util.Log;

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
    private static final String API_KEY = "a9d3aada0aca0bb26c2f47c7e8207893";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
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

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Movie> movieItems() {

        List<Movie> items = new ArrayList<>();

        try {
            String url = Uri.parse("https://api.themoviedb.org/3/movie/top_rated")
                    .buildUpon()
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("language", "en-US")
                    .appendQueryParameter("page", "1")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            parseItems(items, jsonString);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to movie items", ioe);
        }
        return items;
    }

    private void parseItems(List<Movie> movies, String jsonObject) throws IOException, JSONException {

        JSONObject moviesJsonObject = new JSONObject(jsonObject);
        JSONArray movieJsonArray = moviesJsonObject.getJSONArray("results");

        for (int i = 0; i < movieJsonArray.length(); i++) {
            JSONObject movieJsonObject = movieJsonArray.getJSONObject(i);
            Movie movie = new Movie();
            movie.setId(movieJsonObject.getString("id"));
            movie.setTitle(movieJsonObject.getString("title"));
            movie.setOverview(movieJsonObject.getString("overview"));
            movie.setReleaseDate(movieJsonObject.getString("release_date"));
            movie.setPopularity(movieJsonObject.getString("popularity"));
            movie.setPosterPath(movieJsonObject.getString("poster_path"));

            movies.add(movie);
        }
    }
}