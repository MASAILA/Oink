package com.masaila.oink.player;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by MASAILA on 16/5/13.
 * 继承 MediaPlayer 拓展了状态功能
 */
public class ManagedMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener{



    public enum Status {
        IDLE, INITIALIZED, PREPARING, PREPARED, STARTED, PAUSED, STOPPED, COMPLETED
    }

    private Status mState;

    private OnCompletionListener mOnCompletionListener;
    private OnPreparedListener mOnPreparedListener;

    public ManagedMediaPlayer() {
        super();
        mState = Status.IDLE;
        super.setOnCompletionListener(this);
        super.setOnPreparedListener(this);
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (mState == Status.IDLE) {
            super.setDataSource(path);
            mState = Status.INITIALIZED;
        }
    }

    @Override
    public void prepareAsync() {
        if (mState == Status.INITIALIZED || mState == Status.STOPPED) {
            super.prepareAsync();
            mState = Status.PREPARING;
        }
    }

    @Override
    public void prepare() throws IOException {
        if (mState == Status.INITIALIZED) {
            super.prepare();
            mState = Status.PREPARING;
        }
    }

    @Override
    public void setOnPreparedListener(OnPreparedListener listener) {
        this.mOnPreparedListener = listener;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mState = Status.PREPARED;
        if (mOnPreparedListener != null) {
            mOnPreparedListener.onPrepared(mp);
        }
    }

    @Override
    public void start() {
        if (mState == Status.PREPARED || mState == Status.STARTED || mState == Status.PAUSED
                || mState == Status.COMPLETED) {
            super.start();
            mState = Status.STARTED;
        }
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mState = Status.COMPLETED;
        if (mOnCompletionListener != null) {
            mOnCompletionListener.onCompletion(mp);
        }
    }

    @Override
    public void stop() throws IllegalStateException {
        if (mState == Status.STARTED || mState == Status.PAUSED || mState == Status.COMPLETED) {
            super.stop();
            mState = Status.STOPPED;
        }
    }

    @Override
    public void pause() throws IllegalStateException {
        if (mState == Status.STARTED || mState == Status.PAUSED) {
            super.pause();
            mState = Status.PAUSED;
        }
    }

    public Status getState() {
        return mState;
    }

    public boolean isComplete() {
        return mState == Status.COMPLETED;
    }

    public boolean isPrepared() {
        return mState == Status.PREPARED;
    }
}
