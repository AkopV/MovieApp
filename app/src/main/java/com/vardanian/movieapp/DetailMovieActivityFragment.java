package com.vardanian.movieapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vardanian.movieapp.model.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieActivityFragment extends Fragment {

    private static final String OVERVIEW = "OVERVIEW: ";
    private static final String RELEASE_DATE = "RELEASE DATE: ";
    private static final String POPULARITY = "POPULARITY: ";


    private Movie movie;
    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private TextView tvPopularity;

    public DetailMovieActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        movie = getActivity().getIntent().getParcelableExtra(
                Movie.class.getName());
        getActivity().getIntent().getParcelableExtra(movie.getPosterPath());

        ivPoster = (ImageView) v.findViewById(R.id.detail_poster);
        Picasso.with(getActivity())
                .load(movie.getPosterPath())
                .placeholder(R.drawable.ic_pictures_512)
                .into(ivPoster);

        tvTitle = (TextView) v.findViewById(R.id.detail_title);
        tvTitle.setText(movie.getTitle());

        tvOverview = (TextView) v.findViewById(R.id.detail_overview);
        tvOverview.setText(OVERVIEW + movie.getOverview());

        tvReleaseDate = (TextView) v.findViewById(R.id.detail_release_date);
        tvReleaseDate.setText(RELEASE_DATE + movie.getReleaseDate());

        tvPopularity = (TextView) v.findViewById(R.id.detail_popularity);
        tvPopularity.setText(POPULARITY + movie.getPopularity());

        return v;
    }
}
