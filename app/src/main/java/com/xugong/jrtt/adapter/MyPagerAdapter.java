package com.xugong.jrtt.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xugong.jrtt.fragment.sub.Page1Fragment;
import com.xugong.jrtt.fragment.sub.Page2Fragment;
import com.xugong.jrtt.fragment.sub.Page3Fragment;
import com.xugong.jrtt.fragment.sub.Page4Fragment;

import java.util.ArrayList;
import java.util.List;

public   //3,定义了适配器
class MyPagerAdapter extends FragmentPagerAdapter {
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