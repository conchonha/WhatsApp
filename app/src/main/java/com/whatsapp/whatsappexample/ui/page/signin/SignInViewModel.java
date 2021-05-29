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
import com.whatsapp.whatsappexample.viewmodel.BaseViewModel;

import java.util.Objects;

public class SignInViewModel extends BaseViewModel implements CallbackSuccess {
    private final String TAG = "SignInActivity-AAA: ";

    public SignInViewModel( Application application) {
        super(application);
    }

    public void signInEmailAndPassword(String email,String password){
        setDialog(true);
        mFirebaseAuth.signInEmailAndPassword(email, password, task ->{
            if(task.isSuccessful()){
                success();
                return;
            }
            getError(Objects.requireNonNull(task.getException()).getMessage());
        });

    }

    //get info account google from dialog account
    public void getInfoAccountGoogle(Intent data){
        setDialog(true);
        mFirebaseAuth.getInfoAccountGoogle(data, task -> {
            if(task.isSuccessful()){
                signInAccountWithGoogle(Objects.requireNonNull(task.getResult()).getIdToken());
                return;
            }
            getError(Objects.requireNonNull(task.getException()).getMessage());
        });
    }
    //signIn with google firebase
    private void signInAccountWithGoogle(String idToken) {
        mFirebaseAuth.signInAccountWithGoogle(idToken, task -> {
            if(task.isSuccessful()){
                // Sign in success, update UI with the signed-in user's information
                showToast(getApplication().getString(R.string.lbl_signin_success));
                FirebaseUser firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                if(firebaseUser != null){
                    User user = new User();
                    user.setmUserId(firebaseUser.getUid());
                    user.setmUserName(firebaseUser.getDisplayName());
                    user.setmMail(firebaseUser.getEmail());
                    user.setmProfilePic( firebaseUser.getPhotoUrl().toString());

                    mFireBaseDatabase.createUser(user,firebaseUser.getUid(),SignInViewModel.this);
                }
                return;
            }
            getError(Objects.requireNonNull(task.getException()).getMessage());
        });
    }

    public GoogleSignInClient getInstanceGoogleSignInClient(){
        return mFirebaseAuth.getInstanceGoogleSignInClient();
    }

}
