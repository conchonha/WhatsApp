package com.whatsapp.whatsappexample.callback;

import com.whatsapp.whatsappexample.model.MessageModel;

import java.util.List;

public interface  CallBackMessage {
    void onSuccess(List<MessageModel>list);
    public void getError(String str);
}
