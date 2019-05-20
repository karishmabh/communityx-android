package com.communityx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.adapters.GroupAdapter;
import com.communityx.adapters.SelectedMembersAdapter;
import com.communityx.utils.AppConstant;

import java.util.ArrayList;
import java.util.Set;

public class NewGroupActivity extends AppCompatActivity implements GroupAdapter.IUsersSelected , SelectedMembersAdapter.IUserRemoved , AppConstant {

    @BindView(R.id.recycler_user_list)
    RecyclerView recyclerUsers;
    @BindView(R.id.recycler_added_users)
    RecyclerView recyclerAddedUsers;
    @BindView(R.id.image_next)
    FloatingActionButton imageNext;

    private SelectedMembersAdapter selectedMembersAdapter ;
    private GroupAdapter groupAdapter;
    private ArrayList<String> selectedUsersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        ButterKnife.bind(this);

        imageNext.hide();
        setRecyclerViewChat();
    }

    @OnClick(R.id.imageBack)
    void backTapped() {
        super.onBackPressed();
    }

    @OnClick(R.id.image_next)
    void proceedToNext() {
        selectedUsersList.clear();
        selectedUsersList.addAll(groupAdapter.getSelectedUsersList());

        startActivity(new Intent(this, CreateGroupActivity.class)
        .putExtra(INTENT_USERLIST, selectedUsersList));
    }

    private void setRecyclerViewChat() {
        //TODO hard codes strings

        selectedMembersAdapter = new SelectedMembersAdapter(selectedUsersList, this, this);
        recyclerAddedUsers.setAdapter(selectedMembersAdapter);

        ArrayList<String> usersList = new ArrayList<>();
        usersList.add("Anthony");
        usersList.add("Amar");
        usersList.add("Akbar");
        usersList.add("Anamika");
        usersList.add("Bthony");
        usersList.add("Bony");
        usersList.add("Cony");
        usersList.add("Cinthony");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerUsers.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerUsers.getContext(),linearLayoutManager.getOrientation());
        recyclerUsers.addItemDecoration(dividerItemDecoration);

        groupAdapter = new GroupAdapter(usersList, NewGroupActivity.this, this);
        recyclerUsers.setAdapter(groupAdapter);
    }

    @Override
    public void updatedUsersList(Set<String> mSelectedUsers) {
        if (mSelectedUsers.size() > 0) {
            selectedUsersList.clear();
            selectedUsersList.addAll(mSelectedUsers);

            recyclerAddedUsers.setVisibility(View.VISIBLE);
            imageNext.show();
            recyclerAddedUsers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedMembersAdapter.notifyDataSetChanged();
        } else {
            imageNext.hide();
            recyclerAddedUsers.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserRemoved(String userName) {
        groupAdapter.uncheckUser(userName, imageNext);
    }
}
