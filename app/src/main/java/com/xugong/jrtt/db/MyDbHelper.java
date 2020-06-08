package com.xugong.jrtt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import com.xugong.jrtt.bean.NewListData;


public class MyDbHelper  extends OrmLiteSqliteOpenHelper {
    //1：创建数据库
    public MyDbHelper(Context context) {
        super(context, "new.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //2:创建数据库中的表，保存已读新闻
        try{
            TableUtils.createTable(connectionSource,NewInfo.class);
            //收藏
            TableUtils.createTable(connectionSource,NewListData.DataBean.NewsBean.class);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
