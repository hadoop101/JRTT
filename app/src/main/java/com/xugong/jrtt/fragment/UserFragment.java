package com.xugong.jrtt.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xugong.jrtt.R;
import com.xugong.jrtt.activity.ShouCangActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserFragment extends BaseFragment {
    @BindView(R.id.shoucang)
    Button shoucang;
    Unbinder unbinder;

    @Override
    protected View getMyView() {
        View view = View.inflate(getContext(), R.layout.fragment_user, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.shoucang)
    public void onViewClicked() {
        Intent intent=new Intent(getContext(),ShouCangActivity.class);
        getActivity().startActivity(intent);
    }
}

