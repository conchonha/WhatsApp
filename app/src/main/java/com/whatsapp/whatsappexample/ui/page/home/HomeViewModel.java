package com.whatsapp.whatsappexample.ui.page.home;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.whatsapp.whatsappexample.callback.CallbackGetListUser;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.viewmodel.BaseViewModel;

import java.util.List;

public class HomeViewModel extends BaseViewModel implements CallbackGetListUser {
    public HomeViewModel(Application application) {
        super(application);
    }

    public void signOut(){
        mFirebaseAuth.signOut();
    }

    public LiveData<List<User>> countNumberAcceptFriend(){
        mFireBaseDatabase.countRequestFriendsAccept(this);
        return mListUser;
    }

    @Override
    public void getListUser(List<User> listUser) {
        mListUser.setValue(listUser);
        setDialog(false);
    }
}
