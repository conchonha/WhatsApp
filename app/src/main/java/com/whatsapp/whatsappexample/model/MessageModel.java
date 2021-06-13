package com.whatsapp.whatsappexample.model;

public class MessageModel {
    String mUID, mMessage;
    String mTimestamp;

    public MessageModel(String mUID, String mMessage, String mTimestamp) {
        this.mUID = mUID;
        this.mMessage = mMessage;
        this.mTimestamp = mTimestamp;
    }

    public MessageModel(){

    }

    public MessageModel(String mUID, String mMessage) {
        this.mUID = mUID;
        this.mMessage = mMessage;
    }

    public String getUID() {
        return mUID;
    }

    public void setUID(String mUID) {
        this.mUID = mUID;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String mTimestamp) {
        this.mTimestamp = mTimestamp;
    }
}
