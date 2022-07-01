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
    int position = 0;
    int positionRandom = 0;
    boolean repeat = false;
    boolean nextActivy = false;
    boolean checkRandom = false;
    private MyService myService;
    private boolean isServiceConnected;
    String ten,hinhanh, baihat, casi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initView();
        getDataIntent();
        ten = baihatArrayList.get(0).getTenBaihat();
        casi = baihatArrayList.get(0).getCasi();
        hinhanh = baihatArrayList.get(0).getHinhBahat();
        baihat = baihatArrayList.get(0).getLinkBaihat();
        evenClick();

        Handler handler11 = new Handler();
        handler11.postDelayed(new Runnable() {
            @Override
            public void run() {
                        setStatusIButPlay();
                        UpdateTimeDemo();
                        handler11.postDelayed(this, 1000);
            }
        }, 1000);
    }
    private void evenClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
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
                        fragment_dianhac.PlayNhac(baihatArrayList.get(position).getHinhBahat());
                        TimeSong((int) myService.getDuration());
                        handler.removeCallbacks(this);
                    }else {
                        handler.postDelayed(this, 400);
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
//                if (repeat == false){
//                    if (checkRandom == true){
//                        checkRandom = false;
//                    }
//                    repeat = true;
//                    iButRepeat.setImageResource(R.drawable.iconsyned);
//                    iButRandom.setImageResource(R.drawable.iconsuffle);
//                }else {
//                    repeat = false;
//                    iButRepeat.setImageResource(R.drawable.iconrepeat);
//                }
                if (myService.isRepeat() == false){
                    if (myService.isRandom() == true){
                        myService.setKeyStatusFalse(myService.isRandom());
                    }
                    myService.setKeyStatusTrue(myService.isRepeat());
                    iButRepeat.setImageResource(R.drawable.iconsyned);
                    iButRandom.setImageResource(R.drawable.iconsuffle);
                }else {
                    myService.setKeyStatusFalse(myService.isRepeat());
                    iButRepeat.setImageResource(R.drawable.iconrepeat);
                }
            }
        });
        iButRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myService.isRandom() == false){
                    if (myService.isRepeat() == true){
                       myService.setKeyStatusFalse(myService.isRepeat());
                    }
                    myService.setKeyStatusTrue(myService.isRandom());
                    iButRandom.setImageResource(R.drawable.iconshuffled);
                    iButRepeat.setImageResource(R.drawable.iconrepeat);
                }else {
                    myService.setKeyStatusFalse(myService.isRandom());
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
                myService.serviceHandler.nextMp3(baihatArrayList);

                getSupportActionBar().setTitle(baihatArrayList.get(position).getTenBaihat());
                fragment_dianhac.PlayNhac(baihatArrayList.get(position).getHinhBahat());
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
                myService.serviceHandler.preMp3(baihatArrayList);

                fragment_dianhac.PlayNhac(baihatArrayList.get(position).getHinhBahat());
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
            if (baihatArrayList.size() > 0) {
                Intent intent = new Intent(PlayMusicActivity.this, MyService.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baihatplay", baihatArrayList);
                intent.putExtras(bundle);
                startService(intent);
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                fragment_dianhac.PlayNhac(baihatArrayList.get(0).getHinhBahat());
            }
    }

    private void TimeSong(int duration) {
        Log.d("BBB","duration Timesong " + duration);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTotalTimeSong.setText(simpleDateFormat.format(duration));
        seekBarPlay.setMax(duration);
    }
    private void UpdateTimeDemo(){
        seekBarPlay.setProgress(currentTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTimeSong.setText(simpleDateFormat.format(currentTime));
    }
    private void UpdateTiwme(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    seekBarPlay.setProgress(currentTime);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    tvTimeSong.setText(simpleDateFormat.format(currentTime));
                    handler.postDelayed(this,300);
            }
        },400);

        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (myService.isNext()){
                    if (position < baihatArrayList.size()) {
                        iButPlay.setImageResource(R.drawable.ic_pause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = baihatArrayList.size();
                            }
                            position -= 1;
                        }
                        if (checkRandom == true) {
                            Random random = new Random();
                            positionRandom = position;
                            int index = random.nextInt(baihatArrayList.size());
                            if (index == positionRandom) {
                                position = positionRandom + 1;
                            }
                        }
                        if (position > (baihatArrayList.size() - 1) ){
                            position = 0;
                        }
                        getSupportActionBar().setTitle(baihatArrayList.get(position).getTenBaihat());
                        myService.serviceHandler.startMp3(baihatArrayList);
                        fragment_dianhac.PlayNhac(baihatArrayList.get(position).getHinhBahat());
                    }
                iButPre.setClickable(false); iButNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iButPre.setClickable(true);   iButNext.setClickable(true);
                    }
                },3000);
                handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this, 1000);
                }
            }
        },1000);
    }

    @Override
    protected void onDestroy() {
        StopService();
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
    private void StopService(){
        if (myService.isStopService()){
            myService.stopSelf();
        }
    }
}