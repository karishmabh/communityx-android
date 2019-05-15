package com.communityx.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.activity.NewGroupActivity;
import com.communityx.adapters.MessageUserAdapter;
import com.communityx.utils.CustomToolBarHelper;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    @BindView(R.id.recycler_user_list)
    RecyclerView recyclerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);

        setUpToolbar(view);
        setRecyclerView();
        return view;
    }

    @OnClick(R.id.image_tail_toolbar)
    void addGroupTapped() {
        startActivity(new Intent(getActivity(), NewGroupActivity.class));
    }

    private void setUpToolbar(View view){
        CustomToolBarHelper toolbarHelper = new CustomToolBarHelper(view);
        toolbarHelper.setTitle(R.string.messages);
        toolbarHelper.setImageTail(R.drawable.ic_create_crowdfunding_add);
    }

    private void setRecyclerView() {
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerList.setAdapter(new MessageUserAdapter(new ArrayList<String>(), getActivity()));
    }
}
