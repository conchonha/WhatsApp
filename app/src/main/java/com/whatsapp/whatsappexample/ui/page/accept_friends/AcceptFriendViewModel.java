package com.whatsapp.whatsappexample.ui.page.accept_friends;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.whatsapp.whatsappexample.callback.CallbackGetListUser;
import com.whatsapp.whatsappexample.callback.CallbackUser;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.ui.page.home.HomeViewModel;


public class AcceptFriendViewModel extends HomeViewModel {
    public AcceptFriendViewModel(@NonNull Application application) {
        super(application);
    }

    public void onRemovePendingFriends(String key){
        setDialog(true);
        mFireBaseDatabase.onRemovePendingFriends(key,this);
    }

    public void onAcceptPendingFriends(User user1){
        setDialog(true);
        mFireBaseDatabase.getCurrentUser(new CallbackUser() {
            @Override
            public void getError(String str) {
                showToast(str);
                setDialog(false);
                Log.d("AAA", "getError: "+str);
            }

            @Override
            public void getUser(User user) {
                mFireBaseDatabase.onAcceptPendingFriends(user1,user,AcceptFriendViewModel.this);
            }
        });
    }

}
