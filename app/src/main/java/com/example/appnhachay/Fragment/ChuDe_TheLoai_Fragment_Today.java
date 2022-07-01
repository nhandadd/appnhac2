package com.example.appnhachay.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appnhachay.Activity.DanhsachBaihatActivity;
import com.example.appnhachay.Activity.DanhsachChudeActivity;
import com.example.appnhachay.Activity.DanhsachTheloaiTheoChudeActivity;
import com.example.appnhachay.Model.ChuDe;
import com.example.appnhachay.Model.TheLoai;
import com.example.appnhachay.Model.TheLoaiTrongNgay;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuDe_TheLoai_Fragment_Today extends Fragment {
    View view;
    HorizontalScrollView horizontalScrollView;
    TextView textViewXemthem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chu_de__the_loai_, container, false);
        horizontalScrollView = view.findViewById(R.id.horizontalScollView);
        textViewXemthem = view.findViewById(R.id.tvXemthemChude);
        textViewXemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhsachChudeActivity.class);
                startActivity(intent);
            }
        });
        getData();
        return view;
    }
    private void getData() {
        DataService dataService = ApiService.getService();
        Call<TheLoaiTrongNgay> theLoaiTrongNgayCall = dataService.GetChudeTheLoai();
        theLoaiTrongNgayCall.enqueue(new Callback<TheLoaiTrongNgay>() {
            @Override
            public void onResponse(Call<TheLoaiTrongNgay> call, Response<TheLoaiTrongNgay> response) {
                  TheLoaiTrongNgay theLoaiTrongNgay = response.body();
                      final ArrayList<ChuDe> chuDeArrayList = new ArrayList<>();
                      chuDeArrayList.addAll(theLoaiTrongNgay.getChuDe());

                      final ArrayList<TheLoai> theLoaiArrayList = new ArrayList<>();
                      theLoaiArrayList.addAll(theLoaiTrongNgay.getTheLoai());
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(580,250);
                layoutParams.setMargins(20, 20, 20, 30);
                for (int i = 0; i < chuDeArrayList.size(); i++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (chuDeArrayList.get(i).getHinhChuDe() != null){
                        Glide.with(getActivity()).load(chuDeArrayList.get(i).getHinhChuDe()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhsachTheloaiTheoChudeActivity.class);
                            intent.putExtra("chude", chuDeArrayList.get(finalI));
                            startActivity(intent);
                        }
                    });
                }

                for (int j = 0; j < theLoaiArrayList.size(); j++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.MATRIX);
                    if (theLoaiArrayList.get(j).getHinhTheLoai() != null){
                        Glide.with(getActivity()).load(theLoaiArrayList.get(j).getHinhTheLoai()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhsachBaihatActivity.class);
                            intent.putExtra("idtheloai", theLoaiArrayList.get(finalJ));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }
            @Override
            public void onFailure(Call<TheLoaiTrongNgay> call, Throwable t) {

            }
        });
    }
}