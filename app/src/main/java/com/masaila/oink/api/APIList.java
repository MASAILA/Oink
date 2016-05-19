package com.masaila.oink.api;

import com.masaila.oink.model.AllPlaylist;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by MASAILA on 16/5/18.
 */
public interface APIList {

    @GET("getAllPlaylist")
    Observable<AllPlaylist> getAllPlaylist();

}
