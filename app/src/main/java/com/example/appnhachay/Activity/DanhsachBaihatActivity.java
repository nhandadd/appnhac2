package com.example.appnhachay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.appnhachay.Model.Quangcao;
import com.example.appnhachay.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DanhsachBaihatActivity extends AppCompatActivity {
    Quangcao quangcao;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    RecyclerView rcvDanhSach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_baihat);
        anhxa();
        dataIntent();
    }

    private void anhxa() {
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
                Toast.makeText(this, quangcao.getTenBaiHat(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}