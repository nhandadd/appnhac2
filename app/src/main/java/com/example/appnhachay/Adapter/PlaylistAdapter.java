package com.example.appnhachay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.appnhachay.Model.Playlist;
import com.example.appnhachay.R;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
         TextView tenPlaylist;
         ImageView imgBackgoundPlaylist, imgPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.dong_playlist,null);
            viewHolder = new ViewHolder();
            viewHolder.tenPlaylist = convertView.findViewById(R.id.tvTenPlaylist);
            viewHolder.imgBackgoundPlaylist = convertView.findViewById(R.id.imageBackgoundPlaylist);
            viewHolder.imgPlaylist = convertView.findViewById(R.id.imagePlaylist);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Playlist playlist = getItem(position);
        Glide.with(getContext()).load(playlist.getHinhPlaylist()).into(viewHolder.imgBackgoundPlaylist);
        Glide.with(getContext()).load(playlist.getIcon()).into(viewHolder.imgPlaylist);
        viewHolder.tenPlaylist.setText(playlist.getTen());
        return convertView;
    }
}
