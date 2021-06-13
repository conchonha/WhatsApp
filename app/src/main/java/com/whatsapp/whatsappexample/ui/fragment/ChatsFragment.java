package com.whatsapp.whatsappexample.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.app.MyApplication;
import com.whatsapp.whatsappexample.base.BaseFragment;
import com.whatsapp.whatsappexample.callback.onClickItemFragmentChat;
import com.whatsapp.whatsappexample.databinding.FragmentChatsBinding;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.ui.adapter.AdapterItemUserFragmentChats;
import com.whatsapp.whatsappexample.ui.page.addfriens.AddFriendsActivity;
import com.whatsapp.whatsappexample.ui.page.chat_detail.ChatDetailActivity;
import com.whatsapp.whatsappexample.ui.page.home.HomeViewModel;
import com.whatsapp.whatsappexample.utils.Contains;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends BaseFragment implements View.OnClickListener , onClickItemFragmentChat {
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
        HomeViewModel mHomeViewModel = new ViewModelProvider(this, MyApplication.factory).
                get(HomeViewModel.class);
        mHomeViewModel.getListFriends().observe(this,list -> {
            mAdapter.updateList((ArrayList<User>) list,this::onClickItem);
        });
    }

    @Override
    protected void init() {
        mAdapter = new AdapterItemUserFragmentChats();
        mBinding.recyclerFragmentChats.setAdapter(mAdapter);
        mBinding.recyclerFragmentChats.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void onListenerClicked() {
        mBinding.floatingButton.setOnClickListener(this);
    }

    public static ChatsFragment getInstance(){
        if(instance == null){
            instance = new ChatsFragment();
        }
        return instance;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingButton:
                startActivity(new Intent(getActivity(), AddFriendsActivity.class));
                break;
        }
    }

    @Override
    public void onClickItem(int position, List<User> list) {
        startActivity(new Intent(getActivity(), ChatDetailActivity.class).
                putExtra(Contains.keyUser,list.get(position)));
    }
}
