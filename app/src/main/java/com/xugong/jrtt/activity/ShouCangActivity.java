package com.xugong.jrtt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;
import com.xugong.jrtt.R;
import com.xugong.jrtt.adapter.NewListAdapter;
import com.xugong.jrtt.bean.NewListData;
import com.xugong.jrtt.db.MyDbHelper;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShouCangActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang);
        ButterKnife.bind(this);


        //1:收藏列表：初始化

        //2:查询数据
        queryDb();

        //3:显示
        setDataToList();

    }

    private List<NewListData.DataBean.NewsBean> list;

    private void queryDb() {
        MyDbHelper myDbHelper=new MyDbHelper(this);
        try {
            Dao<NewListData.DataBean.NewsBean, Integer> dao = myDbHelper.getDao(NewListData.DataBean.NewsBean.class);

             list = dao.queryForAll();
            System.out.println("我的数据："+list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setDataToList() {

        NewListAdapter adapter=new NewListAdapter(this,list);
        listview.setAdapter(adapter);

    }

}
