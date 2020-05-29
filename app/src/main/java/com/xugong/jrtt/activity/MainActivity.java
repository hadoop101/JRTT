package com.xugong.jrtt.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.xugong.jrtt.R;
import com.xugong.jrtt.fragment.HomeFragment;
import com.xugong.jrtt.fragment.PicFragment;
import com.xugong.jrtt.fragment.UserFragment;
import com.xugong.jrtt.fragment.VideoFragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1，添加点击事件
        listenRadioButtons();
        //2,实始化四个页面
        initPages();
        //3,进来设置默认显示HomeFragment
        setContentShowFragment(R.id.rb1);
    }

    //3.1
    private void setContentShowFragment(int checkedId) {
        // Toast.makeText(MainActivity.this, "切换", Toast.LENGTH_SHORT).show();
        Fragment fragment=map.get(checkedId);
        //2.3 通过Fragment的管理者将fragment显示在指定的布局
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,fragment);//替换
        // 即 判断如果布局上有内容就替换 ，如果没有内容就添加
        transaction.commit();//必须提交，页面才会有更新
    }

    private HashMap<Integer,Fragment> map;
    private void initPages() {
        //2.1
        HomeFragment homeFragment=new HomeFragment();
        VideoFragment videoFragment=new VideoFragment();
        PicFragment picFragment=new PicFragment();
        UserFragment userFragment=new UserFragment();

        //2.2 管理四个页面
        map=new HashMap<>();//r1->HomeFragment  r2->VideoFragment
        map.put(R.id.rb1,homeFragment);
        map.put(R.id.rb2,videoFragment);
        map.put(R.id.rb3,picFragment);
        map.put(R.id.rb4,userFragment);
    }

    private void listenRadioButtons() {
        //1.1 找到RadioGroup
        RadioGroup radioGroup=findViewById(R.id.radio_group);
        //1.2 添加监听器
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //1.3切换
                //3.2
                setContentShowFragment(checkedId);

            }
        });
    }
}
