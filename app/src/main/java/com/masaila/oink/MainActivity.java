package com.masaila.oink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.masaila.oink.event.PlayEvent;
import com.masaila.oink.model.Song;
import com.masaila.oink.player.PlayerService;
import com.masaila.oink.ui.activites.BaseActivity;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startService(new Intent(this, PlayerService.class));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Song song = new Song();
                song.setPath("http://p2.music.126.net/JLIjdi6KtOr-8K3UczVz0g==/1391981730866791.mp3");
                PlayEvent playEvent = new PlayEvent();
                playEvent.setSong(song);
                EventBus.getDefault().post(playEvent);
            }
        }, 1000);

    }

}
