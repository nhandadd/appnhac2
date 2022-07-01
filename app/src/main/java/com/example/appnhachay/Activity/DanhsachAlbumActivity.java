package com.example.appnhachay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.appnhachay.Adapter.ListAlbumAdapter;
import com.example.appnhachay.Model.Album;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachAlbumActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvDsAlbum;
    ListAlbumAdapter listAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_album);
        anhxa();
        getData();
    }

    private void getData() {
        DataService dataService = ApiService.getService();
        Call<List<Album>> callListAlbum = dataService.GetListAlbum();
        callListAlbum.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList = (ArrayList<Album>) response.body();
                listAlbumAdapter = new ListAlbumAdapter(DanhsachAlbumActivity.this,albumArrayList);
                rcvDsAlbum.setAdapter(listAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbarDsAlbum);
        rcvDsAlbum = findViewById(R.id.rcvDsAlbum);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả Album");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}