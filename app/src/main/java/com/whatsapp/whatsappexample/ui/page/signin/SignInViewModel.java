package com.whatsapp.whatsappexample.ui.page.signin;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.firebase.AuthFirebase;
import com.whatsapp.whatsappexample.firebase.HandlingFirebaseDatabase;
import com.whatsapp.whatsappexample.model.User;

public class SignInViewModel extends AndroidViewModel {
    private AuthFirebase mAuth;
    private MutableLiveData<Boolean>mMutableLiveDataCheckLoading;
    private String TAG = "SignInActivity-AAA: ";
    private HandlingFirebaseDatabase mFirebaseDatabase;

    public SignInViewModel( Application application) {
        super(application);
        mAuth = AuthFirebase.getInstance(application);
        mMutableLiveDataCheckLoading = new MutableLiveData<>();
        mFirebaseDatabase = HandlingFirebaseDatabase.getInstance();
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

    //get info account google from dialog account
    public void getInfoAccountGoogle(Intent data){
        setMutableLiveDataCheckLoading(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAuth.getInfoAccountGoogle(data, new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete( Task<GoogleSignInAccount> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "firebaseAuthWithGoogle:" + task.getResult().getId());
                            signInAccountWithGoogle(task.getResult().getIdToken());
                        }else {
                            Log.d(TAG, "Google sign in failed" + task.getException().getMessage());
                        }
                    }
                });
            }
        }).run();
    }
    //signIn with google firebase
    private void signInAccountWithGoogle(String idToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAuth.signInAccountWithGoogle(idToken, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplication(), getApplication().getString(R.string.lbl_signin_success), Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            User user = new User();
                            user.setmUserName(firebaseUser.getDisplayName());
                            user.setmMail(firebaseUser.getEmail());
                            user.setmProfilePic(firebaseUser.getPhotoUrl().toString());

                            mFirebaseDatabase.createUser(user,firebaseUser.getUid());
                        }else{
                            Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                        setMutableLiveDataCheckLoading(false);
                    }
                });
            }
        }).run();
    }


    public boolean checkUser(){
       return mAuth.getCurrentUser() != null;
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
}
