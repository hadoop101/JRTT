package com.xugong.jrtt.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        //4,跳转功能
        listenJumpBtn();
    }

    //4.3
    private boolean isJump = false;//控制 点击关闭之后就不要播放完成再关闭
    private void listenJumpBtn() {
        Button jump=findViewById(R.id.jump);
        //4.1 添加跳过点击监听器
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isJump = true;
                //4.2
                //关闭当前页面
                finish();
                //开启  参1，当前上下文 参2，主页面
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        });
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
                if(!isJump){
                    //关闭当前页面
                    finish();
                    //开启  参1，当前上下文 参2，主页面
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }


            }
        });
    }

}
