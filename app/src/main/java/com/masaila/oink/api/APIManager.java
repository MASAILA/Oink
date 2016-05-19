package com.masaila.oink.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MASAILA on 16/5/18.
 */
public class APIManager {
    private static APIList apiList;

    public static APIList getInstance() {
        return apiList;
    }

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.3.102:3000/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiList = retrofit.create(APIList.class);
    }
}
