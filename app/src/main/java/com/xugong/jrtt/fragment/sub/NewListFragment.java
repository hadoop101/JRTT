package com.xugong.jrtt.fragment.sub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.j256.ormlite.dao.Dao;
import com.xugong.jrtt.R;
import com.xugong.jrtt.activity.DetailActivity;
import com.xugong.jrtt.bean.MoreData;
import com.xugong.jrtt.bean.NewListData;
import com.xugong.jrtt.db.MyDbHelper;
import com.xugong.jrtt.db.NewInfo;
import com.xugong.jrtt.fragment.BaseFragment;
import com.xugong.jrtt.net.MyApi;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//1:继承BaseFragment
@SuppressLint("ValidFragment")
public class NewListFragment extends BaseFragment {
    //10.3 定义一个变量保存下一页的url地址
    private String loadMoreUrl = null;

    private String loadFirstUrl = null;
    //# 3. 列表使用传给你的地址去请求数据
    public NewListFragment(String loadFirstUrl) {
        this.loadFirstUrl = loadFirstUrl;
    }

    //2:重写getMyView
    //3:布式列表
    @Override
    protected View getMyView() {
        System.out.println("oncreateView");
        //4：加载
        return View.inflate(getContext(), R.layout.fragment_new_list,null);//参1，上下文，参2，布局
    }

    //5:请求服务端数据

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");


