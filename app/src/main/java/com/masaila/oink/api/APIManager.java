package com.masaila.oink.api;

import com.masaila.oink.model.HttpResult;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MASAILA on 16/5/18.
 */
public class ApiManager {

    private MusicService mMusicService;

    private Retrofit mRetrofit;

    private ApiManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.3.104:3000/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMusicService = mRetrofit.create(MusicService.class);
    }

    private static class SingletonHolder {
        private static final ApiManager INSTANCE = new ApiManager();
    }

    public static ApiManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getAllPlaylist(Subscriber<HttpResult> subscriber, int page) {
        mMusicService.getAllPlaylist(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
