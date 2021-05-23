package com.whatsapp.whatsappexample.firebase;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthFirebase {
    private static AuthFirebase mInstance;
    private static FirebaseAuth mFirebaseAuth;


    public static AuthFirebase getInstance() {
        if (mInstance == null) {
            mInstance = new AuthFirebase();
            mFirebaseAuth = FirebaseAuth.getInstance();
        }
        return mInstance;
    }

    public void signUp(String mail, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        mFirebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(onCompleteListener);
    }

    public void signInEmailAndPassword(String email,String password,OnCompleteListener<AuthResult> onCompleteListener){
        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(onCompleteListener);
    }

    public FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser();
    }
}
