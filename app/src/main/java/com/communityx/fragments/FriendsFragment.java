package com.communityx.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.MyAllFriendsAdapter;
import com.communityx.viewModels.DashboardActivtyViewModel;

import java.util.Objects;

public class FriendsFragment extends Fragment {

    @BindView(R.id.recycler_my_friends)
    RecyclerView recyclerViewFriends;
    @BindString(R.string.all_friends)
    String allFriends;

    private MyAllFriendsAdapter myAllFriendsAdapter;
    private DashboardActivtyViewModel viewModel;
    private MyAllFriendsFragment parentFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentFragment = (MyAllFriendsFragment) getParentFragment();

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DashboardActivtyViewModel.class);
        initAllFriends();
        viewModel.getFakeAllMyFriends().observe(getActivity(), strings -> myAllFriendsAdapter.notifyDataSetChanged());
    }

    private void initAllFriends() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewFriends.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFriends.getContext(),linearLayoutManager.getOrientation());
        recyclerViewFriends.addItemDecoration(dividerItemDecoration);
        myAllFriendsAdapter = new MyAllFriendsAdapter(getActivity(),viewModel.getFakeAllMyFriends().getValue());
        recyclerViewFriends.setAdapter(myAllFriendsAdapter);
    }
}
