package com.whatsapp.whatsappexample.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.whatsapp.whatsappexample.base.BaseFragment;
import com.whatsapp.whatsappexample.databinding.FragmentChatsBinding;
import com.whatsapp.whatsappexample.ui.adapter.AdapterItemUserFragmentChats;

public class ChatsFragment extends BaseFragment {
    private static ChatsFragment instance;
    private FragmentChatsBinding mBinding;
    private AdapterItemUserFragmentChats mAdapter;

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentChatsBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void init() {
        mAdapter = new AdapterItemUserFragmentChats();
        mBinding.recyclerFragmentChats.setAdapter(mAdapter);
        mBinding.recyclerFragmentChats.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void onListenerClicked() {

    }

    public static ChatsFragment getInstance(){
        if(instance == null){
            instance = new ChatsFragment();
        }
        return instance;
    }
}
