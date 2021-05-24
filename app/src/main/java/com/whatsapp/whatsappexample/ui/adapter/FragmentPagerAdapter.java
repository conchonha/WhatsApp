package com.whatsapp.whatsappexample.ui.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.whatsapp.whatsappexample.ui.fragment.CallsFragment;
import com.whatsapp.whatsappexample.ui.fragment.ChatsFragment;
import com.whatsapp.whatsappexample.ui.fragment.StatusFragment;

public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

    public FragmentPagerAdapter( FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return ChatsFragment.getInstance();
            case 1: return StatusFragment.getInstance();
            case 2: return CallsFragment.getInstance();
            default: return ChatsFragment.getInstance();
        }
    }

    @Override
    public int getCount() {

        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Chats";
            case 1: return "Status";
            case 2: return "Calls";
            default: return "Chats";
        }
    }
}
