package com.masaila.oink;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.masaila.oink.event.PlayEvent;
import com.masaila.oink.model.Song;

import org.greenrobot.eventbus.EventBus;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Song song = new Song();
        song.setPath("http://183.61.67.36/m10.music.126.net/20160513182706/1b1c0723789b84c05b8eb7b34b41c69b/ymusic/71e9/ea80/73f1/3279403c40468e6c3639093583dae201.mp3?wshc_tag=1&wsts_tag=5735a61e&wsid_tag=b725f591&wsiphost=ipdbm");
        PlayEvent playEvent = new PlayEvent();
        playEvent.setSong(song);
        EventBus.getDefault().post(playEvent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
