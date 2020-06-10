package xugong.com.test03test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

//1:注释代码

/*
 *多行
 */
public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       try{
           System.out.println(1/1);
           System.out.println(1/2);
           System.out.println(1/0);
           System.out.println(1/3);
           System.out.println(1/4);
       }catch (Exception e){
            e.printStackTrace();
       }

    }
}
