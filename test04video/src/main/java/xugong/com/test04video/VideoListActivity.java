package xugong.com.test04video;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.ListViewItemPositionGetter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xugong.com.test04video.videolist.BaseVideoItem;
import xugong.com.test04video.videolist.MyVideoItem;
import xugong.com.test04video.videolist.VideoListViewAdapter;

//1：定义类
public class VideoListActivity extends AppCompatActivity {


    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //2:布局
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);

        //3:布局条目

        initData();
        initAdapter();

        //# 1：滚动播放列表中心的视频
        initCalculator();

        //# 6：添加滚动监听器
        onListViewScroll();

    }

    private void onListViewScroll() {
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // # 6 :记录状态
                mScrollState = scrollState;
                if (scrollState == SCROLL_STATE_IDLE && !mList.isEmpty()) { //停止
                    mListItemVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter, view.getFirstVisiblePosition(), view.getLastVisiblePosition());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!mList.isEmpty()) {
                    //计算可见item的坐标
                    System.out.println("mListItemVisibilityCalculator="+mListItemVisibilityCalculator);
                    System.out.println("mItemsPositionGetter="+mItemsPositionGetter);
                    mListItemVisibilityCalculator.onScroll(mItemsPositionGetter, firstVisibleItem, visibleItemCount, mScrollState);
                }
            }
        });
    }

    private  ListItemsVisibilityCalculator mListItemVisibilityCalculator;
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
           new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mList);

          mItemsPositionGetter = new ListViewItemPositionGetter(listview);
    }

    private void initAdapter() {
        //参1播放对象  参2：上下文 参3：集合
     VideoListViewAdapter adapter=new VideoListViewAdapter(getVideoManager(),this,mList);
     listview.setAdapter(adapter);
    }

    //4:List  当前框架使用的数据类型必须是BaseVideoItem
    private List<BaseVideoItem> mList=new ArrayList<>();
    private void initData() {
        //参1：播放管理对象 参2：标题 参3：视频播放地址
        mList.add(new MyVideoItem(getVideoManager(),"http://192.168.88.101:8080/jrtt/video.mp4","1视频"));
        mList.add(new MyVideoItem(getVideoManager(),"http://192.168.88.101:8080/jrtt/video.mp4","2视频"));
        mList.add(new MyVideoItem(getVideoManager(),"http://192.168.88.101:8080/jrtt/video.mp4","3视频"));
        mList.add(new MyVideoItem(getVideoManager(),"http://192.168.88.101:8080/jrtt/video.mp4","4视频"));
        mList.add(new MyVideoItem(getVideoManager(),"http://192.168.88.101:8080/jrtt/video.mp4","5视频"));
        mList.add(new MyVideoItem(getVideoManager(),"http://192.168.88.101:8080/jrtt/video.mp4","6视频"));

    }

    //5:创建播放对象
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

    @Override
    public void onResume() {
        super.onResume();
        if(!mList.isEmpty()){
            listview.post(new Runnable() {
                @Override
                public void run() {
                    //由坐标得到item的位置position
                    mListItemVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            listview.getFirstVisiblePosition(),
                            listview.getLastVisiblePosition());

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

}
