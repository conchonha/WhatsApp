package com.whatsapp.whatsappexample.ui.page.signin;


import android.content.Intent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.app.MyApplication;
import com.whatsapp.whatsappexample.base.BaseActivity;

import com.whatsapp.whatsappexample.databinding.ActivitySigninBinding;
import com.whatsapp.whatsappexample.ui.page.MainActivity;
import com.whatsapp.whatsappexample.ui.page.signup.SignUpActivity;
import com.whatsapp.whatsappexample.utils.Validations;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
        }
    }
}