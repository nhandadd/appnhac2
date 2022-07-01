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

import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;

import java.util.ArrayList;
import java.util.Random;

public class MyService extends Service {
      private ArrayList<Baihat>  baihatArrayList;
      private String ten,hinhanh, baihat, casi;
      private Looper serviceLooper;
      public ServiceHandler serviceHandler;
      private NotificationManager notificationManager;
      private boolean isPlaying = false;
      private int RESUME_MUSIC_CODE = 0;
      private int PAUSE_MUSIC_CODE = 1;
      private int CLEAR_MUSIC_CODE = 2;

      private boolean stopService = false;
      boolean repeat = false;
      boolean random = false;
      int position = 0;
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
            Bundle bundle = msg.getData();
          ArrayList<Baihat> baihats = (ArrayList<Baihat>) bundle.get("baihatstart");
            ten = baihats.get(0).getTenBaihat();
            casi = baihats.get(0).getCasi();
            hinhanh = baihats.get(0).getHinhBahat();
            baihat = baihats.get(0).getLinkBaihat();
            switch (msg.what) {
                case -1:
                    startMp3(baihats);
                    break;
                case 0:
                    resumeMp3(baihats);
                    break;
                case 1:
                    pauseMp3(baihats);
                    break;
                case 2:
                    stopService = true;
                    break;
            }
        }
        private void disableUpdateTime() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()){
                handler.removeCallbacks(runnableTime);
            }
        }
        private void updateCurrentTime(){
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
                if (mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration()) {

                    onListenDuration.onCurrentDuration(mediaPlayer.getCurrentPosition());
                }
                if (isPlaying){
                    handler.postDelayed(this,500);
                }
            }
        };
        public void pauseMp3(ArrayList<Baihat> baihats) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                currentTime = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                isPlaying = false;
                notificationManager.notify(1, makeNotification(baihats,false));
            }
        }
        public void resumeMp3(ArrayList<Baihat> baihats) {
            if (mediaPlayer != null){
                isPlaying = true;
                mediaPlayer.seekTo(currentTime);
                mediaPlayer.start();
                AutoPlayNext(baihats);
                notificationManager.notify(1, makeNotification(baihats,true));
            }
        }
        public void preMp3(ArrayList<Baihat> baihats) {
            if (baihatArrayList.size() > 0) {
                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if (position < baihatArrayList.size()) {
                    position--;

                    if (repeat == true) {
                        position += 1;
                    }
                    if (random == true) {
                        Random random = new Random();
                        positionRandom = position;
                        int index = random.nextInt(baihatArrayList.size());
                        if (index == positionRandom) {
                            position = positionRandom - 1;
                        }
                    }
                    if (position < 0){
                        position = baihatArrayList.size() - 1;
                    }
                }  startMp3(baihatArrayList);
            }
        }
        public void nextMp3(ArrayList<Baihat> baihats){
            if (baihatArrayList.size() > 0) {
                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if (position < baihatArrayList.size()) {
                    position++;
                    if (repeat == true) {
                        if (position == 0) {
                            position = baihatArrayList.size();
                        }
                        position -= 1;
                    }
                    if (random == true) {
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
                } startMp3(baihatArrayList);
            }
        }
        public void startMp3(ArrayList<Baihat> baihats) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(baihats.get(0).getLinkBaihat()));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                  notificationManager.notify(1, makeNotification(baihats,true));
                    isPlaying = true;
                    mediaPlayer.start();
                    duration = mediaPlayer.getDuration();
                   AutoPlayNext(baihats);
                }
            });
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
        serviceHandler.updateCurrentTime();
        Log.d("BBB"," onBind");
        return new MyBound();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        serviceHandler.disableUpdateTime();
        Log.d("BBB"," onUnbind");
        return super.onUnbind(intent);
    }
    @Override
    public void onRebind(Intent intent) {
        stopService = false;
        serviceHandler.updateCurrentTime();
        Log.d("BBB"," onRebind");
        super.onRebind(intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Log.d("BBB"," onCreate");
        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//         start foreground
        baihatArrayList.add()
        startForeground(1, makeNotification(baihatArrayList, isPlaying));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BBB"," onStartCommand");
        Bundle bundle = intent.getExtras();
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
                msg.setData(bundle);
                msg.what = 0;
                serviceHandler.sendMessage(msg);
                break;
            case 1:
                msg.setData(bundle);
                msg.what = 1;
                serviceHandler.sendMessage(msg);
                break;
            case 2:
                msg.setData(bundle);
                msg.what = 2;
                serviceHandler.sendMessage(msg);
                break;
        }
        return START_NOT_STICKY;
    }
    public Notification makeNotification(ArrayList<Baihat> baihats, boolean isPlaying) {
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
        remoteViews.setTextViewText(R.id.tvBaihatSv,baihats.get(0).getTenBaihat());
        remoteViews.setTextViewText(R.id.tvCasiSv,baihats.get(0).getCasi());
//        remoteViews.setImageViewUri(R.id.imageBaihatSv, Uri.parse(hinhanh));

        remoteViews.setImageViewResource(R.id.imagePlayorPause,R.drawable.ic_pause);
        remoteViews.setImageViewResource(R.id.imageClear,R.drawable.ic_clear);
        if (isPlaying){
            remoteViews.setOnClickPendingIntent(R.id.imagePlayorPause,getPendingIntent(this,PAUSE_MUSIC_CODE));
            remoteViews.setImageViewResource(R.id.imagePlayorPause,R.drawable.ic_pause);
            remoteViews.setOnClickPendingIntent(R.id.imageClear,getPendingIntent(this,CLEAR_MUSIC_CODE));
        }else {
            remoteViews.setOnClickPendingIntent(R.id.imagePlayorPause,getPendingIntent(this,RESUME_MUSIC_CODE));
            remoteViews.setImageViewResource(R.id.imagePlayorPause,R.drawable.iconplay);
            remoteViews.setOnClickPendingIntent(R.id.imageClear,getPendingIntent(this,CLEAR_MUSIC_CODE));
        }
        remoteViews.setOnClickPendingIntent(R.id.imageClear,getPendingIntent(this,CLEAR_MUSIC_CODE));

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setWhen(System.currentTimeMillis())
                .setSound(null)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews);
        startForeground(1, notification.build());
        return notification.build();
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
                        if (position < baihatArrayList.size()) {
                            position++;
                            if (repeat == true) {
                                if (position == 0) {
                                    position = baihatArrayList.size();
                                }
                                position -= 1;
                            }
                            if (random == true) {
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
                            serviceHandler.startMp3(baihats);
                        }
                        next = false;
                        handler1.removeCallbacks(this);
                    }else {
                        handler1.postDelayed(this, 1000);
                    }
                }
            },1000);
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
        }  super.onDestroy();
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

    public boolean isNext() {
        return next;
    }
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public boolean isStopService() {
        return stopService;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isRandom() {
        return random;
    }

    public boolean setKeyStatusFalse(boolean keyStatus){
        keyStatus = false;
        return keyStatus;
    }
    public boolean setKeyStatusTrue(boolean keyStatus){
        keyStatus = true;
        return keyStatus;
    }
}

