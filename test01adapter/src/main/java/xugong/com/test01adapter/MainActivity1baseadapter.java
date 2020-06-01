package xugong.com.test01adapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity1baseadapter extends AppCompatActivity {

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list.size();//1000
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        class ViewHolder{
            TextView textView;
        }
        //convertView视图回收器返回的视图
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //取数据
            String data = list.get(position);
            ViewHolder holder =null;
            //准备视图
            if(convertView==null){
                System.out.println("getView: convertView==null");
                //将视图文件加载到内存中，创建视图对象  第一次初始化视图
                convertView= View.inflate(MainActivity1baseadapter.this,R.layout.item,null);//参1，上下文，参2，布局，参3 null

                TextView textView=convertView.findViewById(R.id.textview);

                holder=new ViewHolder();

                holder.textView=textView;
                //将viewHolder与视图绑定
                convertView.setTag(holder);

            }else{//convertView!=null 视图回收器返回旧视图
                //每次显示一个数据的话，就创建一个视图
                System.out.println("getView方法:"+convertView);
                holder= (ViewHolder) convertView.getTag();

            }

            //赋值
            holder.textView.setText(data);
            return convertView;

        }
        //getCount()
        //getView()

    }
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1,准备数据
         list=new ArrayList<>();
        for(int i =0;i<1000;i++){
            list.add("数据"+i);
        }
        //2,查找控件
        ListView listView=findViewById(R.id.listview);

        //3,重创建适配器
        MyAdapter myAdapter=new MyAdapter();
        listView.setAdapter(myAdapter);

    }
}
