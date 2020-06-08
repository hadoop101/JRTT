package com.xugong.jrtt.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //3:加载网页数据

        loadWebView();

    }

    private void loadWebView() {
        //webView发请求加载网页数据
        webview.loadUrl(BASEURL+"/10007/724D6A55496A11726628.html");

        //设置Client 一个防止浏览器app自动弹出，
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new MyWebChromeClient());//另一个是可以获取加载进度

        webview.getSettings().setJavaScriptEnabled(true);//设置js可用

       /* webview.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
        webview.getSettings().setTextZoom(200);*/

    }
    //4:重写onProgressChanged的方法
    class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            System.out.println("进度"+newProgress);
            if(newProgress != 100){
                progress.setMax(100);
                progress.setProgress(newProgress);
                progress.setVisibility(View.VISIBLE);//显示
            }else{
                progress.setVisibility(View.INVISIBLE);//隐藏
            }
        }
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
                onBackClick();
                break;
            case R.id.font:
                onFontClick();



                break;
            case R.id.share:
                break;
            case R.id.save:
                break;
        }
    }

    private String[] levels={"小","中","大","超大"};
    private int choiceItem = 0;
    private void onFontClick() {
        //5：包含webView的设置参数
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //标题
        builder.setTitle("标题");
        builder.setSingleChoiceItems(levels, choiceItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("which:"+which);
                choiceItem=which;
                //dialog.dismiss();//关闭
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(choiceItem==0){
                    webview.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
                }else if(choiceItem==1){
                    webview.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
                }else if(choiceItem==2){
                    webview.getSettings().setTextSize(WebSettings.TextSize.LARGER);
                }else if(choiceItem==3){
                    webview.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
                }

                dialog.dismiss();
            }
        });//确认 参1 按钮文字 参2事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });//取消 参1 按钮文字 参2事件
        //单选  参1 选项数组 参2，默认选中 参3，处理事件
        AlertDialog alertDialog = builder.create();
        alertDialog.show();//打开


    }

    //3:编写事件
    private void onBackClick() {
        getActivity().finish();
    }
}
