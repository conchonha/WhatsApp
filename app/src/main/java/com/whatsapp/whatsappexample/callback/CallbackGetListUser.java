package com.whatsapp.whatsappexample.callback;

import com.whatsapp.whatsappexample.model.User;

import java.util.List;

public interface CallbackGetListUser {
    public void getListUser(List<User>listUser);
    public void getError(String error);
}
