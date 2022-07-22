package com.example.appnhachay.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.appnhachay.Activity.PlayMusicActivity;
import com.example.appnhachay.Fragment.DianhacFragment;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;

import java.util.ArrayList;
import java.util.Random;

public class MyService extends Service {
       private ArrayList<Baihat>  baihats = null;
      private Looper serviceLooper;
      public ServiceHandler serviceHandler;
      private NotificationManager notificationManager;
      private boolean isPlaying = false;
      private int RESUME_MUSIC_CODE = 0;
      private int PAUSE_MUSIC_CODE = 1;
      private int CLEAR_MUSIC_CODE = 2;
      private int PRE_MUSIC_CODE = 3;
      private int NEXT_MUSIC_CODE = 4;

      private boolean stopService = false;
      private boolean repeat = false;
      private boolean random = false;
      private int mPosition = 0;
      int positionRandom = 0;
      private OnListenDuration onListenDuration;
      private long duration = 0;
      private MediaPlayer mediaPlayer;
      boolean next = false;

    public class ServiceHandler extends Handler {
        private int currentTime = 0;
        private Handler handler;
      public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
          if (stopService == false){
            Bundle bundle = msg.getData();
           ArrayList<Baihat> baihats1 = (ArrayList<Baihat>) bundle.get("baihatstart");
             if (baihats1 != null){
              baihats = baihats1;
            }
            switch (msg.what) {
                case -1:
                    startMp3(baihats,0);
                    break;
                case 0:
                    resumeMp3(baihats);
                    break;
                case 1:
                    pauseMp3(baihats);
                    break;
                case 2:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        stopForeground(STOP_FOREGROUND_REMOVE);
                    }
                    if (mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                    }
                    break;
                case 3:
                    preMp3(baihats);
                    break;
                case 4:
                    nextMp3(baihats);
                    break;
            }
          }
        }
        public void setFalseRepeat(){
            repeat = false;
        }
        public void setTrueRepeat(){
            repeat = true;
        }
        public void setFalseRandom(){
            random = false;
        }
        public void setTrueRandom(){
            random = true;
        }
        public void clearBaihats(){
            baihats.clear();
        }

        private void getUpdateCurrentTime(){
            if (mediaPlayer != null) {
                if (isPlaying) {
                    handler = new Handler();
                    handler.postDelayed(runnableTime, 500);
                }
            }
        }
        private Runnable runnableTime = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    if (mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration()) {
                        onListenDuration.onCurrentDuration(mediaPlayer.getCurrentPosition());
                    }
                    if (isPlaying){
                        handler.postDelayed(this,500);
                    }
                }
            }
        };
        public void pauseMp3(ArrayList<Baihat> baihats) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                currentTime = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                isPlaying = false;
                notificationManager.notify(1, makeNotification(baihats,mPosition,isPlaying));
            }
        }
        public void resumeMp3(ArrayList<Baihat> baihats) {
            if (mediaPlayer != null){
                isPlaying = true;
                mediaPlayer.seekTo(currentTime);
                mediaPlayer.start();
                getUpdateCurrentTime();
                AutoPlayNext(baihats);
                if (baihats.size() > 0) {
                    notificationManager.notify(1, makeNotification(baihats,mPosition,isPlaying));
                }
            }
        }
        public void preMp3(ArrayList<Baihat> baihats) {
            if (baihats.size() > 0) {
                positionRandom = mPosition;
                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;

                }
                if (mPosition < baihats.size()) {
                    mPosition--;
                    if (repeat == true) {
                        mPosition += 1;
                    }
                    if (random == true) {
                        Random random = new Random();

                        int index = random.nextInt(baihats.size());
                        if (index == positionRandom) {
                            mPosition = positionRandom - 1;
                        }else {
                            mPosition = index;
                        }
                    }
                    if (mPosition < 0){
                        mPosition = baihats.size() - 1;
                    }
                }
                Log.d("BBB","last" + mPosition);
                serviceHandler.startMp3(baihats,mPosition);
                getUpdateCurrentTime();
            }
        }
        public void nextMp3(ArrayList<Baihat> baihats){
            if (baihats.size() > 0) {
                positionRandom = mPosition;
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;

                        if (mPosition < baihats.size()) {
                            mPosition++;
                            if (repeat == true) {
                                if (mPosition == 0) {
                                    mPosition = baihats.size();
                                }
                                mPosition -= 1;
                            }
                            if (random == true) {
                                Random random = new Random();
                                int index = random.nextInt(baihats.size());
                                if (index == positionRandom) {
                                    mPosition = positionRandom + 1;
                                }else {
                                    mPosition = index;
                                }
                            }
                            if (mPosition > (baihats.size() - 1) ){
                                mPosition = 0;
                            }
                        }
                        serviceHandler.startMp3(baihats,mPosition);
                        getUpdateCurrentTime();
                    }
            }
        }
        public void startMp3(ArrayList<Baihat> baihats, int position) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(baihats.get(position).getLinkBaihat()));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    isPlaying = true;
                    if (baihats.size() > 0) {
                        notificationManager.notify(1, makeNotification(baihats,mPosition,isPlaying));
                    }
                    duration = mediaPlayer.getDuration();
                   AutoPlayNext(baihats);
                   getUpdateCurrentTime();
                }
            });
        }
        private void AutoPlayNext(ArrayList<Baihat> baihats){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null){
                        handler.postDelayed(this,300);
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                next = true;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            },300);
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (next == true){
                        if (baihats.size() > 0) {
                            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                                if (mPosition < baihats.size()) {
                                    mPosition++;
                                    if (repeat == true) {
                                        if (mPosition == 0) {
                                            mPosition = baihats.size();
                                        }
                                        mPosition -= 1;
                                    }
                                    if (random == true) {
                                        Random random = new Random();
                                        positionRandom = mPosition;
                                        int index = random.nextInt(baihats.size());
                                        if (index == positionRandom) {
                                            mPosition = positionRandom + 1;
                                        }else {
                                            mPosition = index;
                                        }
                                    }
                                    if (mPosition > (baihats.size() - 1) ){
                                        mPosition = 0;
                                    }
                                }
                                serviceHandler.startMp3(baihats,mPosition);
                            }
                        }
                        next = false;
                        handler1.removeCallbacks(this);
                    }else {
                        handler1.postDelayed(this, 1000);
                    }
                }
            },1000);
        }
    }
    public class MyBound extends Binder {
       public MyService getService() {
            return MyService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("BBB"," onBind");
        return new MyBound();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("BBB"," onUnbind");
        return super.onUnbind(intent);
    }
    @Override
    public void onRebind(Intent intent) {
        stopService = false;
        Log.d("BBB"," onRebind");
        super.onRebind(intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Log.d("BBB"," onCreate");
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        stopService = false;
        Log.d("BBB"," onStartCommand");
        Bundle bundle = intent.getExtras();
        ArrayList<Baihat>  baihatArrayList = null;
       baihatArrayList = (ArrayList<Baihat>) bundle.getSerializable("baihatplay");
        int requestCode = intent.getIntExtra("requestCode", -1);
        Message msg = serviceHandler.obtainMessage();
        stopService = false;

        switch (requestCode) {
            case -1:
               bundle.putSerializable("baihatstart",baihatArrayList);
                msg.what = -1;
                msg.setData(bundle);
                serviceHandler.sendMessage(msg);
                break;
            case 0:
                bundle.putSerializable("baihatstart",baihatArrayList);
                msg.setData(bundle);
                msg.what = 0;
                serviceHandler.sendMessage(msg);
                break;
            case 1:
                bundle.putSerializable("baihatstart",baihatArrayList);
                msg.setData(bundle);
                msg.what = 1;
                serviceHandler.sendMessage(msg);
                break;
            case 2:
                msg.setData(bundle);
                msg.what = 2;
                serviceHandler.sendMessage(msg);
                break;
            case 3:
                msg.setData(bundle);
                msg.what = 3;
                serviceHandler.sendMessage(msg);
                break;
            case 4:
                msg.setData(bundle);
                msg.what = 4;
                serviceHandler.sendMessage(msg);
                break;
        }
        return START_NOT_STICKY;
    }
    public Notification makeNotification(ArrayList<Baihat> baihats,int position, boolean isPlaying) {
        Intent intentMusic = new Intent(this, MyService.class);

        int action = -1;
        intentMusic.putExtra("requestCode", action );
        PendingIntent pendingIntent = PendingIntent.getService(
                this,
                0,
                intentMusic,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.tvBaihatSv,baihats.get(position).getTenBaihat());
        remoteViews.setTextViewText(R.id.tvCasiSv,baihats.get(position).getCasi());

        remoteViews.setImageViewResource(R.id.imagePlayorPause,R.drawable.ic_pause);
        remoteViews.setImageViewResource(R.id.imageClear,R.drawable.ic_clear);
        if (isPlaying){
            remoteViews.setOnClickPendingIntent(R.id.imagePlayorPause,getPendingIntent(this,PAUSE_MUSIC_CODE));
            remoteViews.setImageViewResource(R.id.imagePlayorPause,R.drawable.ic_pause);
        }else {
            remoteViews.setOnClickPendingIntent(R.id.imagePlayorPause,getPendingIntent(this,RESUME_MUSIC_CODE));
            remoteViews.setImageViewResource(R.id.imagePlayorPause,R.drawable.iconplay);
        }
        remoteViews.setOnClickPendingIntent(R.id.imageClear,getPendingIntent(this,CLEAR_MUSIC_CODE));
        remoteViews.setOnClickPendingIntent(R.id.imagePre,getPendingIntent(this,PRE_MUSIC_CODE));
        remoteViews.setOnClickPendingIntent(R.id.imageNext,getPendingIntent(this,NEXT_MUSIC_CODE));

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setWhen(System.currentTimeMillis())
                .setSound(null)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews);
        startForeground(1, notification.build());
        return notification.build();
    }

    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(context, MyService.class);
        intent.putExtra("requestCode", action);
        return PendingIntent.getService(context, action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    @Override
    public void onDestroy() {
        Log.d("BBB"," onDestroy");
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
    public void setOnListenDuration(OnListenDuration onListenDuration) {
        this.onListenDuration = onListenDuration;
    }
   public interface OnListenDuration {
        void onCurrentDuration(long time);
    }

    public long getDuration() {
        return duration;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isRandom() {
        return random;
    }

    public int getmPosition() {
        return mPosition;
    }
}

