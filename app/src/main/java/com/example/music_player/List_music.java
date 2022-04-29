package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class List_music extends AppCompatActivity {
    ArrayList <song> s;
    ListView musicList;
    private static final int requstedCode =5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        musicList=findViewById(R.id.music_list_view);
        s=new ArrayList<>();
        //check permsion to get songs from external storge
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},requstedCode);
            return;
        }else {
            //permsion accepted ...load songs
            getSongs();
        }
        //handle click on song on the list
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                Intent newSongPlayer=new Intent(List_music.this,music_play_page.class);
                song selectedSong=s.get(postion);
                newSongPlayer.putExtra("song",selectedSong);
                startActivity(newSongPlayer);
            }
        });
    }
    private void getSongs(){
        ContentResolver cr=getContentResolver();
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor=cr.query(songUri,null,null,null,null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int song_title=songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int song_album=songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int song_data=songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                String title=songCursor.getString(song_title);
                String album=songCursor.getString(song_album);
                String path=songCursor.getString(song_data);
                song newSong=new song(title,album,path);
                s.add(newSong);
            }while (songCursor.moveToNext());
        }
        music_list_adapter newSongAdapter=new music_list_adapter(this,R.layout.music_list_item,s);
        musicList.setAdapter(newSongAdapter);
    }
}