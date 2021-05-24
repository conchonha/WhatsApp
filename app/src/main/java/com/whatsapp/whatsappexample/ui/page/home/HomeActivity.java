package com.whatsapp.whatsappexample.ui.page.home;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.app.MyApplication;
import com.whatsapp.whatsappexample.base.BaseActivity;
import com.whatsapp.whatsappexample.databinding.ActivityHomeBinding;
import com.whatsapp.whatsappexample.ui.adapter.FragmentPagerAdapter;
import com.whatsapp.whatsappexample.ui.page.signin.SignInActivity;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding mBinding;
    private HomeViewModel mHomeViewModel;
    @Override
    protected View getContentView() {
        mBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(HomeActivity.this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, getString(R.string.lbl_sign_out), Toast.LENGTH_SHORT).show();
                mHomeViewModel.signOut();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListenerClicked() {

    }

    @Override
    protected void onInit() {
        mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()));
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }

    @Override
    protected void onInitViewModel() {
        mHomeViewModel = new ViewModelProvider(this, MyApplication.factory).get(HomeViewModel.class);

    }
}