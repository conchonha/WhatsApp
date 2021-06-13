package com.whatsapp.whatsappexample.ui.page.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.whatsapp.whatsappexample.callback.CallbackGetListUser;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.viewmodel.BaseViewModel;

import java.util.List;

public class HomeViewModel extends BaseViewModel implements CallbackGetListUser {
    public HomeViewModel(Application application) {
        super(application);
    }
    private MutableLiveData<List<User>>mLiveDataUser = new MutableLiveData<>();

    public void signOut(){
        mFirebaseAuth.signOut();
    }

    public LiveData<List<User>> countNumberAcceptFriend(){
        setDialog(true);
        mFireBaseDatabase.countRequestFriendsAccept(this);
        return mListUser;
    }

    public LiveData<List<User>>getListFriends(){
        setDialog(true);
        mFireBaseDatabase.getListFriends(new CallbackGetListUser() {
            @Override
            public void getListUser(List<User> listUser) {
                mLiveDataUser.setValue(listUser);
                setDialog(false);
            }

            @Override
            public void getError(String error) {
                setDialog(false);
                showToast(error);
            }
        });
        return mLiveDataUser;
    }

    @Override
    public void getListUser(List<User> listUser) {
        mListUser.setValue(listUser);
        setDialog(false);
    }
}
