package com.vardanian.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vardanian.movieapp.model.Movie;
import com.vardanian.movieapp.network.MovieFetchr;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "MainActivityFragment";

    private RecyclerView rvMovie;
    private List<Movie> movieItems = new ArrayList<>();

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new MovieItemTask().execute();
        Log.i(TAG, "Background thread start");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        rvMovie = (RecyclerView) v.findViewById(R.id.rv_movies);
        rvMovie.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2));
        setupAdapter();
        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            rvMovie.setAdapter(new MovieAdapter(getContext(), movieItems));
        }
    }

    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivMovie;
        private Movie movie;
        private Context context;

        public MovieHolder(View itemView) {
            super(itemView);
            movie = new Movie();
            ivMovie = (ImageView) itemView.findViewById(R.id.fragment_movie_image_view);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindMovieItem(Movie movie) {
            this.movie = movie;
            Picasso.with(getActivity())
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.sw)
                    .into(ivMovie);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
            intent.putExtra(Movie.class.getName(), movie);
            v.getContext().startActivity(intent);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

        private List<Movie> movies;
        private Context context;

        public MovieAdapter(Context context, List<Movie> movies) {
            this.context = context;
            this.movies = movies;
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.movie_item, viewGroup, false);
            return new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieHolder movieHolder, int position) {
            movieHolder.movie = getItem(position);
            movieHolder.bindMovieItem(movieHolder.movie);
        }

        public Movie getItem(int position) {
            return movies != null ? movies.get(position) : null;
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

    private class MovieItemTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... params) {
            return new MovieFetchr().movieItems();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            movieItems = movies;
            setupAdapter();
        }
    }
}
