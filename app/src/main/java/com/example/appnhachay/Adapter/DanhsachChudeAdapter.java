package com.example.appnhachay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appnhachay.Activity.DanhsachTheloaiTheoChudeActivity;
import com.example.appnhachay.Model.ChuDe;
import com.example.appnhachay.R;

import java.util.ArrayList;

public class DanhsachChudeAdapter extends RecyclerView.Adapter<DanhsachChudeAdapter.ViewHolder>{
    Context context;
    ArrayList<ChuDe> chuDeArrayList;

    public DanhsachChudeAdapter(Context context, ArrayList<ChuDe> chuDeArrayList) {
        this.context = context;
        this.chuDeArrayList = chuDeArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_danhsach_chude,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                ChuDe chuDe = chuDeArrayList.get(position);
        Glide.with(context).load(chuDe.getHinhChuDe()).into(holder.imgDsChude);
    }

    @Override
    public int getItemCount() {
        return chuDeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDsChude;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDsChude = itemView.findViewById(R.id.imageDsChude);
            imgDsChude.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachTheloaiTheoChudeActivity.class);
                    intent.putExtra("chude", chuDeArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
