package com.communityx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.communityx.R;
import com.communityx.adapters.LikesAdapter;

import java.util.ArrayList;

public class LikesActivity extends AppCompatActivity {
    @BindView(R.id.recycler_likes)
    RecyclerView recyclerLikes;
    private ArrayList<String> likesList = new ArrayList<>();
    private LikesAdapter likesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        setAdapter(likesList);
    }

    public void setAdapter(ArrayList<String> likesList) {
        recyclerLikes.setLayoutManager(new LinearLayoutManager(this));
        likesAdapter = new LikesAdapter(likesList, LikesActivity.this);
        recyclerLikes.setAdapter(likesAdapter);
    }
}

