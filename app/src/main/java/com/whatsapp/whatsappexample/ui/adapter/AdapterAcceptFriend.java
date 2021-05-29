package com.whatsapp.whatsappexample.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.callback.OnclickAdapterAcceptFriend;
import com.whatsapp.whatsappexample.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdapterAcceptFriend extends RecyclerView.Adapter<AdapterAcceptFriend.AcceptFriendViewHolder> {
    private List<User> mList = new ArrayList<>();
    private OnclickAdapterAcceptFriend mOnclick;

    public void updateData(List<User>list,OnclickAdapterAcceptFriend click){
        this.mList = list;
        this.mOnclick = click;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AcceptFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_accept_friends,parent,false);
        return new AcceptFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptFriendViewHolder holder, int position) {
        holder.bin(mList.get(position));
        holder.onClickListener();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

     class AcceptFriendViewHolder extends RecyclerView.ViewHolder{
        private final ImageView mImgAvatar;
        private final TextView mTxtEmail,mTxtName;
        private Button mBtnRemove,mBtnAccept;
        public AcceptFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTxtEmail = itemView.findViewById(R.id.txtEmail);
            mTxtName = itemView.findViewById(R.id.txtUserName);
            mBtnAccept = itemView.findViewById(R.id.btnAccept);
            mBtnRemove = itemView.findViewById(R.id.btnRemove);
        }

        public void bin(User user){
            Picasso.get().load(user.getmProfilePic()).placeholder(R.drawable.ic_user).into(mImgAvatar);
            mTxtEmail.setText(user.getmMail());
            mTxtName.setText(user.getmUserName());
        }

        public void onClickListener(){
            mBtnAccept.setOnClickListener(v -> {
                mOnclick.onAccept(mList.get(getPosition()),getPosition());
            });

            mBtnRemove.setOnClickListener(v -> {
                mOnclick.onRemove(mList.get(getPosition()),getPosition());
            });
        }
    }
}
