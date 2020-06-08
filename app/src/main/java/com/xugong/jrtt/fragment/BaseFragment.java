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
import android.widget.EditText;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//1：定义成抽象类，必须继承该来使用
public abstract class BaseFragment extends Fragment {
    //3:修改给子类使用的成员
    protected View fragmentView  = null;
    //2:成员变量与成员方法

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //判断
        if(fragmentView == null){
            fragmentView = getMyView(); //缓存
        }else{
            //清除当前重用的view的parent
            ViewGroup viewGroup= (ViewGroup) fragmentView.getParent();
            if(viewGroup!=null){
                viewGroup.removeView(fragmentView);
            }
        }

        System.out.println("onCreateView----------------");
        return fragmentView;//--ViewPager  addView()

    }
    protected View getMyView() {
        EditText textView= new EditText(getActivity());
        textView.setBackgroundColor(Color.GREEN);
        textView.setText("我是输入框");
        textView.setTextSize(22);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    //# 5.地址

    public static String HOST="http://192.168.88.101:8080/";
    public static  String BASEURL="http://192.168.88.101:8080/jrtt/";
    protected Retrofit retrofit;
    public BaseFragment() {
        //http://192.168.1.102:8080/jrtt/10007/list_1.json
        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
}
