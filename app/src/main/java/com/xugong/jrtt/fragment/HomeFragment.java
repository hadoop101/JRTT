package com.xugong.jrtt.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

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
         linstenChanged();
        //4,适配器
        return view;
    }

    private MyNewTypeAdapter adapter;
    //1，事件监听
    private void linstenChanged() {
        //1.1，添加页面监听器
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {//整个页面切换完成
                //1.2 根据当前页面显示有屏幕上的编程，获取对应的数据
                ResponseData.DataBean dataBean = adapter.getDatas().get(position);
                Toast.makeText(getContext(), dataBean.title+" " +dataBean.url, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //1:retrofit    当前是子类，继承父类BaseFragment

        //2:重点是发请求获取服务的数据

     //3:将json数据处理一下，使用插件gsonformat生成javaBean
     retrofit.create(MyApi.class).getType().enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                //处理获取数据成功
                ResponseData data = response.body();
                System.out.println(data.data);
                System.out.println(data.retcode);

                //----------------
                adapter=new MyNewTypeAdapter(getFragmentManager(),data.data);
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

        public List<ResponseData.DataBean> getDatas() {
            return datas;
        }



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
