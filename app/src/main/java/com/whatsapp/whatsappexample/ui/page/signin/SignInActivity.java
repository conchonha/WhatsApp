package com.whatsapp.whatsappexample.ui.page.signin;


import android.content.Intent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.app.MyApplication;
import com.whatsapp.whatsappexample.base.BaseActivity;

import com.whatsapp.whatsappexample.databinding.ActivitySigninBinding;
import com.whatsapp.whatsappexample.ui.page.home.HomeActivity;
import com.whatsapp.whatsappexample.ui.page.signup.SignUpActivity;
import com.whatsapp.whatsappexample.utils.Contains;
import com.whatsapp.whatsappexample.utils.Validations;

public class SignInActivity extends BaseActivity implements View.OnClickListener, FirebaseAuth.AuthStateListener {
    private ActivitySigninBinding mBinding;
    private SignInViewModel mSignInViewModel;
    @Override
    protected View getContentView() {
        mBinding = ActivitySigninBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void onListenerClicked() {
        mBinding.btnSignIn.setOnClickListener(this::onClick);
        mBinding.mTxtSignUp.setOnClickListener(this::onClick);
        mBinding.btnGoogle.setOnClickListener(this::onClick);
    }

    @Override
    protected void onInit() {
        getSupportActionBar().hide();
    }

    @Override
    protected void onInitViewModel() {
        mSignInViewModel = new ViewModelProvider(this, MyApplication.factory).get(SignInViewModel.class);

        mSignInViewModel.getMutableLiveDataCheckLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    showProgressLoadding();
                }else {
                    dismisProgressDialog();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignIn:
                String email = mBinding.editTextEmailOrPhone.getText().toString();
                String password = mBinding.editTextPassword.getText().toString();

                mBinding.editTextEmailOrPhone.setError(Validations.isEmailValid(email));
                mBinding.editTextPassword.setError(Validations.isPasswordValid(password));

                if(Validations.isEmailValid(email) == null && Validations.isPasswordValid(password) == null){
                    mSignInViewModel.signInEmailAndPassword(email,password);
                }
                break;
            case R.id.mTxtSignUp:
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                break;
            case R.id.btnGoogle:
                // mở intent signin google
                Intent signInIntent = mSignInViewModel.getInstanceGoogleSignInClient().getSignInIntent();
                startActivityForResult(signInIntent, Contains.RC_SIGN_IN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Contains.RC_SIGN_IN){
            mSignInViewModel.getInfoAccountGoogle(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // đăng kí sự kiện lắng nghe sự kiện khi user thay đổi, đăng kí đăng nhập update
        FirebaseAuth.getInstance().addAuthStateListener(this::onAuthStateChanged);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //hủy đăng kí sự kiện lắng nghe sự kiện khi user thay đổi, đăng kí đăng nhập update
        FirebaseAuth.getInstance().removeAuthStateListener(this::onAuthStateChanged);
    }

    @Override
    public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser() != null ){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }
}