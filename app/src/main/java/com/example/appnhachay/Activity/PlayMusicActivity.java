package com.example.appnhachay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.appnhachay.Adapter.PagerPlayNhacAdapter;
import com.example.appnhachay.Fragment.DanhsachPlayMusicFragment;
import com.example.appnhachay.Fragment.DianhacFragment;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.MyService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayMusicActivity extends AppCompatActivity {
    int currentTime = 0;
    Toolbar toolbar;
    TextView tvTimeSong, tvTotalTimeSong;
    ImageButton iButPlay, iButPre, iButNext, iButRandom, iButRepeat;
    SeekBar seekBarPlay;
    ViewPager viewPagerPlayMusic;
    public static ArrayList<Baihat> baihatArrayList = new ArrayList<>();
    DianhacFragment fragment_dianhac;
    DanhsachPlayMusicFragment fragment_danhsachplay;
    public static PagerPlayNhacAdapter pagerPlayNhacAdapter;
    LinearLayout layoutLoading;
    private MyService myService;
    boolean isServiceConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initView();
        getDataIntent();
        evenClick();
        Handler handlerUpdate = new Handler();
        handlerUpdate.postDelayed(new Runnable() {
            @Override
            public void run() {
                TimeSong((int) myService.getDuration());
                UpdateTimeDemo(currentTime);
                setStatusIButPlay();

                getSupportActionBar().setTitle(baihatArrayList.get(myService.getmPosition()).getTenBaihat());
//                fragment_dianhac.PlayNhac(baihatArrayList.get(myService.getmPosition()).getHinhBahat());
                handlerUpdate.postDelayed(this, 1000);
            }
        }, 1000);
    }
    private void evenClick() {
        final Handler handlerStartService = new Handler();
        handlerStartService.postDelayed(new Runnable() {
            @Override
            public void run() {
                    if (baihatArrayList.size() > 0){
                        // start service
                        Intent intentStart = new Intent(PlayMusicActivity.this, MyService.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("baihatplay", baihatArrayList);
                        intentStart.putExtras(bundle);
                        startService(intentStart);
                        bindService(intentStart, connection, Context.BIND_AUTO_CREATE);

                        getSupportActionBar().setTitle(baihatArrayList.get(0).getTenBaihat());
                        fragment_dianhac.PlayNhac(baihatArrayList.get(0).getHinhBahat());
                        handlerStartService.removeCallbacks(this);
                    }else {
                        handlerStartService.postDelayed(this, 400);
                    }

            }
        }, 500);
        iButPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (myService.isPlaying()){
                     myService.serviceHandler.pauseMp3(baihatArrayList);
                 }else {
                     myService.serviceHandler.resumeMp3(baihatArrayList);
                 }
                 setStatusIButPlay();
            }
        });
        iButRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myService.isRepeat() == false){
                    if (myService.isRandom() == true){
                        myService.serviceHandler.setFalseRandom();
                    }
                    myService.serviceHandler.setTrueRepeat();
                    iButRepeat.setImageResource(R.drawable.iconsyned);
                    iButRandom.setImageResource(R.drawable.iconsuffle);
                }else {
                    myService.serviceHandler.setFalseRepeat();
                    iButRepeat.setImageResource(R.drawable.iconrepeat);
                }
            }
        });
        iButRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myService.isRandom() == false){
                    if (myService.isRepeat() == true){
                       myService.serviceHandler.setFalseRepeat();
                    }
                    myService.serviceHandler.setTrueRandom();
                    iButRandom.setImageResource(R.drawable.iconshuffled);
                    iButRepeat.setImageResource(R.drawable.iconrepeat);
                }else {
                    myService.serviceHandler.setFalseRandom();
                    iButRandom.setImageResource(R.drawable.iconsuffle);
                }
            }
        });
        seekBarPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myService.getMediaPlayer().seekTo(seekBar.getProgress());
            }
        });
        iButNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myService.getMediaPlayer() != null){
                    myService.serviceHandler.nextMp3(baihatArrayList);
                }
                getSupportActionBar().setTitle(baihatArrayList.get(myService.getmPosition()).getTenBaihat());
                fragment_dianhac.PlayNhac(baihatArrayList.get(myService.getmPosition()).getHinhBahat());
                iButPre.setClickable(false);
                iButNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iButPre.setClickable(true);
                        iButNext.setClickable(true);
                    }
                },3000);
            }
        });
        iButPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myService.getMediaPlayer() != null){
                    myService.serviceHandler.preMp3(baihatArrayList);
                }
                getSupportActionBar().setTitle(baihatArrayList.get(myService.getmPosition()).getTenBaihat());
                fragment_dianhac.PlayNhac(baihatArrayList.get(myService.getmPosition()).getHinhBahat());
                iButPre.setClickable(false);
                iButNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iButPre.setClickable(true);
                        iButNext.setClickable(true);
                    }
                },3000);
            }
        });
    }

    private void getDataIntent() {
        baihatArrayList.clear();
        Intent intent = getIntent();
        if (intent != null){
            baihatArrayList.clear();
            if (intent.hasExtra("cakhuc")){
                Baihat baihat = intent.getParcelableExtra("cakhuc");
                baihatArrayList.add(baihat);
            }
            if (intent.hasExtra("cacbaihat")){
                baihatArrayList.clear();
                ArrayList<Baihat> listBaihat = intent.getParcelableArrayListExtra("cacbaihat");
                baihatArrayList = listBaihat;
                }
            }
    }
    private void initView() {
            layoutLoading = findViewById(R.id.layout_loading);
            toolbar = findViewById(R.id.toolbarPlayMucic);
            tvTimeSong = findViewById(R.id.tvTimeSong);
            tvTotalTimeSong = findViewById(R.id.tvTotalTimeSong);
            seekBarPlay = findViewById(R.id.seeBarSong);
            iButPlay = findViewById(R.id.imageButtonPlay);
            iButPre = findViewById(R.id.imageButtonPreview);
            iButNext = findViewById(R.id.imageButtonNext);
            iButRandom = findViewById(R.id.imageButtonSuffle);
            iButRepeat = findViewById(R.id.imageButtonRepeat);
            viewPagerPlayMusic = findViewById(R.id.viewPagerPlayMusic);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            toolbar.setTitleTextColor(Color.WHITE);
            fragment_dianhac = new DianhacFragment();
            fragment_danhsachplay = new DanhsachPlayMusicFragment();
            pagerPlayNhacAdapter = new PagerPlayNhacAdapter(getSupportFragmentManager());
            pagerPlayNhacAdapter.AddFragmet(fragment_danhsachplay);
            pagerPlayNhacAdapter.AddFragmet(fragment_dianhac);
            viewPagerPlayMusic.setAdapter(pagerPlayNhacAdapter);
    }

    private void TimeSong(int duration) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTotalTimeSong.setText(simpleDateFormat.format(duration));
        seekBarPlay.setMax(duration);
    }
    private void UpdateTimeDemo(int timePlay){
        seekBarPlay.setProgress(timePlay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTimeSong.setText(simpleDateFormat.format(timePlay));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MyService.MyBound myBoundService = (MyService.MyBound) service;
            myService = myBoundService.getService();
            isServiceConnected = true;
            myService.setOnListenDuration(new MyService.OnListenDuration() {
                @Override
                public void onCurrentDuration(long time) {
                    currentTime = (int) time;
                }
            });
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            myService = null;
            isServiceConnected = false;
        }
    };
    private void setStatusIButPlay(){
        if (myService.isPlaying()){
            iButPlay.setImageResource(R.drawable.ic_pause);
        } else {
            iButPlay.setImageResource(R.drawable.iconplay);
        }
    }

}