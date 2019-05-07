package com.communityx.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.activity.ConnectAlliesActivity;
import com.communityx.adapters.CommunityAlliesAdapter;
import com.communityx.adapters.InvitationAdapter;

import java.util.ArrayList;


public class InvitationFragment extends Fragment {
    private ArrayList<String> invitationList = new ArrayList<>();
    private InvitationAdapter invitationAdapter;

    @BindView(R.id.recycler_invitation_list)
    RecyclerView recyclerInvitationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invitation, container, false);
        ButterKnife.bind(this,view);
        setAdapter(invitationList);

        return view;
    }

    public void setAdapter(ArrayList <String> mInvitationList ) {
        recyclerInvitationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        invitationAdapter = new InvitationAdapter(mInvitationList, getActivity());
        recyclerInvitationList.setAdapter(invitationAdapter);
    }
}
