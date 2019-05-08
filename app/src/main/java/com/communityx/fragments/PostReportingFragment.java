package com.communityx.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;

public class PostReportingFragment extends Fragment {

    @BindView(R.id.button_post)
    Button buttonPost;
    @BindView(R.id.spinner_type_report)
    Spinner spinnerReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_reporting, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinnerReport.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_member_of_club,R.id.text_item, new String[]{"Earthquake","Floods"}));
    }
}
