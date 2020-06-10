package xugong.com.test04video;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.SimpleMainThreadMediaPlayerListener;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.video_player_1)
    VideoPlayerView videoPlayer1;
    @BindView(R.id.video_cover_1)
    ImageView videoCover1;

    @BindView(R.id.video_player_2)
    VideoPlayerView videoPlayer2;
    @BindView(R.id.video_cover_2)
    ImageView videoCover2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //3:初始化VideoPlayerManager
        initManager();
    }

    private VideoPlayerManager<MetaData> playerManager;
    private void initManager() {
        //播放，停止，重新播放一个视频
         playerManager=new SingleVideoPlayerManager(new PlayerItemChangeListener() {
            @Override
            public void onPlayerItemChanged(MetaData currentItemMetaData) {
                //nothing todo
            }
        });
        //监听器1
        videoPlayer1.addMediaPlayerListener(new SimpleMainThreadMediaPlayerListener(){
            @Override
            public void onVideoPreparedMainThread() {
                videoCover1.setVisibility(View.INVISIBLE);//图片隐藏
            }

            @Override
            public void onVideoCompletionMainThread() {
                videoCover1.setVisibility(View.VISIBLE);//图片显示
            }

            @Override
            public void onVideoStoppedMainThread() {
                videoCover1.setVisibility(View.VISIBLE);//图片显示
            }
        });//加载，播放完成，播放停止

        //监听器1
        videoPlayer2.addMediaPlayerListener(new SimpleMainThreadMediaPlayerListener(){
            @Override
            public void onVideoPreparedMainThread() {
                videoCover2.setVisibility(View.INVISIBLE);//图片隐藏
            }

            @Override
            public void onVideoCompletionMainThread() {
                videoCover2.setVisibility(View.VISIBLE);//图片显示
            }

            @Override
            public void onVideoStoppedMainThread() {
                videoCover2.setVisibility(View.VISIBLE);//图片显示
            }
        });//加载，播放完成，播放停止
    }

    @OnClick({R.id.video_cover_1,R.id.video_cover_2})
    public void onViewClicked(View view) {
        if(view.getId()==R.id.video_cover_1){
            //参2：画图控件
            try {
                playerManager.playNewVideo(null,videoPlayer1,getVideoUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(view.getId()==R.id.video_cover_2){
            //参2：画图控件
            try {
                playerManager.playNewVideo(null,videoPlayer2,getVideoUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private AssetFileDescriptor getVideoUrl() throws IOException {
        return getAssets().openFd("video.mp4");
    }

   /* private String getVideoUrl() {
        //4:网络权限<uses-permission android:name="android.permission.INTERNET"/>
        return "http://192.168.88.101:8080/jrtt/video.mp4";
    }*/

    @Override
    protected void onStop() {//页面不显示
        super.onStop();
        playerManager.stopAnyPlayback();//停止
        videoCover1.setVisibility(View.VISIBLE);//图片显示
        videoCover2.setVisibility(View.VISIBLE);//图片显示
    }
}
