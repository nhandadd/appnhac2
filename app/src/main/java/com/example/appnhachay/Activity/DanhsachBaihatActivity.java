package com.example.appnhachay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.appnhachay.Adapter.DanhsachBaihatAdapter;
import com.example.appnhachay.Model.Album;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.Model.Playlist;
import com.example.appnhachay.Model.Quangcao;
import com.example.appnhachay.Model.TheLoai;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachBaihatActivity extends AppCompatActivity {
    Quangcao quangcao;
    Playlist playlist;
    TheLoai theLoai;
    Album album;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ExtendedFloatingActionButton floatingActionButton;
    RecyclerView rcvDanhSach;
    ImageView imgDanhsachBaihat, imgBackgroundDs;
    ArrayList<Baihat> baihatArrayList;
    DanhsachBaihatAdapter danhsachBaihatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_baihat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        anhxa();
        dataIntent();
        initView();
        if (quangcao != null && !quangcao.getTenBaiHat().equals("")){
            setValueInview(quangcao.getTenBaiHat(), quangcao.getHinhbaihat());
            getDataQuangcao(quangcao.getIdQuangCao());
        }
        if (playlist != null && !playlist.getTen().equals("")){
            setValueInview(playlist.getTen(), playlist.getHinhPlaylist());
            getDataPlaylist(playlist.getIdPlayList());
        }
        if (theLoai != null && !theLoai.getHinhTheLoai().equals("")){
            setValueInview(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            getDataTheloai(theLoai.getIdTheLoai());
        }
        if (album != null && !album.getIdAlbum().equals("")){
            setValueInview(album.getTenAlbum(),album.getHinhanhAlbum());
            getDataAlbum(album.getIdAlbum());
        }
    }

    private void getDataAlbum(String idalbum) {
          DataService dataService = ApiService.getService();
          Call<List<Baihat>> callAlbum = dataService.GetBaiHatAlbum(idalbum);
          callAlbum.enqueue(new Callback<List<Baihat>>() {
              @Override
              public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                  baihatArrayList = (ArrayList<Baihat>) response.body();
                  Log.d("BBB","dataalbum "+baihatArrayList.get(1).getLinkBaihat());
                  danhsachBaihatAdapter = new DanhsachBaihatAdapter(DanhsachBaihatActivity.this, baihatArrayList);
                  rcvDanhSach.setAdapter(danhsachBaihatAdapter);
                  evenClick();
              }
              @Override
              public void onFailure(Call<List<Baihat>> call, Throwable t) {

              }
          });

    }

    private void getDataTheloai(String idtheloai) {
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> callPlaylist = dataService.GetBaiHatTheLoai(idtheloai);
        callPlaylist.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                baihatArrayList = (ArrayList<Baihat>) response.body();
                danhsachBaihatAdapter = new DanhsachBaihatAdapter(DanhsachBaihatActivity.this, baihatArrayList);
                rcvDanhSach.setAdapter(danhsachBaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });

    }

    private void getDataPlaylist(String idplaylist) {
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> callPlaylist = dataService.GetBaiHatPlayList(idplaylist);
        callPlaylist.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                baihatArrayList = (ArrayList<Baihat>) response.body();
                danhsachBaihatAdapter = new DanhsachBaihatAdapter(DanhsachBaihatActivity.this, baihatArrayList);
                rcvDanhSach.setAdapter(danhsachBaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });

    }

    private void getDataQuangcao(String idquangcao) {
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> callBaihatQuangcao = dataService.GetBaiHatQuangCao(idquangcao);
        callBaihatQuangcao.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                baihatArrayList = (ArrayList<Baihat>) response.body();
                 danhsachBaihatAdapter = new DanhsachBaihatAdapter(DanhsachBaihatActivity.this, baihatArrayList);
                 rcvDanhSach.setAdapter(danhsachBaihatAdapter);
                 evenClick();
            }
            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {
            }
        });

    }
    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }
    private void setValueInview(String tenBaihat, String hinhBaiHat) {
        collapsingToolbarLayout.setTitle(tenBaihat);
        try {
            URL url = new URL(hinhBaiHat);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1){
                         collapsingToolbarLayout.setBackground(bitmapDrawable);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Glide.with(DanhsachBaihatActivity.this).load(hinhBaiHat)
                .into(imgDanhsachBaihat);
        Glide.with(DanhsachBaihatActivity.this).load(hinhBaiHat)
                .into(imgBackgroundDs);
    }
    private void anhxa() {
        imgBackgroundDs = findViewById(R.id.imageBackgoundDs);
        imgDanhsachBaihat = findViewById(R.id.imageDanhsach);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingLayout);
        toolbar = findViewById(R.id.toolbarDanhsach);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        rcvDanhSach = findViewById(R.id.rcvDanhsachBaihat);
    }

    private void dataIntent() {
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("quangcao")){
                quangcao = (Quangcao) intent.getSerializableExtra("quangcao");
            }
            if (intent.hasExtra("itemplaylist")){
                playlist = (Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("idtheloai")){
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if (intent.hasExtra("idalbum")){
                album = (Album) intent.getSerializableExtra("idalbum");
            }
        }
    }
    private void evenClick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhsachBaihatActivity.this, PlayMusicActivity.class);
                intent.putExtra("cacbaihat",baihatArrayList);
                startActivity(intent);
            }
        });
    }
}