package com.vardanian.movieapp.view.detailMovie;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vardanian.movieapp.R;
import com.vardanian.movieapp.interfaces.MVPMovies;
import com.vardanian.movieapp.model.Movie;
import com.vardanian.movieapp.presenter.DetailMoviePresenter;

public class DetailMovieActivityFragment extends Fragment implements MVPMovies.DetailMovieView{

    private static final String MOVIE_RELEASE_DATE = "Release date:\n";
    private static final String MOVIE_POPULARITY = "Popularity:\n";
    private static final String MOVIE_VOTE_AVERAGE = "Vote average:\n";

    private ImageView ivPoster;
    private ImageView ivBackdrop;
    private TextView tvTitle;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private TextView tvPopularity;
    private TextView tvVoteAverage;
    private Toolbar toolbar;

    DetailMoviePresenter presenter;

    public DetailMovieActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        presenter = new DetailMoviePresenter(getContext());

        initUI(v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.setView(this);
        presenter.loadData(getActivity().getIntent());
    }

    private void initUI(View v) { //butterknife
        ivPoster = (ImageView) v.findViewById(R.id.detail_poster);
        ivBackdrop = (ImageView) v.findViewById(R.id.detail_backdrop);
        tvTitle = (TextView) v.findViewById(R.id.detail_title);
        tvOverview = (TextView) v.findViewById(R.id.detail_overview);
        tvReleaseDate = (TextView) v.findViewById(R.id.detail_release_date);
        tvPopularity = (TextView) v.findViewById(R.id.detail_popularity);
        tvVoteAverage = (TextView) v.findViewById(R.id.detail_vote_average);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
    }

    public void updateUI(Movie movie) {
        Picasso.with(getActivity())
                .load(movie.getPosterPath(Movie.WIDTH_500))
                .placeholder(R.drawable.ic_pictures_512)
                .into(ivPoster);
        Picasso.with(getActivity())
                .load(movie.getBackdropPath(Movie.WIDTH_500))
                .placeholder(R.drawable.ic_pictures_512)
                .into(ivBackdrop);
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(MOVIE_RELEASE_DATE + movie.getReleaseDate());
        tvPopularity.setText(MOVIE_POPULARITY + movie.getPopularity().substring(0, 6));
        tvVoteAverage.setText(MOVIE_VOTE_AVERAGE + movie.getVoteAverage());
        toolbar.setTitle(movie.getTitle());
    }

    @Override
    public void onMovieReceived(Movie movie) {
        updateUI(movie);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
    }
}
