package com.whatsapp.whatsappexample.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.whatsapp.whatsappexample.R;


public abstract class BaseActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 100;
    private ProgressDialog mProgressDialog;
    private static final String[] PERMISSITION_REQUESTION = {
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_EXTERNAL_STORAGE"
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        if(!isPermisitionGrant()){
            requestPermisition();
        }
        onInitViewModel();
        onInit();
        onListenerClicked();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean isPermisitionGrant(){
        for (String permisition : PERMISSITION_REQUESTION){
            if(checkSelfPermission(permisition) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    protected void requestPermisition(){
        ActivityCompat.requestPermissions(this, PERMISSITION_REQUESTION,REQUEST_CODE);
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        //To be used by child activities
    }

    protected void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
        }
    }

    protected void showProgressLoadding() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.lbl_loadding));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }

    protected void updateMessageProgressDialog(String message){
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.setMessage(message);
        }
    }

    protected void dismisProgressDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    protected void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected boolean isNetworkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfor = connectivityManager.getActiveNetworkInfo();
        boolean isNetworkState = activeNetInfor != null && activeNetInfor.isConnected();
        return isNetworkState;
    }

    protected void showAlertDialog(String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getString(R.string.dialog_box));
        dialogBuilder.setIcon(R.mipmap.ic_launcher);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogBuilder.setCancelable(false);
        dialogBuilder.show();
    }

    protected abstract View getContentView();

    protected abstract void onListenerClicked();

    protected abstract void onInit();

    protected abstract void onInitViewModel();
}