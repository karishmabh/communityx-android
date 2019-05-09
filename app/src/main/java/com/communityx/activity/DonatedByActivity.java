package com.communityx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.DonatedByAdapter;
import com.communityx.utils.CustomToolBarUtils;

import java.util.ArrayList;

public class DonatedByActivity extends AppCompatActivity {
    @BindView(R.id.recycler_likes)
    RecyclerView recyclerLikes;
    private ArrayList<String> likesList = new ArrayList<>();
    private DonatedByAdapter donatedByAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donated_by);
        ButterKnife.bind(this);
        setAdapter(likesList);
        setupToolbar();

    }

    public void setAdapter(ArrayList<String> donatedByList) {
        recyclerLikes.setLayoutManager(new LinearLayoutManager(this));
        donatedByAdapter = new DonatedByAdapter(donatedByList, DonatedByActivity.this);
        recyclerLikes.setAdapter(donatedByAdapter);
    }

  private void setupToolbar() {
      CustomToolBarUtils customToolBarUtils = new CustomToolBarUtils(this);
      customToolBarUtils.setTitle("Donated by (219)");
      customToolBarUtils.setLogoIcon(R.drawable.ic_praise_back_arrow);
      customToolBarUtils.getImageLogo().setOnClickListener(v -> finish());
    }
}

