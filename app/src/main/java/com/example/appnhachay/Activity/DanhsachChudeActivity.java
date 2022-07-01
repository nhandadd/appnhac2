package com.example.appnhachay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.appnhachay.Adapter.DanhsachChudeAdapter;
import com.example.appnhachay.Model.ChuDe;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachChudeActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvDsChude;
    DanhsachChudeAdapter danhsachChudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_chude);
        inintView();
        getData();
    }

    private void getData() {
        DataService dataService = ApiService.getService();
        Call<List<ChuDe>> callChude = dataService.GetListChuDe();
        callChude.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> chuDeArrayList = (ArrayList<ChuDe>) response.body();
                danhsachChudeAdapter = new DanhsachChudeAdapter(DanhsachChudeActivity.this, chuDeArrayList);
                rcvDsChude.setAdapter(danhsachChudeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
     }

    private void inintView() {
         toolbar = findViewById(R.id.toolbarDsChude);
         rcvDsChude = findViewById(R.id.rcvDsChude);
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setTitle("Tất cả chủ đề");
         toolbar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
    }
}