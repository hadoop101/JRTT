package xugong.com.test04video.videolist;

import android.view.View;


import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;

/**
 * Use this class if you have direct path to the video source
 */
public class MyVideoItem extends BaseVideoItem {

    private final String mDirectUrl;
    private final String mTitle;

    public MyVideoItem(VideoPlayerManager<MetaData> videoPlayerManager, String mDirectUrl, String mTitle) {
        super(videoPlayerManager);
        this.mDirectUrl = mDirectUrl;
        this.mTitle = mTitle;
    }

    @Override
    public void update(int position, VideoViewHolder viewHolder, VideoPlayerManager videoPlayerManager) {
        viewHolder.mTitle.setText(mTitle);
        viewHolder.mCover.setVisibility(View.VISIBLE);

    }

    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager) {
        videoPlayerManager.playNewVideo(currentItemMetaData, player, mDirectUrl);
    }

    @Override
    public void stopPlayback(VideoPlayerManager videoPlayerManager) {
        videoPlayerManager.stopAnyPlayback();
    }
}
