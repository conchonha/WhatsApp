package com.whatsapp.whatsappexample.ui.page.chat_detail;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.whatsapp.whatsappexample.callback.CallBackMessage;
import com.whatsapp.whatsappexample.model.MessageModel;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.viewmodel.BaseViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatDetailViewModel extends BaseViewModel implements CallBackMessage {
    private final MutableLiveData<List<MessageModel>> mLiveDataMessage;
    private User mUser = null;
    private String senderId;
    private String senderRoom;
    private String receiverRoom;

    public ChatDetailViewModel(@NonNull Application application) {
        super(application);
        mLiveDataMessage = new MutableLiveData<>();
    }

    public void handlingData(User user) {
        setDialog(true);
        mUser = user;
        senderId = mFirebaseAuth.getCurrentUser().getUid();
        senderRoom =  senderId + mUser.getmUserId();
        receiverRoom = mUser.getmUserId() + senderId;

        mFireBaseDatabase.getMessage(senderRoom,this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendMessage(String edt){
        if(edt.isEmpty() || mUser == null) {
            showToast("Không được để trống message");
            return;
        }

        MessageModel messageModel = new MessageModel(senderId,edt);
        messageModel.setTimestamp(java.time.LocalTime.now()+"");

        mFireBaseDatabase.sendMessage(messageModel,senderRoom,receiverRoom,this);
    }

    public LiveData<List<MessageModel>> getLiveDataMessage() {
        return mLiveDataMessage;
    }

    @Override
    public void onSuccess(List<MessageModel> list) {
        mLiveDataMessage.setValue(list);
        setDialog(false);
    }
}
