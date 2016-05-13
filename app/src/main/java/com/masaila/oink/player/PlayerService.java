package com.masaila.oink.player;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.masaila.oink.event.PlayEvent;

import org.greenrobot.eventbus.EventBus;

public class PlayerService extends Service {

    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    public void onEvent(PlayEvent playEvent) {
        MusicPlayer.getPlayer().play(playEvent.getSong());
    }
}
