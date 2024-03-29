package com.whatsapp.whatsappexample.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Validations {
    public static String isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches() || email.isEmpty())
            return "Email Malformed";
        else
            return null;
    }

    public static String isPasswordValid(String password) {
        String PASSWORD_PATTERN = "^[a-z0-9]{6,12}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches() || password.isEmpty())
            return "Password Malformed";
        else
            return null;
    }

    public static boolean isValidPhoneNumber(String number) {
        String validNumber = "^0[35789]{1}\\d{8}$";
        Pattern pattern = Pattern.compile(validNumber, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(number);
        String validNumber1 = "^(([+84]{3})|[84]{2})[35789]{1}\\d{8}$";
        Pattern pattern1 = Pattern.compile(validNumber1, Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(number);
        String validNumber3 = "^[35789]{1}\\d{8}$";
        Pattern pattern3 = Pattern.compile(validNumber3, Pattern.CASE_INSENSITIVE);
        Matcher matcher3 = pattern3.matcher(number);
        return matcher.find() || matcher1.find() || matcher3.find();
    }

    public static String isValidName(String s) {
        String validName = "[0-9]*";
        Editable editable = new SpannableStringBuilder(s);
        if (s.matches(validName) || s.isEmpty() || isValidSpecialCharacters(editable)) {
            return "Name Malformed";
        }
        return null;
    }

    public static boolean checkInternet(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static boolean isValidSpecialCharacters(Editable s) {
        Pattern regex = Pattern.compile("[$&+,:;=?@#|/'<>.^*()%!-]");//~`•√ππ÷×¶∆\}{°¢€£©®™✓
        return regex.matcher(s).find();
    }

    public static boolean isValidAddress(String s) {
        Pattern regex = Pattern.compile("[$&+:;=?@#|/'<>.^*()%!]");//~`•√ππ÷×¶∆\}{°¢€£©®™✓
        return regex.matcher(s).find() || s.isEmpty();
    }

    public static String replaceMultiple(String baseString, String... replaceParts) {
        for (String s : replaceParts) {
            baseString = baseString.replaceAll(s, "");
        }
        return baseString;
    }

    public static void hideKeyboard(View v, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

}

