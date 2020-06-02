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
import com.xugong.jrtt.bean.NewTypeData;
import com.xugong.jrtt.bean.ResponseData;
import com.xugong.jrtt.fragment.sub.Page1Fragment;
import com.xugong.jrtt.fragment.sub.Page2Fragment;
import com.xugong.jrtt.fragment.sub.Page3Fragment;
import com.xugong.jrtt.fragment.sub.Page4Fragment;
import com.xugong.jrtt.net.MyApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//二级页面
public class HomeFragment extends BaseFragment {
    //3,定义了适配器
    class MyPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> pages=new ArrayList<>();
        private List<String> titles=new ArrayList<>();
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            pages.add(new Page1Fragment());//0
            pages.add(new Page2Fragment());//1
            pages.add(new Page3Fragment());//2
            pages.add(new Page4Fragment());//3
            titles.add("Page1Fragment");
            titles.add("Page2Fragment");
            titles.add("Page3Fragment");
            titles.add("Page4Fragment");
        }

        @Override
        public Fragment getItem(int i) {//返回页面内容
            return pages.get(i);//
        }

        @Override
        public int getCount() {//返回页面的数量
            return pages.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }


    public View getMyView() {
        //1,布局ViewPager
        //2,加载
        View view=View.inflate(getActivity(), R.layout.fragment_home,null);
        ViewPager viewPager=view.findViewById(R.id.viewpager);

        //getData()
        getData();
        //4,适配器
        MyPagerAdapter myPager=new MyPagerAdapter(getFragmentManager());
        viewPager.setAdapter(myPager);
        //5,初始指示器
        TabLayout tablayout = view.findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(viewPager);//->viewpager-->adapter->getPageTitle
        return view;
    }

    private List<NewTypeData> datas=new ArrayList<>();
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

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                //获取数据失败的
                System.out.println("---");
            }
        });

    }
}
