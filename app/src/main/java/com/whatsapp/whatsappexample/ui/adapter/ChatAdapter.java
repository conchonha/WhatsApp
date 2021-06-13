package com.whatsapp.whatsappexample.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.whatsapp.whatsappexample.R;
import com.whatsapp.whatsappexample.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {
    private List<MessageModel> mList = new ArrayList<>();
    private final int SENDER_VIEW_TYPE = 1;
    private final int RECEIVER_VIEW_TYPE = 2;

    public void updateData(List<MessageModel>list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getUID().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        }
        return RECEIVER_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case SENDER_VIEW_TYPE:
                return new ChatViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_right, parent, false));
            default: return new ChatViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_left, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatViewHodler chatViewHodler = (ChatViewHodler) holder;
        chatViewHodler.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ChatViewHodler extends RecyclerView.ViewHolder {
        private TextView txtMessage, txtTime;

        public ChatViewHodler(@NonNull View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtTime = itemView.findViewById(R.id.txtTime);
        }

        public void bind(MessageModel messageModel){
            txtMessage.setText(messageModel.getMessage());
            txtTime.setText(messageModel.getTimestamp());
        }
    }
}
