package com.xugong.jrtt.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.xugong.jrtt.R;
import com.xugong.jrtt.bean.NewListData;
import com.xugong.jrtt.fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
      /*  //4:通过get获取数据对象
        NewListData.DataBean.NewsBean bean= (NewListData.DataBean.NewsBean) getIntent().getSerializableExtra("bean");
        //NewListFragment fragment=new NewListFragment();
        //5:new后面跟着一个有参构造方法
        DetailFragment fragment=new DetailFragment(bean);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();*/
    }
}
