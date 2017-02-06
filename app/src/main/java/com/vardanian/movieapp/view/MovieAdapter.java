package com.vardanian.movieapp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vardanian.movieapp.R;
import com.vardanian.movieapp.model.Movie;
import com.vardanian.movieapp.view.detailMovie.DetailMovieActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private static final String TAG = "MovieAdapter";
    private List<Movie> movies;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieAdapter.MovieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
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

        @BindView(R.id.fragment_movie_image_view)
        ImageView ivMovie;
        private Movie movie;
        private Context context;

        public MovieHolder(View itemView) {
            super(itemView);
            movie = new Movie();
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindMovieItem(final Movie movie) {
            this.movie = movie;

            if (!TextUtils.isEmpty(movie.getPosterPath(Movie.WIDTH_500)))
                Picasso.with(context)
                        .load(movie.getPosterPath(Movie.WIDTH_500))
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
