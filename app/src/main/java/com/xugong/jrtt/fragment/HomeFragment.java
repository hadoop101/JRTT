package com.xugong.jrtt.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.xugong.jrtt.R;
import com.xugong.jrtt.bean.ResponseData;
import com.xugong.jrtt.fragment.sub.Page1Fragment;
import com.xugong.jrtt.net.MyApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//二级页面
public class HomeFragment extends BaseFragment {

    private ViewPager viewPager;
    private TabLayout tablayout;
    public View getMyView() {
        //1,布局ViewPager
        //2,加载
        View view=View.inflate(getActivity(), R.layout.fragment_home,null);
         viewPager=view.findViewById(R.id.viewpager);
         tablayout = view.findViewById(R.id.tablayout);
        //getData()
        getData();
        //4,适配器


        return view;
    }



    private void getData() {
        //1:retrofit
        Retrofit retrofit = new Retrofit.Builder()//创建器
                .baseUrl("http://192.168.88.101:8080/jrtt/")//项目路径，以/结束
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();//执行创建
        //2:重点是发请求获取服务的数据
        MyApi myApi=retrofit.create(MyApi.class);
        //3:将json数据处理一下，使用插件gsonformat生成javaBean
        myApi.getType().enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                //处理获取数据成功
                ResponseData data = response.body();
                System.out.println(data.data);
                System.out.println(data.retcode);

                //----------------
                MyNewTypeAdapter adapter=new MyNewTypeAdapter(getFragmentManager(),data.data);
                viewPager.setAdapter(adapter);
                tablayout.setupWithViewPager(viewPager);//->viewpager-->adapter->getPageTitle

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                //获取数据失败的
                System.out.println("---");
            }
        });

    }

    class MyNewTypeAdapter extends  FragmentPagerAdapter{

        //集合可以让我们重用Fragment
        private List<ResponseData.DataBean> datas=new ArrayList<>();
        private List<Fragment> fragments=new ArrayList<>();

        public MyNewTypeAdapter(FragmentManager fm,List<ResponseData.DataBean> list) {
            super(fm);

            datas.addAll(list);//将一个集合中所有的数据加到当前的datas

            for(ResponseData.DataBean item:datas){
                fragments.add(new Page1Fragment());
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return datas.get(position).title;//将标题返回
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }
}
