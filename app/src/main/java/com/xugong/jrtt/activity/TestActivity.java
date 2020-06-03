package com.xugong.jrtt.activity;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.xugong.jrtt.R;
import com.xugong.jrtt.fragment.HomeFragment;
import com.xugong.jrtt.fragment.sub.NewListFragment;

public class TestActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        NewListFragment fragment=new NewListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }
}
