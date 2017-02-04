package com.vardanian.movieapp.view.detailMovie;

import android.media.tv.TvTrackInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

    private Movie movie;
    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private TextView tvPopularity;
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

    private void initUI(View v) { 
        ivPoster = (ImageView) v.findViewById(R.id.detail_poster);
        tvTitle = (TextView) v.findViewById(R.id.detail_title);
        tvOverview = (TextView) v.findViewById(R.id.detail_overview);
        tvReleaseDate = (TextView) v.findViewById(R.id.detail_release_date);
        tvPopularity = (TextView) v.findViewById(R.id.detail_popularity);
    }

    public void updateUI(Movie movie) {
        Picasso.with(getActivity())
                .load(movie.getPosterPath(Movie.WIDTH_500))
                .placeholder(R.drawable.ic_pictures_512)
                .into(ivPoster);
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(R.string.overview + movie.getOverview());
        tvReleaseDate.setText(R.string.release_date + movie.getReleaseDate());
        tvPopularity.setText(R.string.popularity + movie.getPopularity());
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
