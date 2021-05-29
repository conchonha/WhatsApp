package com.whatsapp.whatsappexample.ui.page.addfriens;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.text.Editable;
import android.view.View;

import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.app.MyApplication;
import com.whatsapp.whatsappexample.base.BaseActivity;
import com.whatsapp.whatsappexample.databinding.ActivityAddFriendsBinding;
import com.whatsapp.whatsappexample.model.TextWatcherCustom;
import com.whatsapp.whatsappexample.ui.adapter.AdapterAddFriend;


public class AddFriendsActivity extends BaseActivity implements View.OnClickListener {
    private ActivityAddFriendsBinding mBinding;
    private AddFriendsViewModel mAddFriendsViewModel;
    private AdapterAddFriend mAdapterAddFriend;

    @Override
    protected View getContentView() {
        mBinding = ActivityAddFriendsBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void onListenerClicked() {
        mBinding.floatingButtonAccept.setOnClickListener(this);
    }

    @Override
    protected void onInit() {
        mAdapterAddFriend = new AdapterAddFriend();
        mBinding.recyclerviewListFriends.setAdapter(mAdapterAddFriend);
        mBinding.recyclerviewListFriends.setLayoutManager(new LinearLayoutManager(this));

        //duong ke ngang giua cac item recyclerview
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mBinding.recyclerviewListFriends.addItemDecoration(itemDecoration);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onInitViewModel() {
        mAddFriendsViewModel = new ViewModelProvider(this, MyApplication.factory).get(AddFriendsViewModel.class);

        mAddFriendsViewModel.getListUserNoFriend().observe(this, listUsers -> {

            mAdapterAddFriend.updateData(listUsers);
            mBinding.edttimkim.addTextChangedListener(new TextWatcherCustom() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void afterTextChanged(Editable s) {
                    super.afterTextChanged(s);
                    mAdapterAddFriend.updateData(mAddFriendsViewModel.searchList(listUsers, s.toString()));
                }
            });

        });

        mAddFriendsViewModel.getCheckDialog().observe(this, bool -> {
            if (bool) {
                showProgressLoadding();
            } else {
                dismisProgressDialog();
            }
        });

        mAddFriendsViewModel.getLiveErrorSendPendingFriend().observe(this, this::showAlertDialog);

        mAddFriendsViewModel.getLiveCountSendPendingFriend().observe(this, s -> {
            if (s >= mAdapterAddFriend.getListUserAccept().size()) {
                mAddFriendsViewModel.setDialog(false);
                showToast(getString(R.string.lbl_send_pending_friend_accept));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingButtonAccept:
                mAddFriendsViewModel.sendFriendRequestPending(mAdapterAddFriend.getListUserAccept());
                break;
        }
    }
}