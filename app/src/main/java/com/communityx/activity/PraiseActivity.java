package com.communityx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import com.communityx.R;
import com.communityx.adapters.PraiseAdapter;
import com.communityx.utils.CustomToolBarUtils;

public class PraiseActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_praise)
    RecyclerView recyclerView;
    @BindView(R.id.image_send)
    ImageView imageSend;
    @BindView(R.id.edit_type)
    EditText editType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praise);
        ButterKnife.bind(this);

        setUpToolbar();
        setPraiseData();
    }

    @OnTextChanged(R.id.edit_type)
    void onTyping(CharSequence s){
        imageSend.setImageResource(s.length() != 0 ? R.drawable.ic_praise_share_select : R.drawable.ic_praise_share_deselect);
    }

    private void setPraiseData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PraiseAdapter(this));
    }

    private void setUpToolbar() {
        CustomToolBarUtils customToolBarUtils = new CustomToolBarUtils(this);
        customToolBarUtils.setLogoIcon(R.drawable.ic_praise_back_arrow);
        customToolBarUtils.setTitle(R.string.praise);
        customToolBarUtils.getImageLogo().setOnClickListener(v -> finish());
    }
}
