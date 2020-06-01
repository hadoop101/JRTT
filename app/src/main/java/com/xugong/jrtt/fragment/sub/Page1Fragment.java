package com.xugong.jrtt.fragment.sub;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class Page1Fragment extends Fragment {
    View view  = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //判断
            if(view == null){
                 view = getMyView(); //缓存
            }else{
                //清除当前重用的view的parent
                ViewGroup viewGroup= (ViewGroup) view.getParent();
                if(viewGroup!=null){
                   viewGroup.removeView(view);
                }
            }

            System.out.println("onCreateView----------------");
            return view;//--ViewPager  addView()

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView----------------");
    }



    private View getMyView() {
        EditText textView= new EditText(getActivity());
        textView.setBackgroundColor(Color.GREEN);
        textView.setText("P1");
        textView.setTextSize(22);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
