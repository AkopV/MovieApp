package com.vardanian.movieapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vardanian.movieapp.db.dao.MoviesDAOImpl;
import com.vardanian.movieapp.model.Movie;
import com.vardanian.movieapp.network.MovieFetchr;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "MainActivityFragment";

    private RecyclerView rvMovies;
    private List<Movie> movies;
    private MovieAdapter adapter;
    private MoviesDAOImpl db;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Log.i(TAG, "Background thread start");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        rvMovies = (RecyclerView) v.findViewById(R.id.rv_movies);
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2));
        setupAdapter();

        db = new MoviesDAOImpl(getContext());
        if (!isNetworkAvailable(getContext())) {
            List<Movie> dbMovies = db.getAllMovies();
            if (dbMovies != null) {
                movies.addAll(dbMovies);
                adapter.notifyDataSetChanged();
            }
        } else new MovieItemTask().execute();

        return v;
    }

    private void setupAdapter() {
        movies = new ArrayList<>();
        adapter = new MovieAdapter(getContext(), movies);
        rvMovies.setAdapter(adapter);
    }

    public boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private class MovieItemTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... params) {
            return new MovieFetchr().movieItems();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            MainActivityFragment.this.movies.addAll(movies);
            adapter.notifyDataSetChanged();

            try {
                db.saveAllMovies(movies);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
