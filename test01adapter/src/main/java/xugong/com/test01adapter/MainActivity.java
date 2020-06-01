package xugong.com.test01adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   class MyAdapter extends QuickAdapter<String>{

       public MyAdapter(Context context, int layoutResId, List<String> data) {
           super(context, layoutResId, data);
       }

       @Override
       protected void convert(BaseAdapterHelper helper, String item) {//参1，处理 viewholder 参2 表示列表中数据
           //取数据
           //将数据赋值给视图
           helper.setText(R.id.textview,item);//参1，视图中的viewId 参2，数据
       }
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
        MyAdapter myAdapter=new MyAdapter(MainActivity.this,R.layout.item,list);
        listView.setAdapter(myAdapter);

    }
}
