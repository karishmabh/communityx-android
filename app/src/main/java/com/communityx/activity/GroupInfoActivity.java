package com.communityx.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.GroupInfoAdapter;
import com.communityx.utils.Utils;

public class GroupInfoActivity extends BaseActivity {

    @BindView(R.id.image_tail_one)
    ImageView imageEdit;
    @BindView(R.id.image_tail_two)
    ImageView imageAdd;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        ButterKnife.bind(this);

        setGroupToolbar(this,"Charity Life", "Created by You, today at 7:15 pm");
        setUpRecyclerData();
    }

    private void setUpRecyclerData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GroupInfoAdapter(this));
    }
}
