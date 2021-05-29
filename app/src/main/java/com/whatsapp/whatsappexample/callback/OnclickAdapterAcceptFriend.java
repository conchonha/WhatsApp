package com.whatsapp.whatsappexample.callback;

import com.whatsapp.whatsappexample.model.User;

public interface OnclickAdapterAcceptFriend {
    void onAccept(User user,int Position);
    void onRemove(User user,int Position);
}
