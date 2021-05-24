package com.whatsapp.whatsappexample.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.utils.Contains;

public class HandlingFirebaseDatabase {
    private static HandlingFirebaseDatabase instance;
    private static DatabaseReference mMyRef;


    public static HandlingFirebaseDatabase getInstance(){
        if(instance == null){
            instance = new HandlingFirebaseDatabase();
            mMyRef = FirebaseDatabase.getInstance().getReference();
        }
        return instance;
    }

    public void createUser(User user, String userId){
        mMyRef.getRef().child(Contains.childUser).child(userId).setValue(user);
    }
}
