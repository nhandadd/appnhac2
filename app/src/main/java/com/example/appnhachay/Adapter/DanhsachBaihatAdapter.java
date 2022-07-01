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

import com.example.appnhachay.Activity.PlayMusicActivity;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachBaihatAdapter extends RecyclerView.Adapter<DanhsachBaihatAdapter.ViewHolder>{
    Context context;
    ArrayList<Baihat> baihatArrayList;

    public DanhsachBaihatAdapter(Context context, ArrayList<Baihat> baihatArrayList) {
        this.context = context;
        this.baihatArrayList = baihatArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_danhsach_baihat, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                Baihat baihat = baihatArrayList.get(position);
                holder.tvIndexDs.setText(position + 1 + "");
                holder.tvTenBaihatDs.setText(baihat.getTenBaihat());
                holder.tvTenCasiDs.setText(baihat.getCasi());
    }

    @Override
    public int getItemCount() {
        return baihatArrayList != null && baihatArrayList.size() > 0 ? baihatArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndexDs, tvTenBaihatDs, tvTenCasiDs;
        ImageView imgLuotthichDs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           tvIndexDs = itemView.findViewById(R.id.tvIndexDs);
           tvTenBaihatDs = itemView.findViewById(R.id.tvTenBaihatDs);
           tvTenCasiDs = itemView.findViewById(R.id.tvTenCasiDs);
           imgLuotthichDs = itemView.findViewById(R.id.imageLuotthichDs);
            imgLuotthichDs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgLuotthichDs.setImageResource(R.drawable.iconloved);
                    DataService dataService = ApiService.getService();
                    Call<String> call = dataService.UpdateLuotThich("1",baihatArrayList.get(getPosition()).getIdBaihat());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if (ketqua.equals("Success")){
                                Toast.makeText(context, "bạn đã thích bài hát này!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imgLuotthichDs.setEnabled(false);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("cakhuc", baihatArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
