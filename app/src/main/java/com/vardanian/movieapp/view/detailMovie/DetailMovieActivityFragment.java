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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivityFragment extends Fragment implements MVPMovies.DetailMovieView{

    @BindView(R.id.detail_poster)
    ImageView ivPoster;
    @BindView(R.id.detail_backdrop)
    ImageView ivBackdrop;
    @BindView(R.id.detail_title)
    TextView tvTitle;
    @BindView(R.id.detail_overview)
    TextView tvOverview;
    @BindView(R.id.detail_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.detail_popularity)
    TextView tvPopularity;
    @BindView(R.id.detail_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    DetailMoviePresenter presenter;

    public DetailMovieActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        ButterKnife.bind(this, v);

        presenter = new DetailMoviePresenter(getContext());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.setView(this);
        presenter.loadData(getActivity().getIntent());
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
        tvReleaseDate.setText(getString(R.string.release_date) + movie.getReleaseDate());
        tvPopularity.setText(getString(R.string.popularity) + movie.getPopularity().substring(0, 6));
        tvVoteAverage.setText(getString(R.string.vote_average) + movie.getVoteAverage());
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
