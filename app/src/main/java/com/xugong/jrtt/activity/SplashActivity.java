package com.xugong.jrtt.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import com.xugong.jrtt.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //1,布局VideoView
        //2,视频放在res/raw/video
        //3,初始化视频播放
        startPlayVideo();
    }

    private void startPlayVideo() {
        //播放地址
        Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
        //调用VideoView来播放
        VideoView videoView=findViewById(R.id.videoview);
        videoView.setVideoURI(uri);
        videoView.start();

        //播放完成的监听器
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //关闭当前页面
                finish();
                //开启  参1，当前上下文 参2，主页面
                startActivity(new Intent(SplashActivity.this,MainActivity.class));

            }
        });
    }
}
