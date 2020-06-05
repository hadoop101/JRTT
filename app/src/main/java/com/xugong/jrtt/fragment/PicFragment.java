package com.xugong.jrtt.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.xugong.jrtt.R;
import com.xugong.jrtt.bean.PicData;
import com.xugong.jrtt.net.MyApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicFragment extends BaseFragment {

    //1:布局

    //变量
    private  boolean isListVisible = true;
    private ListView listView;
    private  GridView gridView;
    @Override
    protected View getMyView() {
        View view= View.inflate(getContext(), R.layout.fragment_pic,null);
        //查找控件
         listView=view.findViewById(R.id.listview);
         gridView=view.findViewById(R.id.gridview);
        ImageView swtichBtn = view.findViewById(R.id.switchBtn);
        //点击事件
        swtichBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //列表与网格互斥
                if(isListVisible){
                    isListVisible = false;
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                }else{
                    isListVisible = true;
                    listView.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);//隐藏
                }
            }
        });
        return view;
    }


    //2:取服务端数据

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //retrofit
        retrofit.create(MyApi.class).getPicData().enqueue(new Callback<PicData>() {
            @Override
            public void onResponse(Call<PicData> call, Response<PicData> response) {
                //成功
                Toast.makeText(getContext(), response.body().data.title, Toast.LENGTH_SHORT).show();
                setDataToView(response.body().data.news);
            }

            @Override
            public void onFailure(Call<PicData> call, Throwable t) {
                //失败
                Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //3.2 编写QuickAdapter
    class PicAdapter extends QuickAdapter<PicData.PicDataBean.PicNewsBean>{

        public PicAdapter(Context context, int layoutResId, List<PicData.PicDataBean.PicNewsBean> data) {
            super(context, layoutResId, data);//参1，上下文，参2 布局 参3 集合
        }

        @Override
        protected void convert(BaseAdapterHelper helper, PicData.PicDataBean.PicNewsBean item) {
            //赋值，不需要写 if判断 ，也不需要做holder
            helper.setText(R.id.item_title,item.title);//id，数据

            ImageView imageView=helper.getView(R.id.item_image);
            // "listimage": "http://10.0.2.2:8080/jrtt/photos/images/46728356PMQ6.jpg",
            String url=item.listimage.replace("http://10.0.2.2:8080/",HOST);
            Glide.with(getContext()).load(url).into(imageView);

            //3.4设置图片的缩放比例
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }
    }

    private void setDataToView(List<PicData.PicDataBean.PicNewsBean> news) {
        //3:将数据设置给布局
        //3.1 布局条目录

        //3.3 创建适配器，赋值给ListView，还赋值给gridView
        PicAdapter adapter = new PicAdapter(getContext(),R.layout.item_list_pic,news);
        listView.setAdapter(adapter);
        gridView.setAdapter(adapter);

    }



}
