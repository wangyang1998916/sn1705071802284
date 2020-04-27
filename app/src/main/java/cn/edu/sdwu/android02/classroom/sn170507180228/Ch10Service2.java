package cn.edu.sdwu.android02.classroom.sn170507180228;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class Ch10Service2 extends Service {
    private MediaPlayer mediaPlayer;
    private  MyBinder myBinder;
    public Ch10Service2() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
       mediaPlayer=MediaPlayer.create(this,R.raw.wav);
        mediaPlayer.setLooping(false);
        myBinder= new MyBinder();
        Log.i(Ch10Service2.class.toString(),"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       Log.i(Ch10Service2.class.toString()," onStartCommand");
        //Ietent 获取用户处理
        String state=intent.getStringExtra("PlayerState");
        if (state!=null){
            if (state.equals("START")){
                start();
                //播放
            }
            if (state.equals("STOP")){
                stop();
                // /暂停
            }
            if (state.equals("PAUSE")){
                pause();
                //停止播放
            }
            if (state.equals("STOPSERVICE")){
                stopSelf();
                //结束服务
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public void start(){
        mediaPlayer.start();
    }
    public void pause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
    public  void stop(){
        mediaPlayer.stop();
        //为了下一次播放调用prepare方法
        try{
            mediaPlayer.prepare();

        }catch (Exception e){
            Log.e(Ch10Service2.class.toString(),e.toString());
        }

    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
return myBinder;
    }
    public  class MyBinder extends Binder{
        public Ch10Service2 getCh10service(){
            return  Ch10Service2.this;
        }
    }
}
