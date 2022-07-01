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

import com.bumptech.glide.Glide;
import com.example.appnhachay.Activity.PlayMusicActivity;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBaihatAdapter extends RecyclerView.Adapter<SearchBaihatAdapter.ViewHolder>{
     Context context;
     ArrayList<Baihat> baihatArrayList;

    public SearchBaihatAdapter(Context context, ArrayList<Baihat> baihatArrayList) {
        this.context = context;
        this.baihatArrayList = baihatArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
              Baihat baihat = baihatArrayList.get(position);
              holder.tvBaihatTk.setText(baihat.getTenBaihat());
              holder.tvCasiTk.setText(baihat.getCasi());
          Glide.with(context).load(baihat.getHinhBahat()).into(holder.imgBaihatTk);
    }

    @Override
    public int getItemCount() {
        return baihatArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBaihatTk,tvCasiTk;
        ImageView imgBaihatTk, imgLuotthichTk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBaihatTk = itemView.findViewById(R.id.tvTenBaihatTk);
            tvCasiTk = itemView.findViewById(R.id.tvTenCasiTk);
            imgBaihatTk = itemView.findViewById(R.id.imageBaihatTk);
            imgLuotthichTk = itemView.findViewById(R.id.imageLuotthichTk);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Intent intent = new Intent(context, PlayMusicActivity.class);
                     intent.putExtra("cakhuc", baihatArrayList.get(getPosition()));
                     context.startActivity(intent);
                }
            });
            imgLuotthichTk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgLuotthichTk.setImageResource(R.drawable.iconloved);
                    DataService dataService = ApiService.getService();
                    Call<String> callLuotthich = dataService.UpdateLuotThich("1",baihatArrayList.get(getPosition()).getIdBaihat());
                    callLuotthich.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                             String ketqua = response.body();
                             if (ketqua.equals("success")){
                                 Toast.makeText(context, "Bạn đã thích bài hát này",Toast.LENGTH_SHORT).show();
                             }else {
                                 Toast.makeText(context,"Lỗi",Toast.LENGTH_SHORT).show();
                             }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            });
            imgLuotthichTk.setEnabled(false);
        }
    }
}
