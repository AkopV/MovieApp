package com.vardanian.movieapp.presenter;

import com.vardanian.movieapp.data.DataRepository;
import com.vardanian.movieapp.interfaces.MVPMovies;
import com.vardanian.movieapp.model.Movie;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MoviesPresenter implements MVPMovies.MoviesPresenter {

    private MVPMovies.MoviesView view;
    private final MVPMovies.MoviesModel model;

    public MoviesPresenter(DataRepository dataRepository) {
        this.model = dataRepository;
    }

    @Override
    public void setView(MVPMovies.MoviesView view) {
        this.view = view;
    }

    @Override
    public void getMovies() {
        showProgress();
        model.fetchMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Movie>>() {
                    @Override
                    public void onCompleted() {
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        MoviesPresenter.this.onError((Exception) e);
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        MoviesPresenter.this.updateUIWithResults(movies);
                    }
                });
    }

    private void showProgress() {

    }

    private void hideProgress() {

    }

    private void updateUIWithResults(List<Movie> movies) {
        if (MoviesPresenter.this.view != null) {
            MoviesPresenter.this.view.onMoviesReceived(movies);
        }
    }

    private void onError(Exception e) {
        if (MoviesPresenter.this.view != null) {
            MoviesPresenter.this.view.onError(e.getLocalizedMessage());
        }
    }

    @Override
    public void onMovieSelected(int id) {
        throw new UnsupportedOperationException();
    }
}
