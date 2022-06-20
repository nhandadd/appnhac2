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
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;

import java.util.ArrayList;

public class BaihatHotAdapter extends  RecyclerView.Adapter<BaihatHotAdapter.ViewHolder>{
    Context context;
    ArrayList<Baihat> baihatArrayList;

    public BaihatHotAdapter(Context context, ArrayList<Baihat> baihatArrayList) {
        this.context = context;
        this.baihatArrayList = baihatArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
       View view = layoutInflater.inflate(R.layout.dong_baihathot, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
             Baihat baihat = baihatArrayList.get(position);
             holder.tvTenBaiHot.setText(baihat.getTenBaihat());
             holder.tvTenCasiHot.setText(baihat.getCasi());
             Glide.with(context).load(baihatArrayList.get(position).getHinhBahat()).into(holder.imgBaihathot);
    }

    @Override
    public int getItemCount() {
        return baihatArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenBaiHot, tvTenCasiHot;
        ImageView imgBaihathot, imgLuotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBaiHot = itemView.findViewById(R.id.tvTenBaiHatHot);
            tvTenCasiHot = itemView.findViewById(R.id.tvTenCasiHot);
            imgBaihathot = itemView.findViewById(R.id.imageBaihatHot);
            imgLuotthich = itemView.findViewById(R.id.imageLuotthich);
        }
    }
}
