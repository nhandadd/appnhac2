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
import com.example.appnhachay.Model.Album;
import com.example.appnhachay.R;

import java.util.ArrayList;

public class ListAlbumAdapter extends RecyclerView.Adapter<ListAlbumAdapter.ViewHolder>{
    Context context;
    ArrayList<Album> albumArrayList;

    public ListAlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_list_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Album album = albumArrayList.get(position);
            holder.tvTenDsAlbum.setText(album.getTenAlbum());
        Glide.with(context).load(album.getHinhanhAlbum()).into(holder.imgDsAlbum);
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDsAlbum;
        TextView tvTenDsAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDsAlbum = itemView.findViewById(R.id.imageDsAlbum);
            tvTenDsAlbum = itemView.findViewById(R.id.tvTenDsAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachBaihatActivity.class);
                    intent.putExtra("idalbum", albumArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
