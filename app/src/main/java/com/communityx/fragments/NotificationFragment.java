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
import com.communityx.adapters.NotificationAlertAdapter;
import com.communityx.utils.CustomToolBarHelper;

public class NotificationFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerNotfications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpToolBar();
        setUpNotificationData();
    }

    private void setUpToolBar() {
        CustomToolBarHelper toolBarHelper = new CustomToolBarHelper(getView());
        toolBarHelper.setTitle(R.string.activity);
    }

    private void setUpNotificationData() {
        recyclerNotfications.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerNotfications.setAdapter(new NotificationAlertAdapter(getActivity()));
    }
}
