package com.communityx.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.utils.CustomToolBarHelper;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        CustomToolBarHelper customToolBarHelper = new CustomToolBarHelper(view);
        customToolBarHelper.setTitle(R.string.my_profile);
        return view;
    }
}
