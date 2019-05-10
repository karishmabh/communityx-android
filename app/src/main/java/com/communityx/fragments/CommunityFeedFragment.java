package com.communityx.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import com.communityx.R;
import com.communityx.activity.CreatePostActivity;
import com.communityx.adapters.CommunityFeedAdapter;
import com.communityx.utils.CustomToolBarUtils;

public class CommunityFeedFragment extends Fragment {

    @BindView(R.id.recycler_view_feed)
    RecyclerView recyclerView;
    @BindView(R.id.edit_search)
    EditText editPost;

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

        editPost.setHint(R.string.write_something_here);
        editPost.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        customToolBarUtils.setTitle(R.string.my_community);
        customToolBarUtils.setImageTail(R.drawable.ic_my_community_nav_filter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        communityFeedAdapter = new CommunityFeedAdapter(getContext());
        recyclerView.setAdapter(communityFeedAdapter);
    }

    @OnTouch(R.id.edit_search)
    boolean tappedCreatePost(View view, MotionEvent event){
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(getContext(), CreatePostActivity.class));
            return true;
        }
        return false;
    }
}
