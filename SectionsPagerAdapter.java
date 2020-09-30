package com.example.conormcadorey.chatbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //Position of each tab
    @Override
    public Fragment getItem(int position) {

        switch(position)    {
            case 0:
                FindChatters findChatters = new FindChatters();
                return findChatters;

            case 1:
                MyChats myChats = new MyChats();
                return myChats;

            case 2:
                MyLocation myLocation = new MyLocation();
                return myLocation;

            default:
                    return null;

        }

    }

    //Number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    //name tabs
    public CharSequence getPageTitle(int position)  {

        switch (position)   {

            case 0:
                return "CHAT RADAR";

            case 1:
                return "MY CHATS";

            case 2:
                return "MY LOCATION";

            default:
                return null;

        }

    }
}
