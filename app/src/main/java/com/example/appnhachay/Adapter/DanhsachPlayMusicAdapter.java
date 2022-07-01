package com.example.appnhachay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhachay.Activity.DanhsachBaihatActivity;
import com.example.appnhachay.Activity.PlayMusicActivity;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;

import java.util.ArrayList;

public class DanhsachPlayMusicAdapter extends RecyclerView.Adapter<DanhsachPlayMusicAdapter.ViewHolder>{
    Context context;
    ArrayList<Baihat> baihatArrayList;

    public DanhsachPlayMusicAdapter(Context context, ArrayList<Baihat> baihatArrayList) {
        this.context = context;
        this.baihatArrayList = baihatArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_playmusic,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
             Baihat baihat = baihatArrayList.get(position);
             holder.tvTenPlay.setText(baihat.getTenBaihat());;
             holder.tvCasiPlay.setText(baihat.getCasi());
             holder.tvIndexPlay.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return baihatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndexPlay, tvTenPlay, tvCasiPlay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndexPlay = itemView.findViewById(R.id.tvIndexPlay);
            tvTenPlay = itemView.findViewById(R.id.tvTenPlay);
            tvCasiPlay = itemView.findViewById(R.id.tvCasiPlay);
        }
    }
}
