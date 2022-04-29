package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;

public class music_play_page extends AppCompatActivity {
    //start declerations
    ImageView btn_next,btn_back,btn_play,btn_add_favorite,btn_loop_song,album_art;
    TextView duration,currant_progress,song_title,song_album;
    SeekBar seekBar_time;
    MediaPlayer mediaPlayer;
    //End declerations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play_page);
        btn_back=findViewById(R.id.btn_back);
        btn_next=findViewById(R.id.btn_next);
        btn_play=findViewById(R.id.btn_play);
        duration=findViewById(R.id.duration);
        btn_loop_song=findViewById(R.id.btn_loop_song);
        album_art=findViewById(R.id.album_art);
        btn_add_favorite=findViewById(R.id.btn_add_favorite);
        song_title=findViewById(R.id.song_title);
        song_album=findViewById(R.id.song_album);
        currant_progress=findViewById(R.id.currant_progress);
        seekBar_time=findViewById(R.id.seekbar_time);
        //handle song data
        song newSong= (song) getIntent().getSerializableExtra("song");
        song_title.setText(newSong.getTitle());
        song_album.setText(newSong.getAlbum());
        try {
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(newSong.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_loop_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isLooping()==false){
                    mediaPlayer.setLooping(true);
                    btn_loop_song.setColorFilter(R.color.primery_start);
                }else{
                    mediaPlayer.setLooping(false);
                    btn_loop_song.setColorFilter(R.color.white);
                    Toast.makeText(music_play_page.this, "hii", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f,0.5f);
        //get full duration of music
        String f_duration=elapsedTime(mediaPlayer.getDuration());
        duration.setText(f_duration);
        //control button play
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btn_play.setImageResource(R.drawable.ic_play);
                }else{
                    mediaPlayer.start();
                    btn_play.setImageResource(R.drawable.ic_stop);
                }
            }
        });

        seekBar_time.setMax(mediaPlayer.getDuration());
        seekBar_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
               while (mediaPlayer != null){
                   if (mediaPlayer.isPlaying()){
                       try { double currantTime=mediaPlayer.getCurrentPosition();
                       String elapsedTime=elapsedTime((int)currantTime);
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               currant_progress.setText(elapsedTime);
                               seekBar_time.setProgress((int)currantTime);
                           }
                       });
                           Thread.sleep(1000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }
            }
        }).start();

    }//End main

    //get elapsed Time
    public  String elapsedTime(int time){
        String elapsedTime="";
        int minutes=time / 1000 /60;
        int secondes=time/ 1000 % 60;
        elapsedTime =minutes +":";
        if (secondes < 10 ){
            elapsedTime+="0";
        }
        elapsedTime+=secondes;
        return elapsedTime;

    }
}