package com.xugong.jrtt.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xugong.jrtt.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//1:布局
//2:加载布局
public class DetailFragment extends BaseFragment {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.font)
    ImageView font;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.save)
    ImageView save;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.webview)
    WebView webview;
    Unbinder unbinder;

    //...
    @Override
    protected View getMyView() {
        View view = View.inflate(getContext(), R.layout.fragment_detail, null);
        //find...
        //set
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back, R.id.font, R.id.share, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:

                break;
            case R.id.font:
                break;
            case R.id.share:
                break;
            case R.id.save:
                break;
        }
    }
}
