package com.whatsapp.whatsappexample.ui.page.home;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.whatsapp.whatsappexample.callback.CallbackGetListUser;
import com.whatsapp.whatsappexample.firebase.AuthFirebase;
import com.whatsapp.whatsappexample.firebase.HandlingFirebaseDatabase;
import com.whatsapp.whatsappexample.model.User;

import java.util.List;

public class HomeViewModel extends AndroidViewModel implements CallbackGetListUser {
    private final AuthFirebase mAuth;
    private final HandlingFirebaseDatabase mFireBaseDatabase;
    private final MutableLiveData<Boolean>mMutableCheckShowDialog;
    private final MutableLiveData<List<User>>mMutableListAcceptUser;

    public HomeViewModel(Application application) {
        super(application);
        mAuth = AuthFirebase.getInstance(application);
        mFireBaseDatabase = HandlingFirebaseDatabase.getInstance(application);
        mMutableCheckShowDialog = new MutableLiveData<>();
        mMutableListAcceptUser = new MutableLiveData<>();
    }

    public void signOut(){
        mAuth.signOut();
    }


    public MutableLiveData<Boolean> getMutableCheckShowDialog() {
        return mMutableCheckShowDialog;
    }

    public MutableLiveData<List<User>> getMutableListAcceptUser() {
        this.mMutableCheckShowDialog.setValue(true);
        mFireBaseDatabase.countRequestFriendsAccept(this);
        return mMutableListAcceptUser;
    }

    @Override
    public void getListUser(List<User> listUser) {
        this.mMutableListAcceptUser.setValue(listUser);
        mMutableCheckShowDialog.setValue(false);
    }

    @Override
    public void getError(String error) {
        this.mMutableCheckShowDialog.setValue(false);
        Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show();
    }
}
