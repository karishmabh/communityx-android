package com.communityx.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.activity.ProfileActivity;
import com.communityx.utils.CustomToolBarHelper;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    @BindView(R.id.image_user_profile)
    CircleImageView imageUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        CustomToolBarHelper customToolBarHelper = new CustomToolBarHelper(view);
        customToolBarHelper.setTitle(R.string.my_profile);
        return view;
    }

    @OnClick(R.id.button_view_profile)
    void tappedProfile(){
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                Objects.requireNonNull(getActivity()),
                imageUser,
                Objects.requireNonNull(ViewCompat.getTransitionName(imageUser)));
        Objects.requireNonNull(getContext()).startActivity(intent, options.toBundle());
    }
}
