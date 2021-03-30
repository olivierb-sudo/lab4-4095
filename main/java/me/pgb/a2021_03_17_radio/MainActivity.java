package me.pgb.a2021_03_17_radio;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final String TAG = "MAIN__";
    private MediaPlayer mediaPlayer;
    private static final String url = "http://stream.whus.org:8000/whusfm";
    private Button onBtn;
    private Button offBtn;
    private boolean bMusicOn;

    private Spinner MusicStationSpinner;
    private ArrayList<String> musicStations = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicStations.add("http://broadcast.miami/proxy/tkdisco?mp=/stream/");
        musicStations.add("http://ccfz.dyndns.org:4427/live");
        musicStations.add("http://waw01-01.ic.smcdn.pl:8000/1240-1.mp3");
        musicStations.add("http://stream.wrtcfm.com/listenhigh.m3u");
        musicStations.add("http://www.antenaweb.pt/globalplayer/miaw01.m3u");
        musicStations.add("http://broadcast.miami/proxy/westend?mp=/stream/;");
        musicStations.add("http://broadcast.miami/proxy/salsoul?mp=/stream/;");
        musicStations.add("http://wnkr.streamguys1.com/live-mp3");

        MusicStationSpinner = findViewById(R.id.musicStationSelection);
        MusicStationSpinner.setOnItemSelectedListener(this);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,musicStations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MusicStationSpinner.setAdapter(adapter);

        mediaPlayer = new MediaPlayer();

        onBtn = findViewById(R.id.onBtn);
        offBtn = findViewById(R.id.offBtn);


        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bMusicOn == false) {
                    mediaPlayer = new MediaPlayer();
                    radioSetup(mediaPlayer, musicStations.get(0));
                    mediaPlayer.prepareAsync();
                    bMusicOn = true;
                }
            }
        });

        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                bMusicOn = false;
            }
        });

    }

    public void radioSetup(MediaPlayer mediaPlayer, String url) {

        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i(TAG, "onPrepared" );
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i(TAG, "onError: " + String.valueOf(what).toString());
                return false;
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(TAG, "onCompletion" );
                mediaPlayer.reset();
            }
        });

        try {
            mediaPlayer.setDataSource(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = null;
    }

    private void setUpMediaPlayer() {
        Handler handler = null;
        HandlerThread handlerThread = new HandlerThread("media player") {
            @Override
            public void onLooperPrepared() {
                Log.i(TAG, "onLooperPrepared");

            }
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.musicStationSelection)
        {
            if (bMusicOn == true) {
                mediaPlayer.release();
                String selectedStation = parent.getItemAtPosition(position).toString();

                mediaPlayer = new MediaPlayer();
                radioSetup(mediaPlayer, selectedStation);
                mediaPlayer.prepareAsync();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}