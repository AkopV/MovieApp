package com.vardanian.movieapp.dependency.component;

import com.vardanian.movieapp.dependency.modules.ApplicationModule;
import com.vardanian.movieapp.dependency.modules.DataModule;
import com.vardanian.movieapp.view.detailMovie.DetailMovieActivity;
import com.vardanian.movieapp.view.detailMovie.DetailMovieActivityFragment;
import com.vardanian.movieapp.view.movies.MainActivity;
import com.vardanian.movieapp.view.movies.MainActivityFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    void inject(MainActivityFragment mainActivityFragment);

    void inject(DetailMovieActivityFragment detailMovieActivityFragment);
}
