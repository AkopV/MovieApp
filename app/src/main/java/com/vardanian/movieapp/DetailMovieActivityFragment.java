package com.vardanian.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vardanian.movieapp.model.Movie;
import com.vardanian.movieapp.network.MovieFetchr;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieActivityFragment extends Fragment {

    private static final String OVERVIEW = "OVERVIEW: ";
    private static final String RELEASE_DATE = "RELEASE DATE: ";
    private static final String POPULARITY = "POPULARITY: ";


    private Movie movie;
    private ImageView poster;
    private TextView title;
    private TextView overview;
    private TextView releaseDate;
    private TextView popularity;

    public DetailMovieActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        movie = getActivity().getIntent().getParcelableExtra(
                Movie.class.getName());
        getActivity().getIntent().getParcelableExtra(movie.getPosterPath());

        poster = (ImageView) v.findViewById(R.id.detail_poster);
        Picasso.with(getActivity())
                .load(movie.getPosterPath())
                .placeholder(R.drawable.sw)
                .into(poster);

        title = (TextView) v.findViewById(R.id.detail_title);
        title.setText(movie.getTitle());

        overview = (TextView) v.findViewById(R.id.detail_overview);
        overview.setText(OVERVIEW + movie.getOverview());

        releaseDate = (TextView) v.findViewById(R.id.detail_release_date);
        releaseDate.setText(RELEASE_DATE + movie.getReleaseDate());

        popularity = (TextView) v.findViewById(R.id.detail_popularity);
        popularity.setText(POPULARITY + movie.getPopularity());

        return v;
    }
}
