package com.whatsapp.whatsappexample.ui.page.signup;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.callback.CallbackSuccess;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.utils.Validations;
import com.whatsapp.whatsappexample.viewmodel.BaseViewModel;

import java.util.Objects;


public class SignUpViewModel extends BaseViewModel implements CallbackSuccess {
    public SignUpViewModel(Application application) {
        super(application);
    }

    public void createUser(String userName, String mail, String password) {
        if(Validations.isValidName(userName) == null && Validations.isEmailValid(mail) == null && Validations.isPasswordValid(password) == null){
            setDialog(true);
            mFirebaseAuth.signUp(mail, password, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplication(), getApplication().getString(R.string.lbl_signup_success), Toast.LENGTH_SHORT).show();
                    User user = new User(userName, mail, password);
                    user.setmUserId(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid());
                    mFireBaseDatabase.createUser(user, user.getmUserId(), SignUpViewModel.this);
                    return;
                }
                getError(Objects.requireNonNull(task.getException()).getMessage());
            });
        }
    }

    //b1 create instance GoogleSignInClient
    public GoogleSignInClient getInstanceGoogleSignInClient() {
        return mFirebaseAuth.getInstanceGoogleSignInClient();
    }

    //b2 get info account google from dialog account
    public void getInfoAccountGoogle(Intent data) {
        setDialog(true);
        mFirebaseAuth.getInfoAccountGoogle(data, task -> {
            if (task.isSuccessful()) {
                signInAccountWithGoogle(Objects.requireNonNull(task.getResult()).getIdToken());
                return;
            }
            getError(getApplication().getString(R.string.lbl_sign_faild));
        });
    }

    //b33 signInwith google firebase
    private void signInAccountWithGoogle(String idToken) {
        mFirebaseAuth.signInAccountWithGoogle(idToken, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
               showToast(getApplication().getString(R.string.lbl_signup_success));
                FirebaseUser firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                if(firebaseUser != null){
                    User user = new User();
                    user.setmUserName(firebaseUser.getDisplayName());
                    user.setmMail(firebaseUser.getEmail());
                    user.setmProfilePic(Objects.requireNonNull(firebaseUser.getPhotoUrl()).toString());
                    user.setmUserId(firebaseUser.getUid());

                    mFireBaseDatabase.createUser(user, firebaseUser.getUid(), SignUpViewModel.this);
                }
                return;
            }
            getError(Objects.requireNonNull(task.getException()).getMessage());
        });
    }
}
