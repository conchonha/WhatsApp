package com.whatsapp.whatsappexample.firebase;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.whatsapp.whatsappexample.R;

public class AuthFirebase {
    private static AuthFirebase mInstance;
    private static FirebaseAuth mFirebaseAuth;
    private static Context mContext;
    private GoogleSignInOptions mGoogleOption;
    private GoogleSignInClient mGoogleSignInClient;

    public static AuthFirebase getInstance(Context context) {
        if (mInstance == null) {
            mContext = context;
            mInstance = new AuthFirebase();
            mFirebaseAuth = FirebaseAuth.getInstance();
        }
        return mInstance;
    }

    public void signUp(String mail, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        mFirebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(onCompleteListener);
    }

    public void signInEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener);
    }

    public FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser();
    }

    public void signOut() {
        mFirebaseAuth.signOut();
    }

    //get instance GoogleSignInClient
    public GoogleSignInClient getInstanceGoogleSignInClient() {
        // Configure Google Sign In
        mGoogleOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(mContext, mGoogleOption);
        return mGoogleSignInClient;
    }

    //get info account google from dialog account
    public void getInfoAccountGoogle(Intent data, OnCompleteListener<GoogleSignInAccount>onCompleteListener){
        GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener(onCompleteListener);
    }

    //signIn with google firebase
    public void signInAccountWithGoogle(String idToken, OnCompleteListener<AuthResult> onCompleteListener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(onCompleteListener);
    }

}
