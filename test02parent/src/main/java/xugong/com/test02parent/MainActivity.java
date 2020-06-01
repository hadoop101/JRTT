package xugong.com.test02parent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=new Button(this);
        button.setText("我是一个按钮");

        LinearLayout layout=findViewById(R.id.parent);
        layout.addView(button);//parent=linearlayout






        //将一个视图从parent中删除 ，才能重用
        ViewGroup viewGroup= (ViewGroup) button.getParent();
        if(viewGroup!=null){
            viewGroup.removeView(button);
        }
       // layout.removeView(button);//parent=null
        layout.addView(button);//parent!=null ,再执行addView
        System.out.println("parent="+button.getParent());
    }
}
