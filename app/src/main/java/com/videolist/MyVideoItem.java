package com.videolist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.xugong.jrtt.bean.VideoData;

/**
 *数据类型
 */
public class MyVideoItem extends BaseVideoItem {

    private VideoData.VideoBean.VlistBean item;
    private Context context;
    public MyVideoItem(Context context,VideoPlayerManager<MetaData> videoPlayerManager, VideoData.VideoBean.VlistBean item) {
        super(videoPlayerManager);
        this.item = item;
        this.context=context;
    }

    @Override
    public void update(int position, VideoViewHolder viewHolder, VideoPlayerManager videoPlayerManager) {
        //显示 集数+标题
        viewHolder.mTitle.setText(item.vn+item.vt);
        viewHolder.mCover.setVisibility(View.VISIBLE);
        //加载
        Glide.with(context).load(item.vpic).into(viewHolder.mCover);
        //缩放
        viewHolder.mCover.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager) {
        item.vurl="http://192.168.88.101:8080/jrtt/video.mp4";//因为目前服务是假数据，没有视频
        videoPlayerManager.playNewVideo(currentItemMetaData, player, item.vurl);
    }

    @Override
    public void stopPlayback(VideoPlayerManager videoPlayerManager) {
        videoPlayerManager.stopAnyPlayback();
    }
}
