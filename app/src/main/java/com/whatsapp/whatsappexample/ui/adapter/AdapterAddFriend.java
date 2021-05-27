package com.whatsapp.whatsappexample.ui.adapter;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdapterAddFriend extends RecyclerView.Adapter<AdapterAddFriend.AddFriendViewHolder> {
    private List<User> mListUser = new ArrayList<>();

    public void updateData(List<User> list) {
        this.mListUser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_add_friends, parent,false);
        return new AddFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterAddFriend.AddFriendViewHolder holder, int position) {
        holder.bind(mListUser.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mListUser.size();
    }

    class AddFriendViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgAvatar;
        private final TextView mTxtUserName, mTxtEmail;
        private final CheckBox mCheckBoxAdd;

        public AddFriendViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTxtUserName = itemView.findViewById(R.id.txtUserName);
            mTxtEmail = itemView.findViewById(R.id.txtEmail);
            mCheckBoxAdd = itemView.findViewById(R.id.checkBoxAdd);
        }

        public void bind(User user,int position) {
            Picasso.get().load(user.getmProfilePic()).placeholder(R.drawable.ic_user).into(mImgAvatar);
            mTxtUserName.setText(user.getmUserName());
            mTxtEmail.setText(user.getmMail());
            mCheckBoxAdd.setChecked(user.getmCheckTrueFalse());

            mCheckBoxAdd.setOnClickListener(v -> {
                user.setmCheckTrueFalse(!user.getmCheckTrueFalse());
                notifyItemChanged(position);
                Log.d("AAA", "bind: "+mListUser.get(position).getmCheckTrueFalse());
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<User> getListUserAccept() {
        List<User>list = new ArrayList<>();
        mListUser.forEach(user -> {
            if(user.getmCheckTrueFalse()){
                list.add(user);
            }
        });
        return list;
    }
}
