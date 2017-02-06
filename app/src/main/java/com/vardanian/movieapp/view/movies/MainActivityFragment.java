package com.vardanian.movieapp.view.movies;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vardanian.movieapp.R;
import com.vardanian.movieapp.interfaces.MVPMovies;
import com.vardanian.movieapp.model.Movie;
import com.vardanian.movieapp.presenter.MoviesPresenter;
import com.vardanian.movieapp.view.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment implements MVPMovies.MoviesView {

    private static final String TAG = "MainActivityFragment";

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    private List<Movie> movies;
    private MovieAdapter adapter;

    private MVPMovies.MoviesPresenter presenter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, v);

        initUI(v);
        presenter = new MoviesPresenter(getContext());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.setView(this);
        presenter.getMovies();
    }

    private void initUI(View root) {
        movies = new ArrayList<>();
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2));
        setupAdapter();
    }

    private void setupAdapter() {
        movies = new ArrayList<>();
        adapter = new MovieAdapter(getContext(), movies);
        rvMovies.setAdapter(adapter);
    }

    @Override
    public void onMoviesReceived(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
    }
}
