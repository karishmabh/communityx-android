package com.communityx.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.adapters.ChatAdapter;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.recycler_chat)
    RecyclerView recyclerViewChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        setRecyclerViewChat();
    }

    @OnClick(R.id.imageBack)
    void backTapped() {
       super.onBackPressed();
    }

    private void setRecyclerViewChat() {
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(new ChatAdapter(MessageActivity.this, new ArrayList<String>()));
    }

    @OnClick(R.id.topView)
    void tappedToolbar(){
        startActivity(new Intent(this, GroupInfoActivity.class));
    }
}
