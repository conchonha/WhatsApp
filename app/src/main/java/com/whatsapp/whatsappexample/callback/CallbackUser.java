package com.whatsapp.whatsappexample.callback;

import com.whatsapp.whatsappexample.model.User;

public interface CallbackUser {
    public void setErrorCallBack(String str);
    public void getUser(User user);
}
