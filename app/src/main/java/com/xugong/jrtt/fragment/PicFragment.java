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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    protected View getMyView() {
        View view= View.inflate(getContext(), R.layout.fragment_pic,null);
        //查找控件
        final ListView listView=view.findViewById(R.id.listview);
        final GridView gridView=view.findViewById(R.id.gridview);
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

    private void setDataToView(List<PicData.PicDataBean.PicNewsBean> news) {
        //3:将数据设置给布局
    }



}
