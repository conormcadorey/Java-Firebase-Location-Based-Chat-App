package com.example.conormcadorey.chatbox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindChatters extends Fragment {

    private RecyclerView mUsersList;
    private View mMainView;

    public FindChatters() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_find_chatters, container, false);

        mUsersList = (RecyclerView) mMainView.findViewById(R.id.users_list);


        return mMainView;
    }

}
