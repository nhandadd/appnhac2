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
import com.example.appnhachay.Model.TheLoai;
import com.example.appnhachay.R;

import java.util.ArrayList;

public class TheloaiTheoChudeAdapter extends RecyclerView.Adapter<TheloaiTheoChudeAdapter.ViewHolder>{
    Context context;
    ArrayList<TheLoai> theLoaiArrayList;

    public TheloaiTheoChudeAdapter(Context context, ArrayList<TheLoai> theLoaiArrayList) {
        this.context = context;
        this.theLoaiArrayList = theLoaiArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_theloai_theo_chude,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                TheLoai theLoai = theLoaiArrayList.get(position);
                holder.tvTentheloai.setText(theLoai.getTenTheLoai());
        Glide.with(context).load(theLoai.getHinhTheLoai()).into(holder.imgHinhnen);
    }

    @Override
    public int getItemCount() {
        return theLoaiArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhnen;
        TextView tvTentheloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhnen = itemView.findViewById(R.id.imageTltheoCd);
            tvTentheloai = itemView.findViewById(R.id.tvTenTltheoCd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachBaihatActivity.class);
                    intent.putExtra("idtheloai", theLoaiArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
