package com.xugong.jrtt.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        //5,将画面设置成屏幕大小
        setVideoSize();

    }

    //5,
    private void setVideoSize() {
        //5.1 攻取屏幕尺寸对象
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //5.2 创建布局参数
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(metrics.widthPixels,metrics.heightPixels);
        //5.3 设置给VideoView
        videoView.setLayoutParams(params);
        //低版本模拟器上添加
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

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

    private VideoView videoView;
    private void startPlayVideo() {
        //播放地址
        Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
        //调用VideoView来播放
         videoView=findViewById(R.id.videoview);
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
