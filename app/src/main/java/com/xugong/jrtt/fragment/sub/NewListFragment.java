package com.xugong.jrtt.fragment.sub;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

    class ViewHolderOne{
        public TextView title;
        public TextView date;
        public ImageView image;
    }
    class ViewHolderThree extends ViewHolderOne{
        public ImageView image1;
        public ImageView image2;
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
        public int getViewTypeCount() {//两种视频，一种是一图，另一种是三图
            return 2;
        }

        @Override
        public int getItemViewType(int position) {//哪一个是三图的，哪一行是一图
            NewListData.DataBean.NewsBean bean = listData.get(position);
            if(bean.type==0){
                //一图
                return 0;//R.layout.item_new_one.xml
            }else {
                //三图
                return 1;//R.layout.item_new_three.xml
            }
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

            //A:获取数据
            NewListData.DataBean.NewsBean bean = listData.get(position);

            //B:返回视图
            //判断当前视图的类型是一图，还是三图
            int type=getItemViewType(position);
            if(type==0){
                convertView = setDataToOneView(convertView, bean);
                //C:赋值
                return convertView;
            }else{//1
                //A:数据
                //B:控件
                convertView = setDataToThreeView(convertView,bean);
                return convertView;
            }

        }

        //给三图赋值
        @NonNull
        private View setDataToThreeView(View converView,NewListData.DataBean.NewsBean bean) {

            ViewHolderThree holderThree =null;
            if(converView == null){
                converView=View.inflate(getContext(),R.layout.item_new_three,null);

                holderThree = new ViewHolderThree();
                holderThree. title=converView.findViewById(R.id.item_title);
                holderThree. date=converView.findViewById(R.id.item_date);
                holderThree. image=converView.findViewById(R.id.item_image);
                holderThree. image1=converView.findViewById(R.id.item_image1);
                holderThree. image2=converView.findViewById(R.id.item_image2);
                converView.setTag(holderThree);//将视图与holder绑定
            }else{
                holderThree= (ViewHolderThree) converView.getTag();
            }


            //C:赋值
            holderThree.title.setText(bean.title);
            holderThree.date.setText(bean.pubdate);

            String url="http://192.168.88.101:8080/"+bean.listimage;
            String url1="http://192.168.88.101:8080/"+bean.listimage1;
            String url2="http://192.168.88.101:8080/"+bean.listimage2;
            holderThree.image.setScaleType(ImageView.ScaleType.CENTER_CROP);//放大裁切
            holderThree.image1.setScaleType(ImageView.ScaleType.CENTER_CROP);//放大裁切
            holderThree.image2.setScaleType(ImageView.ScaleType.CENTER_CROP);//放大裁切
            Glide.with(getContext()).load(url).into(holderThree.image);
            Glide.with(getContext()).load(url1).into(holderThree.image1);
            Glide.with(getContext()).load(url2).into(holderThree.image2);
            //D:convertView优化
            return converView;
        }

        //给一图赋值
        @NonNull
        private View setDataToOneView(View convertView, NewListData.DataBean.NewsBean bean) {
            ViewHolderOne holderOne=null;
            //一图
            if(convertView == null){//表示视图不是重用。
                convertView = View.inflate(getContext(),R.layout.item_new_one,null);
                // find,set..
                holderOne=new ViewHolderOne();
                holderOne. title=convertView.findViewById(R.id.item_title);
                holderOne. date=convertView.findViewById(R.id.item_date);
                holderOne. image=convertView.findViewById(R.id.item_image);
                convertView.setTag(holderOne);//将视图与holder绑定在一起。
            }else{
                //重用， find,set..
                // convertView!=null
                holderOne= (ViewHolderOne) convertView.getTag();
            }


            holderOne.title.setText(bean.title);
            holderOne.date.setText(bean.pubdate);
            String url="http://192.168.88.101:8080/"+bean.listimage;
            holderOne.image.setScaleType(ImageView.ScaleType.CENTER_CROP);//放大裁切
            Glide.with(getContext()).load(url).into(holderOne.image);
            return convertView;
        }

    }
}
