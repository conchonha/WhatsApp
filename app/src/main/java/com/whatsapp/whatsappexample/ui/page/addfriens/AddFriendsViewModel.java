package com.whatsapp.whatsappexample.ui.page.addfriens;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.whatsapp.whatsappexample.callback.CallbackGetListUser;
import com.whatsapp.whatsappexample.callback.CallbackSuccess;
import com.whatsapp.whatsappexample.firebase.HandlingFirebaseDatabase;
import com.whatsapp.whatsappexample.model.User;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsViewModel extends AndroidViewModel implements CallbackGetListUser {
    private final HandlingFirebaseDatabase mFirebaseDatabase;
    private final String TAG = "AddFriendsActivity-AAA";
    private final MutableLiveData<List<User>> mMutableDataListUserNoFriend;
    public final MutableLiveData<Boolean> mMutableLiveDataCheckLoading;
    private final MutableLiveData<String>mLiveErrorSendPendingFriend;
    private final MutableLiveData<Integer>mLiveSendPendingFriend;
    private String errorSendPendingFriend = "";
    private int totalErrorSendPendingFriend = 0;
    private int totalSendPendingFriend = 0;

    public AddFriendsViewModel(Application application) {
        super(application);
        mFirebaseDatabase = HandlingFirebaseDatabase.getInstance(getApplication());
        mMutableDataListUserNoFriend = new MutableLiveData<>();
        mMutableLiveDataCheckLoading = new MutableLiveData<>();
        mLiveErrorSendPendingFriend = new MutableLiveData<>();
        mLiveSendPendingFriend = new MutableLiveData<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<User> searchList(List<User> listUsers, String strSearch) {
        List<User> list = new ArrayList<>();
        listUsers.forEach(user -> {
            if (user.getmMail().toLowerCase().contains(strSearch.toLowerCase())) {
                list.add(user);
            }
        });
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendFriendRequestPending(List<User> list) {
        totalErrorSendPendingFriend = 0;
        errorSendPendingFriend = "";
        mMutableLiveDataCheckLoading.setValue(true);
        list.forEach(user -> mFirebaseDatabase.sendFriendRequestPending(user.getmUserId(), new CallbackSuccess() {
            @Override
            public void success() {
                ++totalSendPendingFriend;
                mLiveSendPendingFriend.setValue(totalSendPendingFriend);
            }

            @Override
            public void getError(String str) {
                ++totalErrorSendPendingFriend;
                errorSendPendingFriend += "Error send request friends total: " + totalErrorSendPendingFriend + "/\n" + user.getmUserName() + "\n";
                mLiveErrorSendPendingFriend.setValue(errorSendPendingFriend);
            }
        }));

    }

    public LiveData<List<User>> getListUserNoFriend() {
        mMutableLiveDataCheckLoading.setValue(true);
        mFirebaseDatabase.getListFriends(this);
        return mMutableDataListUserNoFriend;
    }

    public MutableLiveData<Boolean> getMutableLiveDataCheckLoading() {
        return mMutableLiveDataCheckLoading;
    }


    public MutableLiveData<String> getLiveErrorSendPendingFriend() {
        return mLiveErrorSendPendingFriend;
    }

    public MutableLiveData<Integer> getLiveCountSendPendingFriend() {
        return mLiveSendPendingFriend;
    }


    @Override
    public void getListUser(List<User> listUser) {
        mMutableDataListUserNoFriend.setValue(listUser);
        mMutableLiveDataCheckLoading.setValue(false);
    }

    @Override
    public void getError(String str) {
        Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "getError: "+str);
        mMutableLiveDataCheckLoading.setValue(false);
    }
}
