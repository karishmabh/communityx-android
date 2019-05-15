package com.communityx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.GroupAdapter;

import java.util.ArrayList;

public class NewGroupActivity extends AppCompatActivity {

    @BindView(R.id.recycler_user_list)
    RecyclerView recyclerUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        ButterKnife.bind(this);

        setRecyclerViewChat();
    }

    private void setRecyclerViewChat() {
        //TODO hard codes strings
        ArrayList<String> usersList = new ArrayList<>();
        usersList.add("Anthony");
        usersList.add("Amar");
        usersList.add("Akbar");
        usersList.add("Anamika");
        usersList.add("Bthony");
        usersList.add("Bony");
        usersList.add("Cony");
        usersList.add("Cinthony");

        recyclerUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerUsers.setAdapter(new GroupAdapter(usersList, NewGroupActivity.this));
    }
}
