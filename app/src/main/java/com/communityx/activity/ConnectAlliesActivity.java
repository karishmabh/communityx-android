package com.communityx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.adapters.CommunityAlliesAdapter;

import java.util.ArrayList;

public class ConnectAlliesActivity extends AppCompatActivity {
    private ArrayList<String> alliesList = new ArrayList<>();
    private CommunityAlliesAdapter communityAlliesAdapter;

    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_description)
    TextView textDescription;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindString(R.string.connect_with_allias)
    String textAllias;
    @BindString(R.string.we_found_global)
    String textTitleDescription ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_allies);
        ButterKnife.bind(this);

        textTitle.setText(textAllias);
        textDescription.setText(textTitleDescription);

        setAdapter(alliesList);
    }

    public void setAdapter(ArrayList<String> alliesList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        communityAlliesAdapter = new CommunityAlliesAdapter(alliesList, ConnectAlliesActivity.this);
        recyclerView.setAdapter(communityAlliesAdapter);
    }
}
