package com.vardanian.movieapp.data.network;

import com.vardanian.movieapp.model.Page;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitRestApi {

    @GET("3/discover/movie")
    Observable<Page> getMovies(
            @Query("sort_by") String sortBy,
            @Query("api_key") String apiKey
    );
}
