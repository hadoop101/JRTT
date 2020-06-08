package com.xugong.jrtt.fragment;

import android.view.View;

import com.xugong.jrtt.R;

//1:布局
//2:加载布局
public class DetailFragment extends BaseFragment {
    @Override
    protected View getMyView() {
        return View.inflate(getContext(), R.layout.fragment_detail,null);
    }
}
