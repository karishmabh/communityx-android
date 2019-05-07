package com.communityx.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.CommunityFeedAdapter;
import com.communityx.utils.CustomToolBarUtils;

public class CommunityFeedFragment extends Fragment {

    @BindView(R.id.recycler_view_feed)
    RecyclerView recyclerView;

    private CommunityFeedAdapter communityFeedAdapter;
    private CustomToolBarUtils customToolBarUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_feed, container, false);
        ButterKnife.bind(this,view);
        customToolBarUtils = new CustomToolBarUtils(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customToolBarUtils.setTitle(R.string.my_community);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        communityFeedAdapter = new CommunityFeedAdapter(getContext());
        recyclerView.setAdapter(communityFeedAdapter);
    }
}
