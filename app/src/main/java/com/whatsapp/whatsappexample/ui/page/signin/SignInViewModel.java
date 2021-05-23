package com.whatsapp.whatsappexample.ui.page.signin;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.firebase.AuthFirebase;

public class SignInViewModel extends AndroidViewModel {
    private AuthFirebase mAuth;
    private MutableLiveData<Boolean>mMutableLiveDataCheckLoading;

    public SignInViewModel( Application application) {
        super(application);
        mAuth = AuthFirebase.getInstance();
        mMutableLiveDataCheckLoading = new MutableLiveData<>();
    }

    public void signInEmailAndPassword(String email,String password){
        setMutableLiveDataCheckLoading(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAuth.signInEmailAndPassword(email, password, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        setMutableLiveDataCheckLoading(false);
                        if(task.isSuccessful()){
                            Toast.makeText(getApplication(), getApplication().getString(R.string.lbl_signin_success), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }

    public MutableLiveData<Boolean> getMutableLiveDataCheckLoading() {
        return mMutableLiveDataCheckLoading;
    }

    public void setMutableLiveDataCheckLoading(Boolean bool) {
        this.mMutableLiveDataCheckLoading.setValue(bool);
    }
}
