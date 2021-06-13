package com.whatsapp.whatsappexample.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.callback.onClickItemFragmentChat;
import com.whatsapp.whatsappexample.model.User;

import java.util.ArrayList;

public class AdapterItemUserFragmentChats extends RecyclerView.Adapter<AdapterItemUserFragmentChats.ItemUserViewHolder> {
    private ArrayList<User>mList = new ArrayList<>();
    private onClickItemFragmentChat mOnClickItemFragmentChat;

    public void updateList(ArrayList<User>list, onClickItemFragmentChat onClickItemFragmentChat){
        mList = list;
        notifyDataSetChanged();
        this.mOnClickItemFragmentChat = onClickItemFragmentChat;
    }

    @Override
    public ItemUserViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_user_fragment_chat,parent,false);
        return new ItemUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AdapterItemUserFragmentChats.ItemUserViewHolder holder, int position) {
        holder.bin(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ItemUserViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImgAvatar;
        private TextView mTxtLastMessage,mTxtUserName;
        private LinearLayout mLinearLayout;

        public ItemUserViewHolder( View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTxtLastMessage = itemView.findViewById(R.id.txtLastMessage);
            mTxtUserName = itemView.findViewById(R.id.txtUserName);
            mLinearLayout = itemView.findViewById(R.id.mLinearLayout);
        }

        public void bin(User user){
            Picasso.get().load(user.getmProfilePic()).placeholder(R.drawable.ic_user).into(mImgAvatar);
            mTxtUserName.setText(user.getmUserName());
            mLinearLayout.setOnClickListener(v -> {
                mOnClickItemFragmentChat.onClickItem(getPosition(),mList);
            });
        }
    }
}
