package com.example.appnhachay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.appnhachay.Activity.DanhsachBaihatActivity;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.Model.Quangcao;
import com.example.appnhachay.R;

import java.util.ArrayList;

public class QuangcaoAdapter extends PagerAdapter {
    Context context;
    ArrayList<Quangcao> quangcaoArrayList;

    public QuangcaoAdapter(Context context, ArrayList<Quangcao> quangcaoArrayList) {
        this.context = context;
        this.quangcaoArrayList = quangcaoArrayList;
    }

    @Override
    public int getCount() {
        return quangcaoArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_banner,null);
        ImageView imvBackgroundQuangcao = view.findViewById(R.id.backgroundQuangcao);
        ImageView imvSongQuangcao = view.findViewById(R.id.imageQuangcao);
        TextView tvTitle = view.findViewById(R.id.tvTitleQuangcao);
        TextView tvNoidung = view.findViewById(R.id.tvNoidung);
        Glide.with(context)
                .load(quangcaoArrayList.get(position).getHinhAnh())
                .into(imvBackgroundQuangcao);
        Glide.with(context)
                .load(quangcaoArrayList.get(position).getHinhbaihat())
                .into(imvSongQuangcao);
        tvTitle.setText(quangcaoArrayList.get(position).getTenBaiHat());
        tvNoidung.setText(quangcaoArrayList.get(position).getNoiDung());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DanhsachBaihatActivity.class);
                intent.putExtra("quangcao", quangcaoArrayList.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

