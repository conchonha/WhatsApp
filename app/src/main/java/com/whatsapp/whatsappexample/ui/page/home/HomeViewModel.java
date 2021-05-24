package com.whatsapp.whatsappexample.ui.page.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.whatsapp.whatsappexample.firebase.AuthFirebase;

public class HomeViewModel extends AndroidViewModel {
    private AuthFirebase mAuth;

    public HomeViewModel(Application application) {
        super(application);
        mAuth = AuthFirebase.getInstance(application);
    }

    public void signOut(){
        mAuth.signOut();
    }
}
