package com.communityx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.DonatedCreatorAdapter;
import com.communityx.utils.CustomToolBarUtils;

import java.util.ArrayList;

public class DonatedCreatedActivity extends AppCompatActivity {

    private ArrayList<String> mDonatedList = new ArrayList<>();
    private DonatedCreatorAdapter donatedCreatorAdapter;

    @BindView(R.id.recycler_donated)
    RecyclerView recyclerDonated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donated_created);
        ButterKnife.bind(this);
        setupToolbar();
        setAdapter(mDonatedList);
    }

    private void setAdapter(ArrayList<String> donatedList ) {
        recyclerDonated.setLayoutManager(new LinearLayoutManager(this));
        donatedCreatorAdapter = new DonatedCreatorAdapter(donatedList, DonatedCreatedActivity.this);
        recyclerDonated.setAdapter(donatedCreatorAdapter);
    }

    private void setupToolbar() {
        CustomToolBarUtils customToolBarUtils = new CustomToolBarUtils(this);
        customToolBarUtils.setTitle("Donated by (219)");
        customToolBarUtils.setLogoIcon(R.drawable.ic_praise_back_arrow);
        customToolBarUtils.getImageLogo().setOnClickListener(v -> finish());
    }
}
