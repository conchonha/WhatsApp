package com.whatsapp.whatsappexample.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsapp.whatsappexample.base.BaseFragment;
import com.whatsapp.whatsappexample.databinding.FragmentCallsBinding;

public class CallsFragment extends BaseFragment {
    private static CallsFragment instance;
    private FragmentCallsBinding mBinding;

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentCallsBinding.inflate(inflater,container,false);
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

    public static CallsFragment getInstance(){
        if(instance == null){
            instance = new CallsFragment();
        }
        return instance;
    }

}
