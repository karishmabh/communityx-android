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
import com.communityx.adapters.EventAdapter;

public class MyHostedEventFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_hosted_event, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRecyclerEventData();
    }

    private void setRecyclerEventData() {
        EventAdapter eventAdapter = new EventAdapter(getContext());
        recyclerViewEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEvent.setAdapter(eventAdapter);
    }
}
