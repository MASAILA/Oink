package com.masaila.oink.api;

import com.masaila.oink.model.HttpResult;
import com.masaila.oink.model.Playlist;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by MASAILA on 16/5/18.
 */
public interface MusicService {

    @GET("getAllPlaylist/{page}")
    Observable<HttpResult<List<Playlist>>> getAllPlaylist(
            @Path("page") int page
    );

}