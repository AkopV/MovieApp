package com.vardanian.movieapp.dependency.component;

import com.vardanian.movieapp.dependency.modules.ApplicationModule;
import com.vardanian.movieapp.view.detailMovie.DetailMovieActivity;
import com.vardanian.movieapp.view.movies.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MainActivity activity);

    void inject(DetailMovieActivity detailMovieActivity);
}
