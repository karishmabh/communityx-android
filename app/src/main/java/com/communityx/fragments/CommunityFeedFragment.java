package com.communityx.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import com.communityx.R;
import com.communityx.activity.CreatePostActivity;
import com.communityx.activity.DashboardActivity;
import com.communityx.adapters.CommunityFeedAdapter;
import com.communityx.utils.CustomToolBarHelper;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommunityFeedFragment extends Fragment {

    @BindView(R.id.recycler_view_feed)
    RecyclerView recyclerView;
    @BindView(R.id.edit_search)
    EditText editPost;
    @BindView(R.id.image_user_profile)
    CircleImageView imageUser;
    @BindView(R.id.card)
    CardView cardPost;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_feed, container, false);
        ButterKnife.bind(this,view);
        setUpToolbar(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editPost.setHint(R.string.write_something_here);
        editPost.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CommunityFeedAdapter communityFeedAdapter = new CommunityFeedAdapter(getContext());
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

    @OnClick(R.id.image_user_profile)
    void userImageTapped(){
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.fragment_transition));
        assert getFragmentManager() != null;
        getFragmentManager()
                .beginTransaction()
                .addSharedElement(imageUser, Objects.requireNonNull(ViewCompat.getTransitionName(imageUser)))
                .addSharedElement(cardPost, Objects.requireNonNull(ViewCompat.getTransitionName(cardPost)))
                .replace(R.id.frame_root, profileFragment)
                .addToBackStack("fragment_profile")
                .commit();
        DashboardActivity activity = (DashboardActivity) getActivity();
        activity.hasGoneToProfileViewImage =true;
        activity.bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
    }

    private void setUpToolbar(View view){
        CustomToolBarHelper toolbarHelper = new CustomToolBarHelper(view);
        toolbarHelper.setTitle(R.string.my_community);
        toolbarHelper.setImageTail(R.drawable.ic_my_community_nav_filter);
    }
}
