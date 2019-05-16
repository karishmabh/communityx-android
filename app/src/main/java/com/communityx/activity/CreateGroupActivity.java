package com.communityx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.adapters.SelectedMembersAdapter;
import com.communityx.utils.AppConstant;

import java.util.ArrayList;

public class CreateGroupActivity extends AppCompatActivity implements AppConstant , SelectedMembersAdapter.IUserRemoved {

    @BindView(R.id.recycler_added_users)
    RecyclerView recyclerAddedUsers;
    @BindView(R.id.text_participant)
    TextView textParticipant;

    private ArrayList mUsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);

        getIntentData();
    }


    @OnClick(R.id.imageBack)
    void backTapped() {
        super.onBackPressed();
    }

    private void getIntentData() {
        mUsersList = (ArrayList<String>) getIntent().getExtras().get(INTENT_USERLIST);

        if (mUsersList == null) return;
        recyclerAddedUsers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SelectedMembersAdapter selectedMembersAdapter = new SelectedMembersAdapter(mUsersList, this, this);
        recyclerAddedUsers.setAdapter(selectedMembersAdapter);

        textParticipant.setText("Participants: " + mUsersList.size());
    }

    @Override
    public void onUserRemoved(String userName) {

    }
}
