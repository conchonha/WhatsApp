package com.whatsapp.whatsappexample.ui.page.signup;


import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.app.MyApplication;
import com.whatsapp.whatsappexample.base.BaseActivity;
import com.whatsapp.whatsappexample.databinding.ActivitySignupBinding;
import com.whatsapp.whatsappexample.ui.page.home.HomeActivity;
import com.whatsapp.whatsappexample.utils.Contains;
import com.whatsapp.whatsappexample.utils.Validations;

public class SignUpActivity extends BaseActivity implements View.OnClickListener ,FirebaseAuth.AuthStateListener{
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

        mSignUpViewModel.getMutableLiveDataCheckLoading().observe(this, aBoolean -> {
            if(aBoolean){
                showProgressLoadding();
            }else{
                dismisProgressDialog();
            }
        });
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onListenerClicked() {
        mBinding.btnSignUp.setOnClickListener(this);
        mBinding.mtxtAreadyAcount.setOnClickListener(this);
        mBinding.btnGoogle.setOnClickListener(this);
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
            case R.id.btnGoogle:
                // mở intent signin google
                Intent signInIntent = mSignUpViewModel.getInstanceGoogleSignInClient().getSignInIntent();
                startActivityForResult(signInIntent, Contains.RC_SIGN_IN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Contains.RC_SIGN_IN){
            mSignUpViewModel.getInfoAccountGoogle(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // đăng kí sự kiện lắng nghe sự kiện khi user thay đổi, đăng kí đăng nhập update
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //hủy đăng kí sự kiện lắng nghe sự kiện khi user thay đổi, đăng kí đăng nhập update
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser() != null ){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }
}