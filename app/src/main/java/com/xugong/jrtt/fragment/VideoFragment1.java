package com.xugong.jrtt.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.xugong.jrtt.R;
import com.xugong.jrtt.bean.VideoData;
import com.xugong.jrtt.net.MyApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoFragment1 extends BaseFragment {
    //1:布局


    public View getMyView() {
        return View.inflate(getContext(), R.layout.fragment_video,null);
    }

    //2:获取服务端数据


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //retrofit ->MyApi
        retrofit.create(MyApi.class).getVideoData().enqueue(new Callback<VideoData>() {
            @Override
            public void onResponse(Call<VideoData> call, Response<VideoData> response) {
                try {
                    System.out.println(response.body().data);//json
                    setDataToListView(response.body().data);
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

    private ListView listView;
    //3:显示
    private void setDataToListView(VideoData.VideoBean data) {
        if(listView==null){
            listView=fragmentView.findViewById(R.id.listview);
            //创建适配器
            QuickAdapter<VideoData.VideoBean.VlistBean> adapter=new QuickAdapter<VideoData.VideoBean.VlistBean>(getContext(),R.layout.item_list_novideo,data.vlist) {
                @Override
                protected void convert(BaseAdapterHelper helper, VideoData.VideoBean.VlistBean item) {
                    //赋值
                    helper.setText(R.id.title,item.vn+item.vt);
                    ImageView imageView=helper.getView(R.id.cover_image);
                    //加载
                    Glide.with(getActivity()).load(item.vpic).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            };
            //设置适配器
            listView.setAdapter(adapter);
        }


    }
}
