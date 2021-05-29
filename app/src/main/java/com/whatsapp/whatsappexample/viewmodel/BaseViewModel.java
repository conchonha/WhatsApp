package com.whatsapp.whatsappexample.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.whatsapp.whatsappexample.callback.CallbackSuccess;
import com.whatsapp.whatsappexample.firebase.AuthFirebase;
import com.whatsapp.whatsappexample.firebase.HandlingFirebaseDatabase;
import com.whatsapp.whatsappexample.model.User;

import java.util.List;

public class BaseViewModel extends AndroidViewModel implements CallbackSuccess {
    public final AuthFirebase mFirebaseAuth;
    public final HandlingFirebaseDatabase mFireBaseDatabase;
    public final MutableLiveData<List<User>>mListUser;
    public final MutableLiveData<Boolean>mCheckDialog;
    private final MutableLiveData<User>mUser;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mFirebaseAuth = AuthFirebase.getInstance(application);
        mFireBaseDatabase = HandlingFirebaseDatabase.getInstance(application);
        mListUser = new MutableLiveData<>();
        mCheckDialog = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
    }

    public MutableLiveData<List<User>> getListUser() {
        return mListUser;
    }

    public MutableLiveData<Boolean> getCheckDialog() {
        return mCheckDialog;
    }

    public void setDialog(Boolean bDialog){
        mCheckDialog.setValue(bDialog);
    }

    public void setUser(User user){
        mUser.setValue(user);
    }

    public LiveData<User> getUser(){
        return mUser;
    }

    @Override
    public void success() {
        setDialog(false);
    }

    @Override
    public void getError(String str) {
        setDialog((false));
        showToast(str);
    }

    public void showToast(String str){
        Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show();
    }
}
