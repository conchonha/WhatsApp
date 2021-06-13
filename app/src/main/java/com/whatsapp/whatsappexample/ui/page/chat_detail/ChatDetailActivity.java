package com.whatsapp.whatsappexample.ui.page.chat_detail;


import android.annotation.SuppressLint;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.squareup.picasso.Picasso;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.app.MyApplication;
import com.whatsapp.whatsappexample.base.BaseActivity;
import com.whatsapp.whatsappexample.databinding.ActivityChatDetailBinding;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.ui.adapter.ChatAdapter;
import com.whatsapp.whatsappexample.utils.Contains;

import java.util.Objects;


public class ChatDetailActivity extends BaseActivity implements View.OnClickListener {
    private ActivityChatDetailBinding mBinDing;
    private ChatDetailViewModel mViewModel;
    private final ChatAdapter mAdapter = new ChatAdapter();

    @Override
    protected View getContentView() {
        mBinDing = ActivityChatDetailBinding.inflate(getLayoutInflater());
        return mBinDing.getRoot();
    }

    @Override
    protected void onListenerClicked() {
        mBinDing.imgSend.setOnClickListener(this);
    }

    @Override
    protected void onInit() {
        initActionBar();
        initRecyclerview();
        if(getIntent().hasExtra(Contains.keyUser)){
            User user = (User) getIntent().getSerializableExtra(Contains.keyUser);
            updateUi(user);
            mViewModel.handlingData(user);
        }
    }

    private void initRecyclerview() {
        mBinDing.recyclerChat.setAdapter(mAdapter);
        mBinDing.recyclerChat.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateUi(User user) {
        Picasso.get().load(user.getmProfilePic()).placeholder(R.drawable.ic_user).into(mBinDing.imgAvatar);
        mBinDing.txtName.setText(user.getmUserName());
    }

    private void initActionBar() {
        setSupportActionBar(mBinDing.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinDing.toolbar.setNavigationIcon(R.drawable.ic_back);
        mBinDing.toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onInitViewModel() {
        mViewModel = new ViewModelProvider(this, MyApplication.factory).get(ChatDetailViewModel.class);
        mViewModel.getLiveDataMessage().observe(this, list -> {
            mAdapter.updateData(list);
            mBinDing.recyclerChat.scrollToPosition(list.size()-1);
        });

        mViewModel.getCheckDialog().observe(this,aBoolean -> {
            if(aBoolean){
                showProgressLoadding();
                return;
            }
            dismisProgressDialog();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_video:
                break;
            case R.id.actionCall:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgSend:
                mViewModel.sendMessage(mBinDing.edtChat.getText().toString());
                mBinDing.edtChat.setText(null);
                break;
        }
    }
}