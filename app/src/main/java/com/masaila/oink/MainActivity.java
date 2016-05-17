package com.masaila.oink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.masaila.oink.event.PlayEvent;
import com.masaila.oink.model.Song;
import com.masaila.oink.player.PlayerService;
import com.masaila.oink.ui.activites.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.button_play)
    Button buttonPlay;
    @BindView(R.id.button_next)
    Button buttonNext;
    @BindView(R.id.button_seek)
    Button buttonSeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        startService(new Intent(this, PlayerService.class));

        PlayEvent playEvent = new PlayEvent();
        List<Song> queue = new ArrayList<>();
        queue.add(getSong("http://m2.music.126.net/AuxCK2R5aJlTETgi8kwN3g==/5923069139252948.mp3"));
        queue.add(getSong("http://m2.music.126.net/AuxCK2R5aJlTETgi8kwN3g==/5923069139252948.mp3"));
        playEvent.setAction(PlayEvent.Action.PLAY);
        playEvent.setQueue(queue);
        EventBus.getDefault().post(playEvent);

    }

    @OnClick({R.id.button_play, R.id.button_next, R.id.button_seek})
    public void onClick(View view) {
        PlayEvent playEvent;
        switch (view.getId()) {
            case R.id.button_play:
                playEvent = new PlayEvent();
                List<Song> queue = new ArrayList<>();
                queue.add(getSong("http://m2.music.126.net/AuxCK2R5aJlTETgi8kwN3g==/5923069139252948.mp3"));
                queue.add(getSong("http://m2.music.126.net/AuxCK2R5aJlTETgi8kwN3g==/5923069139252948.mp3"));
                playEvent.setAction(PlayEvent.Action.PLAY);
                playEvent.setQueue(queue);
                EventBus.getDefault().post(playEvent);
                break;
            case R.id.button_next:
                playEvent = new PlayEvent();
                playEvent.setAction(PlayEvent.Action.NEXT);
                EventBus.getDefault().post(playEvent);
                break;
            case R.id.button_seek:
                playEvent=new PlayEvent();
                playEvent.setAction(PlayEvent.Action.SEEK);
                break;
        }
    }


    private Song getSong(String url) {
        Song song = new Song();
        song.setPath(url);
        return song;
    }

}
