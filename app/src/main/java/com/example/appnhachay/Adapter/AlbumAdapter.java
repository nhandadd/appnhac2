package com.example.appnhachay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appnhachay.Model.Album;
import com.example.appnhachay.R;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
    Context context;
    ArrayList<Album> albumArrayList;

    public AlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
             Album album = albumArrayList.get(position);
             holder.tvTenAlbum.setText(album.getTenAlbum());
             holder.tvCasiAlum.setText(album.getTencasiAlbum());
             Glide.with(context).load(album.getHinhanhAlbum()).into(holder.imageAlbum);
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageAlbum;
        TextView tvTenAlbum, tvCasiAlum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAlbum = itemView.findViewById(R.id.imageAlbum);
            tvTenAlbum = itemView.findViewById(R.id.tvTenAlbum);
            tvCasiAlum = itemView.findViewById(R.id.tvTenCasiAlbum);
        }
    }
}