        //# 4.要求获取列表首页的方法，必须根据我指定的地址去获取
        //6:执行请求
        retrofit.create(MyApi.class).getNewList(loadFirstUrl).enqueue(new Callback<NewListData>() {
            @Override
            public void onResponse(Call<NewListData> call, Response<NewListData> response) {
                /*System.out.println(response.body().retcode);
                System.out.println(response.body().data);*/
                setDataToView(response.body().data);
                //10.3保存下一页地址
                loadMoreUrl=response.body().data.more;
            }

            @Override
            public void onFailure(Call<NewListData> call, Throwable t) {

            }
        });//
    }
    //7：显示
    private  NewListAdapter adapter;
    private PullToRefreshListView pullToRefreshListView;
    //10:下拉刷新与滚动加载
    private void setDataToView(NewListData.DataBean data) {
        //7.1查找出控件

        //10.1 判断控件是否为空，为空才初始化

        if(pullToRefreshListView == null){
            pullToRefreshListView = fragmentView.findViewById(R.id.pull_listview);
            onlisteners(pullToRefreshListView);
            //10.2 给列表添加滚动与下拉刷新的监听器
            onlistenerspull(pullToRefreshListView);
            //7.2.赋值一个适配器
            adapter=new NewListAdapter(data.news);
            pullToRefreshListView.setAdapter(adapter);
        }

    }

    private void onlistenerspull(PullToRefreshListView pullToRefreshListView) {
        //10.3 设置模式为BOTH
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
                //下拉
               //请求服务端
                retrofit.create(MyApi.class).getNewList(loadFirstUrl).enqueue(new Callback<NewListData>() {
                    @Override
                    public void onResponse(Call<NewListData> call, Response<NewListData> response) {
                        //10.3保存下一页地址
                        loadMoreUrl=response.body().data.more;
                        //请求成功
                        //10.4:请求到服务端数据之后，先清空集合，再添加数据，再刷新列表，关闭等待
                        if(adapter!=null){
                            adapter.getListData().clear();//清空
                            List<NewListData.DataBean.NewsBean> newsBeanList = response.body().data.news;
                            adapter.getListData().addAll(newsBeanList);//添加数据，为什么使用addAll
                            //多个元素要加入到当前集合使用addAll

                            adapter.notifyDataSetChanged();//整体刷新

                            refreshView.onRefreshComplete();//关闭等待视图
                            Toast.makeText(getContext(), "下拉刷新成功", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<NewListData> call, Throwable t) {
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
                //10.4 滚动加载需要使用loadMoreUrl地址取得下一页数据
                Toast.makeText(getContext(), "滚动", Toast.LENGTH_SHORT).show();
                //10.5 在MyApi中定义接口方法，定义url与解析的类型MoreData
                retrofit.create(MyApi.class).getMoreData(loadMoreUrl).enqueue(new Callback<MoreData>() {
                    @Override
                    public void onResponse(Call<MoreData> call, Response<MoreData> response) {
                        //1:不清空集合
                        if(adapter!=null){
                            //2:直接添加到当前集合
                            adapter.getListData().addAll(response.body().data.news);
                            //3:刷新列表，关闭等待
                            adapter.notifyDataSetChanged();
                            //4:关闭底部刷新
                            refreshView.onRefreshComplete();

                        }


                        Toast.makeText(getContext(), "下拉刷新成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MoreData> call, Throwable t) {
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //9:监听点击

    private void onlisteners(PullToRefreshListView pullToRefreshListView) {
        //9.1点击监听器
        //ListView它的第一条数据的position是0，第二条是1
        //pullToRefreshListView  0位置被刷新视图占用，所以第1条数据从1 position
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int position当前点击的位置
                NewListData.DataBean.NewsBean bean = adapter.getListData().get(position-1);

                //列表页面跳到详情页面
                goToNewDetailPage(bean);

                //Toast.makeText(getContext(), "id="+bean.id, Toast.LENGTH_SHORT).show();
                //9.2使用数据库保存起来
                MyDbHelper helper=new MyDbHelper(getContext());
                try {
                    //dao是有增删改查方法的对象
                    Dao<NewInfo, Integer> dao = helper.getDao(NewInfo.class);
                    //保存
                    List<NewInfo> list =  dao.queryForEq("newId",bean.id);//select 语句
                    if(list==null||list.size()==0){
                        dao.create(new NewInfo(bean.id));//执行一条insert 语句
                    }

                    List<NewInfo> newInfos = dao.queryForAll();
                    System.out.println(newInfos.toString());


                    //9.6 调用getView刷新列表一行，如果需要刷新整个表，调adapter.notifyDataSetChanged()
                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //
    private void goToNewDetailPage(NewListData.DataBean.NewsBean bean) {
            //1:打开页面
        Intent intent=new Intent(getContext(),DetailActivity.class);//参1，当前页面 参2目标页面
        intent.putExtra("bean",bean);//需要对象的类实现序列化
        // public static class NewsBean implements Serializable
            //2:传递参数
        getActivity().startActivity(intent);
            //3:收藏
    }

    class ViewHolderOne{
        public TextView title;
        public TextView date;
        public ImageView image;
    }
    class ViewHolderThree extends ViewHolderOne{
        public ImageView image1;
        public ImageView image2;
    }
    //8:定义适配器
    class NewListAdapter extends BaseAdapter{
        private List<NewListData.DataBean.NewsBean> listData;

        public List<NewListData.DataBean.NewsBean> getListData() {
            return listData;
        }



        //构造方法
        public NewListAdapter(List<NewListData.DataBean.NewsBean> list){
            listData=list;
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

            //查询数据库是否存在id  true,false
            boolean isExist= false;
            try {
                isExist = queryIdInDb(bean.id);
            } catch (SQLException e) {
                e.printStackTrace();
            }

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

        //9.3 查询当前新闻是否存在数据库中
        private MyDbHelper myDbHelper;
        private Dao<NewInfo, Integer> dao;
        private boolean queryIdInDb(int id) throws SQLException {
            if(myDbHelper==null){
                myDbHelper=new MyDbHelper(getContext());
                dao = myDbHelper.getDao(NewInfo.class);
            }

            List<NewInfo> list = dao.queryForEq("newId", id);
            if(list==null||list.size()==0){
                return false;//表示不存在数据库中，就是没点过的新闻
            }else{
                return true;
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

            String url=HOST+bean.listimage;
            String url1=HOST+bean.listimage1;
            String url2=HOST+bean.listimage2;
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
            String url=HOST+bean.listimage;
            holderOne.image.setScaleType(ImageView.ScaleType.CENTER_CROP);//放大裁切
            Glide.with(getContext()).load(url).into(holderOne.image);

            //9.4根据是否存在于数据库表， 来调整标题与日期的颜色
            holderOne.title.setTextColor(isExist ? Color.GRAY:Color.BLACK);
            holderOne.date.setTextColor(isExist ? Color.GRAY:Color.BLACK);
            return convertView;
        }

    }
}
