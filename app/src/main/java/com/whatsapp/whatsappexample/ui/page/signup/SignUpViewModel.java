package com.whatsapp.whatsappexample.ui.page.signup;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.callback.CallbackSuccess;
import com.whatsapp.whatsappexample.firebase.AuthFirebase;
import com.whatsapp.whatsappexample.firebase.HandlingFirebaseDatabase;
import com.whatsapp.whatsappexample.model.User;

import java.util.Objects;


public class SignUpViewModel extends AndroidViewModel implements CallbackSuccess {
    private final AuthFirebase mAuthFirebase;
    private final HandlingFirebaseDatabase mFirebaseDatabase;
    private final MutableLiveData<Boolean> mutableLiveDataCheckLoading;
    private final String TAG = "SignUpActivity-AAA:";

    public SignUpViewModel(Application application) {
        super(application);
        mAuthFirebase = AuthFirebase.getInstance(application);
        mFirebaseDatabase = HandlingFirebaseDatabase.getInstance(application);
        mutableLiveDataCheckLoading = new MutableLiveData<>();
    }

    public void createUser(String userName, String mail, String password) {
        setMutableLiveDataCheckLoading(true);
        mAuthFirebase.signUp(mail, password, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplication(), getApplication().getString(R.string.lbl_signup_success), Toast.LENGTH_SHORT).show();
                User user = new User(userName, mail, password);
                user.setmUserId(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid());
                mFirebaseDatabase.createUser(user, user.getmUserId(), SignUpViewModel.this);
                return;
            }
            getError(Objects.requireNonNull(task.getException()).getMessage());
        });
    }

    //b1 create instance GoogleSignInClient
    public GoogleSignInClient getInstanceGoogleSignInClient() {
        return mAuthFirebase.getInstanceGoogleSignInClient();
    }

    //b2 get info account google from dialog account
    public void getInfoAccountGoogle(Intent data) {
        setMutableLiveDataCheckLoading(true);
        mAuthFirebase.getInfoAccountGoogle(data, task -> {
            if (task.isSuccessful()) {
                signInAccountWithGoogle(Objects.requireNonNull(task.getResult()).getIdToken());
                return;
            }
            getError(getApplication().getString(R.string.lbl_sign_faild));
        });
    }

    //b33 signInwith google firebase
    private void signInAccountWithGoogle(String idToken) {
        mAuthFirebase.signInAccountWithGoogle(idToken, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(getApplication(), getApplication().getString(R.string.lbl_signup_success), Toast.LENGTH_SHORT).show();
                FirebaseUser firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                if(firebaseUser != null){
                    User user = new User();
                    user.setmUserName(firebaseUser.getDisplayName());
                    user.setmMail(firebaseUser.getEmail());
                    user.setmProfilePic(Objects.requireNonNull(firebaseUser.getPhotoUrl()).toString());
                    user.setmUserId(firebaseUser.getUid());

                    mFirebaseDatabase.createUser(user, firebaseUser.getUid(), SignUpViewModel.this);
                }
                return;
            }
            getError(Objects.requireNonNull(task.getException()).getMessage());
        });
    }

    public MutableLiveData<Boolean> getMutableLiveDataCheckLoading() {
        return mutableLiveDataCheckLoading;
    }

    public void setMutableLiveDataCheckLoading(Boolean bool) {
        this.mutableLiveDataCheckLoading.setValue(bool);
    }

    @Override
    public void success() {
        setMutableLiveDataCheckLoading(false);
    }

    @Override
    public void getError(String str) {
        Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "getError: " + str);
        setMutableLiveDataCheckLoading(false);
    }
}
