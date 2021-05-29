package com.whatsapp.whatsappexample.ui.page.accept_friends;

import android.app.Application;

import androidx.annotation.NonNull;

import com.whatsapp.whatsappexample.callback.CallbackGetListUser;
import com.whatsapp.whatsappexample.ui.page.home.HomeViewModel;


public class AcceptFriendViewModel extends HomeViewModel implements CallbackGetListUser {
    public AcceptFriendViewModel(@NonNull Application application) {
        super(application);

    }

    public void onRemovePendingFriends(String key){
        setDialog(true);
        mFireBaseDatabase.onRemovePendingFriends(key,this);
    }
}
