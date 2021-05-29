package com.whatsapp.whatsappexample.model;

public class User {
    private String mProfilePic,mUserName,mMail,mPassword,mUserId,mLastMessage,mKey;
    private Boolean mCheckTrueFalse = false;

    //Construcor defaul
    public User(){

    }

    public User(String mProfilePic, String mUserName, String mMail, String mPassword, String mUserId, String mLastMessage) {
        this.mProfilePic = mProfilePic;
        this.mUserName = mUserName;
        this.mMail = mMail;
        this.mPassword = mPassword;
        this.mUserId = mUserId;
        this.mLastMessage = mLastMessage;
    }

   //SignUp Construcor
    public User(String mUserName, String mMail, String mPassword) {
        this.mUserName = mUserName;
        this.mMail = mMail;
        this.mPassword = mPassword;
    }

    public String getmProfilePic() {
        return mProfilePic;
    }

    public void setmProfilePic(String mProfilePic) {
        this.mProfilePic = mProfilePic;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmMail() {
        return mMail;
    }

    public void setmMail(String mMail) {
        this.mMail = mMail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmLastMessage() {
        return mLastMessage;
    }

    public void setmLastMessage(String mLastMessage) {
        this.mLastMessage = mLastMessage;
    }

    public Boolean getmCheckTrueFalse() {
        return mCheckTrueFalse;
    }

    public void setmCheckTrueFalse(Boolean mCheckTrueFalse) {
        this.mCheckTrueFalse = mCheckTrueFalse;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
