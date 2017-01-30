package com.vardanian.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vardanian.movieapp.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<Movie> movies;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
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

    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            Picasso.with(context)
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.ic_pictures_512)
                    .into(ivMovie);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
            intent.putExtra(Movie.class.getName(), movie);
            v.getContext().startActivity(intent);
        }
    }
}
