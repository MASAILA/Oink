package com.masaila.oink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.masaila.oink.event.PlayEvent;
import com.masaila.oink.model.Song;
import com.masaila.oink.player.PlayerService;
import com.masaila.oink.ui.activites.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
                PlayEvent playEvent = new PlayEvent();
                List<Song> queue = new ArrayList<>();
                queue.add(getSong("http://m2.music.126.net/O2V3vb8xsXpm2kp1MoF0gg==/1398578800754897.mp3"));
                queue.add(getSong("http://p2.music.126.net/JLIjdi6KtOr-8K3UczVz0g==/1391981730866791.mp3"));
                playEvent.setAction(PlayEvent.Action.PLAY);
                playEvent.setQueue(queue);
                EventBus.getDefault().post(playEvent);
            }
        }, 1000);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PlayEvent playEvent = new PlayEvent();
                playEvent.setAction(PlayEvent.Action.NEXT);
                EventBus.getDefault().post(playEvent);
            }
        }, 5000);

    }

    private Song getSong(String url) {
        Song song = new Song();
        song.setPath(url);
        return song;
    }

}
