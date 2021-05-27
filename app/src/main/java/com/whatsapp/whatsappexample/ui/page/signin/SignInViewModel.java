package com.whatsapp.whatsappexample.ui.page.signin;

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

public class SignInViewModel extends AndroidViewModel implements CallbackSuccess {
    private final AuthFirebase mAuth;
    private final MutableLiveData<Boolean>mMutableLiveDataCheckLoading;
    private final String TAG = "SignInActivity-AAA: ";
    private final HandlingFirebaseDatabase mFirebaseDatabase;

    public SignInViewModel( Application application) {
        super(application);
        mAuth = AuthFirebase.getInstance(application);
        mMutableLiveDataCheckLoading = new MutableLiveData<>();
        mFirebaseDatabase = HandlingFirebaseDatabase.getInstance(application);
    }

    public void signInEmailAndPassword(String email,String password){
        setMutableLiveDataCheckLoading(true);
        mAuth.signInEmailAndPassword(email, password, task ->{
            if(task.isSuccessful()){
                success();
                return;
            }
            getError(Objects.requireNonNull(task.getException()).getMessage());
        });

    }

    //get info account google from dialog account
    public void getInfoAccountGoogle(Intent data){
        setMutableLiveDataCheckLoading(true);
        mAuth.getInfoAccountGoogle(data, task -> {
            if(task.isSuccessful()){
                signInAccountWithGoogle(Objects.requireNonNull(task.getResult()).getIdToken());
                return;
            }
            getError(Objects.requireNonNull(task.getException()).getMessage());
        });
    }
    //signIn with google firebase
    private void signInAccountWithGoogle(String idToken) {
        mAuth.signInAccountWithGoogle(idToken, task -> {
            if(task.isSuccessful()){
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(getApplication(), getApplication().getString(R.string.lbl_signin_success), Toast.LENGTH_SHORT).show();
                FirebaseUser firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                if(firebaseUser != null){
                    User user = new User();
                    user.setmUserId(firebaseUser.getUid());
                    user.setmUserName(firebaseUser.getDisplayName());
                    user.setmMail(firebaseUser.getEmail());
                    user.setmProfilePic( firebaseUser.getPhotoUrl().toString());

                    mFirebaseDatabase.createUser(user,firebaseUser.getUid(),SignInViewModel.this);
                }
                return;
            }
            getError(Objects.requireNonNull(task.getException()).getMessage());
        });
    }

    public GoogleSignInClient getInstanceGoogleSignInClient(){
        return mAuth.getInstanceGoogleSignInClient();
    }

    public MutableLiveData<Boolean> getMutableLiveDataCheckLoading() {
        return mMutableLiveDataCheckLoading;
    }

    public void setMutableLiveDataCheckLoading(Boolean bool) {
        this.mMutableLiveDataCheckLoading.setValue(bool);
    }

    @Override
    public void success() {
        setMutableLiveDataCheckLoading(false);
    }

    @Override
    public void getError(String str) {
        Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "getError: "+str);
        setMutableLiveDataCheckLoading(false);
    }
}
