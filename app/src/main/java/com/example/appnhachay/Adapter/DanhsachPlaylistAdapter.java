package com.example.appnhachay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appnhachay.Activity.DanhsachBaihatActivity;
import com.example.appnhachay.Model.Playlist;
import com.example.appnhachay.R;

import java.util.ArrayList;

public class DanhsachPlaylistAdapter extends RecyclerView.Adapter<DanhsachPlaylistAdapter.ViewHolder>{
    Context context;
    ArrayList<Playlist> playlistArrayList;

    public DanhsachPlaylistAdapter(Context context, ArrayList<Playlist> playlistArrayList) {
        this.context = context;
        this.playlistArrayList = playlistArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_danhsach_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           Playlist playlist = playlistArrayList.get(position);
        Glide.with(context).load(playlist.getIcon()).into(holder.imgHinhnen);
        holder.tvTenDsPlaylist.setText(playlist.getTen());
    }

    @Override
    public int getItemCount() {
        return playlistArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhnen;
        TextView tvTenDsPlaylist;
         public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhnen = itemView.findViewById(R.id.imageDsPlaylist);
            tvTenDsPlaylist = itemView.findViewById(R.id.tvTenDsPlaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachBaihatActivity.class);
                    intent.putExtra("itemplaylist", playlistArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
