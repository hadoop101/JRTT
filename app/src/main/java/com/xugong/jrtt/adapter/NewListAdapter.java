package com.xugong.jrtt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.j256.ormlite.dao.Dao;
import com.xugong.jrtt.R;
import com.xugong.jrtt.bean.NewListData;
import com.xugong.jrtt.db.MyDbHelper;
import com.xugong.jrtt.db.NewInfo;
import com.xugong.jrtt.fragment.BaseFragment;
import com.xugong.jrtt.fragment.sub.NewListFragment;

import java.sql.SQLException;
import java.util.List;



//8:定义适配器
public class NewListAdapter extends BaseAdapter {
    private List<NewListData.DataBean.NewsBean> listData;

    public List<NewListData.DataBean.NewsBean> getListData() {
        return listData;
    }



    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //构造方法
    public NewListAdapter(Context context,List<NewListData.DataBean.NewsBean> list){
        listData=list;
        this.context=context;
    }
    @Override
    public int getCount() {//行数
        return listData.size();
    }

    @Override
    public int getViewTypeCount() {//两种视频，一种是一图，另一种是三图
        return 2;
    }

    @Override
    public int getItemViewType(int position) {//哪一个是三图的，哪一行是一图
        NewListData.DataBean.NewsBean bean = listData.get(position);
        if(bean.type==0){
            //一图
            return 0;//R.layout.item_new_one.xml
        }else {
            //三图
            return 1;//R.layout.item_new_three.xml
        }
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //A:获取数据
        NewListData.DataBean.NewsBean bean = listData.get(position);


        boolean isExist =false;
        //B:返回视图
        //判断当前视图的类型是一图，还是三图
        int type=getItemViewType(position);
        if(type==0){
            convertView = setDataToOneView(convertView, bean,isExist);
            //C:赋值
            return convertView;
        }else{//1
            //A:数据
            //B:控件
            convertView = setDataToThreeView(convertView,bean,isExist);
            return convertView;
        }

    }


    //给三图赋值
    @NonNull
    private View setDataToThreeView(View converView, NewListData.DataBean.NewsBean bean, boolean isExist) {

        ViewHolderThree holderThree =null;
        if(converView == null){
            converView=View.inflate(getContext(),R.layout.item_new_three,null);

            holderThree = new ViewHolderThree();
            holderThree. title=converView.findViewById(R.id.item_title);
            holderThree. date=converView.findViewById(R.id.item_date);
            holderThree. image=converView.findViewById(R.id.item_image);
            holderThree. image1=converView.findViewById(R.id.item_image1);
            holderThree. image2=converView.findViewById(R.id.item_image2);
            converView.setTag(holderThree);//将视图与holder绑定
        }else{
            holderThree= (ViewHolderThree) converView.getTag();
        }


        //C:赋值
        holderThree.title.setText(bean.title);
        holderThree.date.setText(bean.pubdate);

        String url=BaseFragment.HOST+bean.listimage;
        String url1=BaseFragment.HOST+bean.listimage1;
        String url2=BaseFragment.HOST+bean.listimage2;
        holderThree.image.setScaleType(ImageView.ScaleType.CENTER_CROP);//放大裁切
        holderThree.image1.setScaleType(ImageView.ScaleType.CENTER_CROP);//放大裁切
        holderThree.image2.setScaleType(ImageView.ScaleType.CENTER_CROP);//放大裁切
        Glide.with(getContext()).load(url).into(holderThree.image);
        Glide.with(getContext()).load(url1).into(holderThree.image1);
        Glide.with(getContext()).load(url2).into(holderThree.image2);


        //9.5根据是否存在于数据库表， 来调整标题与日期的颜色
        holderThree.title.setTextColor(isExist?Color.GRAY:Color.BLACK);
        holderThree.date.setTextColor(isExist?Color.GRAY:Color.BLACK);

        //D:convertView优化
        return converView;
    }

    //给一图赋值
    @NonNull
    private View setDataToOneView(View convertView, NewListData.DataBean.NewsBean bean, boolean isExist) {
        ViewHolderOne holderOne=null;
        //一图
        if(convertView == null){//表示视图不是重用。
            convertView = View.inflate(getContext(),R.layout.item_new_one,null);
            // find,set..
            holderOne=new ViewHolderOne();
            holderOne. title=convertView.findViewById(R.id.item_title);
            holderOne. date=convertView.findViewById(R.id.item_date);
            holderOne. image=convertView.findViewById(R.id.item_image);
            convertView.setTag(holderOne);//将视图与holder绑定在一起。
        }else{
            //重用， find,set..
            // convertView!=null
            holderOne= (ViewHolderOne) convertView.getTag();
        }


        holderOne.title.setText(bean.title);
        holderOne.date.setText(bean.pubdate);
        String url=BaseFragment.HOST+bean.listimage;
        holderOne.image.setScaleType(ImageView.ScaleType.CENTER_CROP);//放大裁切
        Glide.with(getContext()).load(url).into(holderOne.image);

        //9.4根据是否存在于数据库表， 来调整标题与日期的颜色
        holderOne.title.setTextColor(isExist ? Color.GRAY:Color.BLACK);
        holderOne.date.setTextColor(isExist ? Color.GRAY:Color.BLACK);
        return convertView;
    }

}