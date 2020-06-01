package com.xugong.jrtt.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xugong.jrtt.R;
import com.xugong.jrtt.fragment.sub.Page1Fragment;
import com.xugong.jrtt.fragment.sub.Page2Fragment;
import com.xugong.jrtt.fragment.sub.Page3Fragment;
import com.xugong.jrtt.fragment.sub.Page4Fragment;

import java.util.ArrayList;
import java.util.List;

//二级页面
public class HomeFragment extends Fragment {
    //3,定义了适配器
    class MyPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> pages=new ArrayList<>();
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            pages.add(new Page1Fragment());//0
            pages.add(new Page2Fragment());//1
            pages.add(new Page3Fragment());//2
            pages.add(new Page4Fragment());//3
        }

        @Override
        public Fragment getItem(int i) {//返回页面内容
            return pages.get(i);//
        }

        @Override
        public int getCount() {//返回页面的数量
            return pages.size();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = getMyView();
            return view;

    }

    private View getMyView() {
        //1,布局ViewPager
        //2,加载
        View view=View.inflate(getActivity(), R.layout.fragment_home,null);
        ViewPager viewPager=view.findViewById(R.id.viewpager);
        //4,适配器
        MyPagerAdapter myPager=new MyPagerAdapter(getFragmentManager());
        viewPager.setAdapter(myPager);
        return view;
    }
}
