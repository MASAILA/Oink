package com.masaila.oink.event;

import com.masaila.oink.model.Song;

/**
 * Created by MASAILA on 16/5/13.
 */
public class PlayEvent {

    private Song mSong;

    public Song getSong() {
        return mSong;
    }

    public void setSong(Song song) {
        mSong = song;
    }
}
