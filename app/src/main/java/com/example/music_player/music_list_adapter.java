package com.example.music_player;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import org.w3c.dom.Text;

import java.util.List;

public class music_list_adapter extends ArrayAdapter<song> {
    public music_list_adapter(@NonNull Context context, int resource, @NonNull List<song> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item, null);

        TextView title=convertView.findViewById(R.id.song_title);
        TextView album=convertView.findViewById(R.id.song_album);
        song s=getItem(position);
        title.setText(s.getTitle());
        album.setText(s.getAlbum());
        return convertView;
    }
}
