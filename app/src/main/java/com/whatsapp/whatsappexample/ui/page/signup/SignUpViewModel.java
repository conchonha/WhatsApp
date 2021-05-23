package com.whatsapp.whatsappexample.ui.page.signup;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.firebase.AuthFirebase;
import com.whatsapp.whatsappexample.firebase.HandlingFirebaseDatabase;
import com.whatsapp.whatsappexample.model.User;


public class SignUpViewModel extends AndroidViewModel {
    private AuthFirebase mAuthFirebase;
    private HandlingFirebaseDatabase mFirebaseDatabase;
    private MutableLiveData<Boolean>mutableLiveDataCheckLoading;

    public SignUpViewModel(Application application) {
        super(application);
        mAuthFirebase = AuthFirebase.getInstance();
        mFirebaseDatabase = HandlingFirebaseDatabase.getInstance();
        mutableLiveDataCheckLoading = new MutableLiveData<>();
    }

    public void createUser(String userName, String mail, String password) {
        setMutableLiveDataCheckLoading(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAuthFirebase.signUp(mail, password, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        setMutableLiveDataCheckLoading(false);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplication(), getApplication().getString(R.string.lbl_signup_success), Toast.LENGTH_SHORT).show();
                            User user = new User(userName,mail,password);
                            mFirebaseDatabase.createUser(user,task.getResult().getUser().getUid());
                        } else {
                            Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).run();
    }

    public MutableLiveData<Boolean> getMutableLiveDataCheckLoading() {
        return mutableLiveDataCheckLoading;
    }

    public void setMutableLiveDataCheckLoading(Boolean bool) {
        this.mutableLiveDataCheckLoading.setValue(bool);
    }
}
