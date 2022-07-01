package com.example.appnhachay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.appnhachay.Adapter.DanhsachPlaylistAdapter;
import com.example.appnhachay.Model.Playlist;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachPlaylistActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvDsPlaylist;
    DanhsachPlaylistAdapter danhsachPlaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_playlist);
        anhxa();
        initView();
        getData();
    }

    private void getData() {
        DataService dataService = ApiService.getService();
        Call<List<Playlist>> callListPlaylist = dataService.GetListPlayList();
        callListPlaylist.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> playlistArrayList = (ArrayList<Playlist>) response.body();
                danhsachPlaylistAdapter = new DanhsachPlaylistAdapter(DanhsachPlaylistActivity.this, playlistArrayList);
                rcvDsPlaylist.setAdapter(danhsachPlaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void initView() {
           setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           getSupportActionBar().setTitle("Play lists");
           toolbar.setNavigationOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   finish();
               }
           });
    }
    private void anhxa() {
          toolbar = findViewById(R.id.toolbarDsPlaylist);
          rcvDsPlaylist = findViewById(R.id.rcvDsPlaylist);
    }
}