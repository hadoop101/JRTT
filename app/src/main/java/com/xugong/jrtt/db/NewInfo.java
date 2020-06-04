package com.xugong.jrtt.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

 //3:定义数据库中的新闻实体类
@DatabaseTable(tableName = "news_table")
public class NewInfo{
    @DatabaseField(columnName = "id",generatedId = true) //自动生成
    public Integer id;
    @DatabaseField(columnName = "newId")
    public Integer newId;//在表中有这个newId，表示已被读过，否则未读

    public NewInfo(Integer newId) {//自己调用
        this.newId = newId;
    }
    public NewInfo() {//框架调用

    }

     @Override
     public String toString() {
         return "NewInfo{" +
                 "id=" + id +
                 ", newId=" + newId +
                 '}';
     }
 }