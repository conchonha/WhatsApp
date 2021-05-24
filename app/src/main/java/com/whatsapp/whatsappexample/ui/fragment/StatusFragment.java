package com.whatsapp.whatsappexample.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsapp.whatsappexample.base.BaseFragment;
import com.whatsapp.whatsappexample.databinding.FragmentStatusBinding;

public class StatusFragment extends BaseFragment {
    private static StatusFragment instance;
    private FragmentStatusBinding mBinding;

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentStatusBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void onListenerClicked() {

    }

    public static StatusFragment getInstance(){
        if(instance == null){
            instance = new StatusFragment();
        }
        return instance;
    }
}
