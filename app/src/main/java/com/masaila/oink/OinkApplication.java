package com.masaila.oink;

import android.app.Application;
import android.content.Intent;

import com.masaila.oink.player.PlayerService;

/**
 * Created by MASAILA on 16/5/17.
 */
public class OinkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, PlayerService.class));
    }
}
