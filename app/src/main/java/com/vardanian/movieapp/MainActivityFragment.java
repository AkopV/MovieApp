package com.vardanian.movieapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
    private ThumbnailDownloader<MovieHolder> downloader;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new MovieItemTask().execute();

        Handler responseHandler = new Handler();
        downloader = new ThumbnailDownloader<>(responseHandler);
        downloader.setThumbnailDownloadListener(new ThumbnailDownloader.ThumbnailDownloadListener<MovieHolder>(){

            @Override
            public void onThumbnailDownloaded(MovieHolder movieHolder, Bitmap bitmap) {
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                movieHolder.bindDrawable(drawable);
            }
        });
        downloader.start();
        downloader.getLooper();
        Log.i(TAG, "Background thread start");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        rvMovie = (RecyclerView) v.findViewById(R.id.rv_movies);
        rvMovie.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        setupAdapter();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        downloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        downloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    private void setupAdapter() {
        if (isAdded()) {
            rvMovie.setAdapter(new MovieAdapter(movieItems));
        }
    }

    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movieImageView;
        private Movie movie = new Movie();

        public MovieHolder(View itemView) {
            super(itemView);
            movieImageView = (ImageView) itemView.findViewById(R.id.fragment_movie_image_view);
            itemView.setOnClickListener(this);
        }

        public void bindDrawable(Drawable drawable) {
            movieImageView.setImageDrawable(drawable);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    movie.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

        private List<Movie> movies;

        public MovieAdapter(List<Movie> movies) {
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
            Movie movie = movies.get(position);

            Drawable placeholder = getResources().getDrawable(R.drawable.sw);
            movieHolder.bindDrawable(placeholder);
            downloader.queueThumbnail(movieHolder, movie.getPosterPath());
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

    private class MovieItemTask extends AsyncTask<Void,Void,List<Movie>> {

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
