package com.masaila.oink.player;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by MASAILA on 16/5/13.
 * 继承 MediaPlayer 拓展了状态功能
 */
public class ManagedMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener {

    public enum Status {
        IDLE, INITIALIZED, PREPARING, PREPARED, STARTED, PAUSED, STOPPED, COMPLETED
    }

    private Status state;

    private OnCompletionListener onCompletionListener;

    public ManagedMediaPlayer() {
        super();
        state = Status.IDLE;
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (state == Status.IDLE) {
            super.setDataSource(path);
            state = Status.INITIALIZED;
        }
    }

    @Override
    public void prepareAsync() {
        if (state == Status.INITIALIZED || state == Status.STOPPED) {
            super.prepareAsync();
            state = Status.PREPARING;
        }
    }

    @Override
    public void prepare() throws IOException {
        if (state == Status.INITIALIZED) {
            super.prepare();
            state = Status.PREPARING;
        }
    }

    @Override
    public void start() {
        if (state == Status.PREPARED || state == Status.STARTED || state == Status.PAUSED
                || state == Status.COMPLETED) {
            super.start();
            state = Status.STARTED;
        }
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        this.onCompletionListener = listener;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        state = Status.COMPLETED;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion(mp);
        }
    }

    @Override
    public void stop() throws IllegalStateException {
        if (state == Status.STARTED || state == Status.PAUSED || state == Status.COMPLETED) {
            super.stop();
            state = Status.STOPPED;
        }
    }

    @Override
    public void pause() throws IllegalStateException {
        if (state == Status.STARTED || state == Status.PAUSED) {
            super.pause();
            state = Status.PAUSED;
        }
    }

    public Status getState() {
        return state;
    }

    public boolean isComplete() {
        return state == Status.COMPLETED;
    }

    public boolean isPrepared() {
        return state == Status.PREPARED;
    }
}
