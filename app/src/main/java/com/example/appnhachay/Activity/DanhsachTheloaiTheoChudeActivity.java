package com.example.appnhachay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appnhachay.Adapter.TheloaiTheoChudeAdapter;
import com.example.appnhachay.Model.ChuDe;
import com.example.appnhachay.Model.TheLoai;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachTheloaiTheoChudeActivity extends AppCompatActivity {
    ChuDe chuDe;
    RecyclerView rcvDsTltheoCd;
    Toolbar toolbar;
    TheloaiTheoChudeAdapter theloaiTheoChudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_theloai_theo_chude);
        GetIntent();
        initView();
        getData(chuDe.getIdChuDe());
    }

    private void getData(String idchude) {
        DataService dataService = ApiService.getService();
        Call<List<TheLoai>> callTheloai = dataService.GetTheLoaiTheoChuDe(idchude);
        callTheloai.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> theLoaiArrayList = (ArrayList<TheLoai>) response.body();
                theloaiTheoChudeAdapter = new TheloaiTheoChudeAdapter(DanhsachTheloaiTheoChudeActivity.this, theLoaiArrayList);
                rcvDsTltheoCd.setAdapter(theloaiTheoChudeAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
     }

    private void initView() {
        rcvDsTltheoCd = findViewById(R.id.rcvDsTltheoCd);
        toolbar = findViewById(R.id.toolbarDsTltheoCd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("chude")){
            chuDe = (ChuDe) intent.getSerializableExtra("chude");
        }
    }
}