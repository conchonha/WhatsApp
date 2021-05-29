package com.whatsapp.whatsappexample.firebase;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp.whatsappexample.callback.CallbackGetListUser;
import com.whatsapp.whatsappexample.callback.CallbackSuccess;
import com.whatsapp.whatsappexample.callback.CallbackUser;
import com.whatsapp.whatsappexample.model.User;
import com.whatsapp.whatsappexample.utils.Contains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandlingFirebaseDatabase {
    private static HandlingFirebaseDatabase instance;
    private static DatabaseReference mMyRef;
    private static AuthFirebase mFirebaseAuth;


    public static HandlingFirebaseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new HandlingFirebaseDatabase();
            mMyRef = FirebaseDatabase.getInstance().getReference();
            mFirebaseAuth = AuthFirebase.getInstance(context);
        }
        return instance;
    }

    public void createUser(User user, String userId, CallbackSuccess callbackSuccess) {
        mMyRef.getRef().child(Contains.childUser).child(userId).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callbackSuccess.success();
                return;
            }
            callbackSuccess.getError(Objects.requireNonNull(task.getException()).getMessage());
        });
    }

    public void getCurrentUser(CallbackUser callbackUser) {
        mMyRef.getRef().child(Contains.childUser).child(mFirebaseAuth.getCurrentUser().getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        user.setmUserId(snapshot.getKey());
                        callbackUser.getUser(user);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        callbackUser.setErrorCallBack(error.getMessage());
                    }
                });
    }

    public void getListUser(CallbackGetListUser callbackGetListUser) {
        List<User> list = new ArrayList<>();
        mMyRef.getRef().child(Contains.childUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        User user = snapshot1.getValue(User.class);
                        user.setmUserId(snapshot1.getKey());
                        list.add(user);
                    }
                    callbackGetListUser.getListUser(list);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callbackGetListUser.getError(error.getMessage());
            }
        });
    }

    public void getListFriends(CallbackGetListUser callbackGetListUser) {
        getListUser(new CallbackGetListUser() {
            @Override
            public void getListUser(List<User> listUser) {
                mMyRef.getRef().child(Contains.childFriend).
                        child(Contains.childAccept).
                        child(mFirebaseAuth.getCurrentUser().getUid()).
                        addValueEventListener(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    User user = dataSnapshot.getValue(User.class);
                                    listUser.removeIf(user1 -> user1.getmUserId().equals(user.getmUserId()) || user1.getmUserId().equals(mFirebaseAuth.getCurrentUser().getUid()));
                                }
                                callbackGetListUser.getListUser(listUser);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                callbackGetListUser.getError(error.getMessage());
                            }
                        });
            }

            @Override
            public void getError(String error) {
                callbackGetListUser.getError(error);
            }
        });
    }

    //public void acceptFriends()

    public void sendFriendRequestPending(String keyId, CallbackSuccess callbackSuccess) {
        getCurrentUser(new CallbackUser() {
            @Override
            public void setErrorCallBack(String str) {
                callbackSuccess.getError(str);
            }

            @Override
            public void getUser(User user) {
                mMyRef.getRef().child(Contains.childFriend).
                        child(Contains.childKeyPending).
                        child(keyId).push().setValue(user).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        callbackSuccess.success();
                        return;
                    }
                    callbackSuccess.getError(task.getException().getMessage());
                });
            }
        });
    }

    public void countRequestFriendsAccept(CallbackGetListUser callbackGetListUser) {
        mMyRef.getRef().child(Contains.childFriend).
                child(Contains.childKeyPending).
                child(mFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                List<User>list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    user.setmKey(snapshot1.getKey());
                    list.add(user);
                }
                callbackGetListUser.getListUser(list);
            }

            @Override
            public void onCancelled( DatabaseError error) {
                callbackGetListUser.getError(error.getMessage());
            }
        });
    }

    public void onRemovePendingFriends(String key,CallbackSuccess callbackSuccess){
        mMyRef.getRef().child(Contains.childFriend).
                child(Contains.childKeyPending).
                child(mFirebaseAuth.getCurrentUser().getUid()).
                child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callbackSuccess.success();
                    return;
                }
                callbackSuccess.getError(task.getException().getMessage());
            }
        });
    }
}
