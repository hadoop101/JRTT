package com.xugong.jrtt.fragment.sub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xugong.jrtt.R;
import com.xugong.jrtt.bean.NewListData;
import com.xugong.jrtt.fragment.BaseFragment;
import com.xugong.jrtt.net.MyApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//1:继承BaseFragment
public class NewListFragment extends BaseFragment {

    //2:重写getMyView
    //3:布式列表
    @Override
    protected View getMyView() {
        System.out.println("oncreateView");
        //4：加载
        return View.inflate(getContext(), R.layout.fragment_new_list,null);//参1，上下文，参2，布局
    }

    //5:请求服务端数据

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");

        //http://192.168.88.101:8080/jrtt/10007/list_1.json
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.88.101:8080/jrtt/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        //6:执行请求
        retrofit.create(MyApi.class).getNewList().enqueue(new Callback<NewListData>() {
            @Override
            public void onResponse(Call<NewListData> call, Response<NewListData> response) {
                /*System.out.println(response.body().retcode);
                System.out.println(response.body().data);*/
                setDataToView(response.body().data);
            }

            @Override
            public void onFailure(Call<NewListData> call, Throwable t) {

            }
        });//
    }
    //7：显示
    private void setDataToView(NewListData.DataBean data) {
        //7.1查找出控件
        PullToRefreshListView pullToRefreshListView = fragmentView.findViewById(R.id.pull_listview);
        //7.2.赋值一个适配器
        NewListAdapter adapter=new NewListAdapter(data.news);
        pullToRefreshListView.setAdapter(adapter);
    }

    //8:定义适配器
    class NewListAdapter extends BaseAdapter{
        private List<NewListData.DataBean.NewsBean> listData;
        //构造方法
        public NewListAdapter(List<NewListData.DataBean.NewsBean> list){
            listData=list;
        }
        @Override
        public int getCount() {//行数
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //行视图
            NewListData.DataBean.NewsBean newsBean = listData.get(position); //每一条新闻是newsBean

            //视图 R.layout.item_new_one
            View view = View.inflate(getContext(),R.layout.item_new_one,null);

            //赋值
            TextView title=view.findViewById(R.id.item_title);
            TextView date=view.findViewById(R.id.item_date);
            ImageView image=view.findViewById(R.id.item_image);

            String imageUrl="http://192.168.88.101:8080/"+newsBean.listimage;
            Glide.with(getContext()).load(imageUrl).into(image);
            //设置缩放类型
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);//先按比例放大，再裁切

            title.setText(newsBean.title);
            date.setText(newsBean.pubdate);
            return view;
        }

    }
}
