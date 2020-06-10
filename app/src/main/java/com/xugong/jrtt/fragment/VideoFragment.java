package com.xugong.jrtt.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.videolist.BaseVideoItem;
import com.videolist.MyVideoItem;
import com.videolist.VideoListViewAdapter;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.ListViewItemPositionGetter;
import com.xugong.jrtt.R;
import com.xugong.jrtt.bean.VideoData;
import com.xugong.jrtt.net.MyApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoFragment  extends BaseFragment {
    //1:布局


    public View getMyView() {

        View view = View.inflate(getContext(), R.layout.fragment_video,null);
        listView=view.findViewById(R.id.listview);
        return view;
    }

    //2:获取服务端数据


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("VideoFragment onActivityCreated");

        //retrofit ->MyApi
        retrofit.create(MyApi.class).getVideoData().enqueue(new Callback<VideoData>() {
            @Override
            public void onResponse(Call<VideoData> call, Response<VideoData> response) {
                try {
                    System.out.println("VideoFragment onResponse");
                    System.out.println(response.body().data);//json
                    setDataToListView(response.body().data);


                    //# 1：滚动播放列表中心的视频
                    initCalculator();
                    //# 6：添加滚动监听器
                    onListViewScroll();
                    initIndex();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<VideoData> call, Throwable t) {
                Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT).show();

            }
        });

    }
    //---------------------------完成视频滚动停止之后的自动播放---------------Start
    private ListItemsVisibilityCalculator mListItemVisibilityCalculator;
    //# 2：Getter可以获取视频的position
    private ItemsPositionGetter mItemsPositionGetter;

    /*
	# 3：将坐标转成position
	记录列表滚动的状态
	    SCROLL_STATE_IDLE:列表停住

	*/
    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
    private void initCalculator() {
        /**
         * 5：用于计算处于列表中间的条目是哪个。
         * 暂停其他视频，播放当前视频
         */
        mListItemVisibilityCalculator =
                new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), list);

        mItemsPositionGetter = new ListViewItemPositionGetter(listView);
    }

    private void onListViewScroll() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // # 6 :记录状态
                mScrollState = scrollState;
                if (scrollState == SCROLL_STATE_IDLE && !list.isEmpty()) { //停止
                    mListItemVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter, view.getFirstVisiblePosition(), view.getLastVisiblePosition());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!list.isEmpty()) {
                    //计算可见item的坐标
                    System.out.println("mListItemVisibilityCalculator="+mListItemVisibilityCalculator);
                    System.out.println("mItemsPositionGetter="+mItemsPositionGetter);
                    mListItemVisibilityCalculator.onScroll(mItemsPositionGetter, firstVisibleItem, visibleItemCount, mScrollState);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("VideoFragment onResume");
        initIndex();
    }

    private void initIndex() {
        if(!list.isEmpty()){
            listView.post(new Runnable() {
                @Override
                public void run() {
                    //由坐标得到item的位置position
                    mListItemVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            listView.getFirstVisiblePosition(),
                            listView.getLastVisiblePosition());

                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // 如果页面处理后台，那么停止播放
        playerManager.resetMediaPlayer();
    }


    //---------------------------完成视频滚动停止之后的自动播放---------------End

    private ListView listView;
    //适配器指定使用BaseVideoItem类型
    private List<BaseVideoItem> list=new ArrayList<>();
    //3:显示
    private void setDataToListView(VideoData.VideoBean data) {
        if(listView!=null){

            //做一个数据的转换  VlistBean->MyVideoItem
            for(VideoData.VideoBean.VlistBean video: data.vlist){
                list.add(new MyVideoItem(getContext(),getVideoManager(),video));
            }
            //创建适配器
            //#视频列表：1:添加适配器
            //参1：播放管理者 参2：上下文
            VideoListViewAdapter adapter=new VideoListViewAdapter(getVideoManager(),getContext(),list);
            //设置适配器
            listView.setAdapter(adapter);
        }


    }
    private VideoPlayerManager<MetaData> playerManager;
    private VideoPlayerManager<MetaData> getVideoManager() {

        if(playerManager==null){
            playerManager=new SingleVideoPlayerManager(new PlayerItemChangeListener(){

                @Override
                public void onPlayerItemChanged(MetaData currentItemMetaData) {
                    //不写代码
                }
            });
        }
        return playerManager;
    }
}
