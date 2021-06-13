package com.whatsapp.whatsappexample.ui.page.accept_friends;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.whatsapp.whatsappexample.app.MyApplication;
import com.whatsapp.whatsappexample.base.BaseActivity;
import com.whatsapp.whatsappexample.callback.OnclickAdapterAcceptFriend;
import com.whatsapp.whatsappexample.databinding.ActivityAcceptFriendBinding;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.ui.adapter.AdapterAcceptFriend;

public class AcceptFriendActivity extends BaseActivity implements OnclickAdapterAcceptFriend {
    private ActivityAcceptFriendBinding mBinding;
    private AcceptFriendViewModel mAcceptFriendViewModel;
    private final String TAG = AcceptFriendActivity.class.getName();
    private AdapterAcceptFriend mAdapter;

    @Override
    protected View getContentView() {
        mBinding = ActivityAcceptFriendBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void onListenerClicked() {

    }

    @Override
    protected void onInit() {
        mAdapter = new AdapterAcceptFriend();
        mBinding.recyclerAccept.setAdapter(mAdapter);
        mBinding.recyclerAccept.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerAccept.setHasFixedSize(true);
    }

    @Override
    protected void onInitViewModel() {
        mAcceptFriendViewModel = new ViewModelProvider(this, MyApplication.factory).get(AcceptFriendViewModel.class);

        mAcceptFriendViewModel.getCheckDialog().observe(this,aBoolean -> {
           if(aBoolean){
               showProgressLoadding();
               return;
           }
           dismisProgressDialog();
        });

        mAcceptFriendViewModel.countNumberAcceptFriend().observe(this,list -> {
            Log.d(TAG, "onInitViewModel: "+list.size());
            mAdapter.updateData(list,this);
        });
    }

    @Override
    public void onAccept(User user, int Position) {
        mAcceptFriendViewModel.onAcceptPendingFriends(user);
    }

    @Override
    public void onRemove(User user, int Position) {
        mAcceptFriendViewModel.onRemovePendingFriends(user.getmKey());
    }
}