package com.communityx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.adapters.ChatAdapter;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.recycler_chat)
    RecyclerView recyclerViewChat;
    @BindView(R.id.edit_chat)
    EditText editChat;

    private ArrayList<String> mChatList;
    private ChatAdapter mChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        setRecyclerViewChat();
        setEditChatLocationClick();
    }

    private void setEditChatLocationClick() {
        editChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editChat.getRight() - editChat.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        startActivityForResult(new Intent(MessageActivity.this, SendLocationActivity.class), 101);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            try {
                String filename = data.getExtras().getString("bitmap");
                mChatList.add(filename);
                mChatAdapter.notifyDataSetChanged();
                recyclerViewChat.scrollToPosition(mChatList.size() - 1);
            } catch (Exception e) {

            }
        }
    }

    @OnClick(R.id.imageBack)
    void backTapped() {
        super.onBackPressed();
    }

    private void setRecyclerViewChat() {
        mChatList = new ArrayList<>();
        mChatList.add("S");
        mChatList.add("S");
        mChatList.add("S");
        mChatList.add("S");
        mChatList.add("d");
        mChatList.add("R");
        mChatList.add("R");
        mChatList.add("R");
        mChatList.add("R");

        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        mChatAdapter = new ChatAdapter(MessageActivity.this, mChatList);
        recyclerViewChat.setAdapter(mChatAdapter);
    }

    @OnClick(R.id.topView)
    void tappedToolbar(){
        startActivity(new Intent(this, GroupInfoActivity.class));
    }
}
