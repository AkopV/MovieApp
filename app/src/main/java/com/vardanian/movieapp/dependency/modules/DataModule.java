package com.vardanian.movieapp.dependency.modules;

import android.content.Context;

import com.vardanian.movieapp.data.DataRepository;
import com.vardanian.movieapp.interfaces.MVPMovies;
import com.vardanian.movieapp.presenter.DetailMoviePresenter;
import com.vardanian.movieapp.presenter.MoviesPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    DataRepository provideDataRepository(Context context) {
        return new DataRepository(context);
    }

    @Provides
    @Singleton
    MVPMovies.MoviesPresenter provideMoviesPresenter(DataRepository repository) {
        return new MoviesPresenter(repository);
    }

    @Provides
    @Singleton
    MVPMovies.DetailMoviePresenter provideDetailMoviePresenter( DataRepository repository) {
        return new DetailMoviePresenter(repository);
    }
}
