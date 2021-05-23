package com.whatsapp.whatsappexample.ui.page.signup;


import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.app.MyApplication;
import com.whatsapp.whatsappexample.base.BaseActivity;
import com.whatsapp.whatsappexample.databinding.ActivitySignupBinding;
import com.whatsapp.whatsappexample.utils.Validations;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySignupBinding mBinding;
    private SignUpViewModel mSignUpViewModel;

    @Override
    protected View getContentView() {
        mBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void onInitViewModel() {
        mSignUpViewModel = new ViewModelProvider(this, MyApplication.factory).get(SignUpViewModel.class);

        mSignUpViewModel.getMutableLiveDataCheckLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    showProgressLoadding();
                }else{
                    dismisProgressDialog();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onInit() {
        getSupportActionBar().hide();
    }

    @Override
    protected void onListenerClicked() {
        mBinding.btnSignUp.setOnClickListener(this);
        mBinding.mtxtAreadyAcount.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                String name = mBinding.editTextPersonName.getText().toString();
                String email = mBinding.editTextEmail.getText().toString();
                String password = mBinding.editTextPassword.getText().toString();

                mBinding.editTextPersonName.setError(Validations.isValidName(name));
                mBinding.editTextEmail.setError(Validations.isEmailValid(email));
                mBinding.editTextPassword.setError(Validations.isPasswordValid(password));

                if(Validations.isValidName(name) == null && Validations.isEmailValid(email) == null && Validations.isPasswordValid(password) == null){
                    mSignUpViewModel.createUser(name, email, password);
                }
                break;
            case R.id.mtxtAreadyAcount:
                finish();
                break;
        }
    }
}