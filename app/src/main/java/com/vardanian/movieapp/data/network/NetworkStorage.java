//package com.vardanian.movieapp.data.network;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.vardanian.movieapp.interfaces.Constants;
//import com.vardanian.movieapp.model.Movie;
//import com.vardanian.movieapp.utils.NetworkUtils;
//
//import org.json.JSONException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//
//import rx.Observable;
//import rx.Subscriber;
//
//public class NetworkStorage {
//
//    private final Context context;
//
//    public NetworkStorage(Context context) {
//        this.context = context;
//    }
//
//    public Observable<List<Movie>> getMovies() {
//        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
//            @Override
//            public void call(Subscriber<? super List<Movie>> subscriber) {
//                if (NetworkUtils.isNetworkAvailable(context)) {
//                    try {
//                        subscriber.onNext(getTopMoviesApi());
//                        subscriber.onCompleted();
//                    } catch (Exception e) {
//                        subscriber.onError(e);
//                    }
//                }
//            }
//        });
//    }
//
//    private List<Movie> getTopMoviesApi() throws JSONException, IOException {
//        String json = MovieFetchr.getUrlString(Constants.URL_FETCH_MOVIES);
//        return MovieFetchr.parseItems(json);
//    }
//}
