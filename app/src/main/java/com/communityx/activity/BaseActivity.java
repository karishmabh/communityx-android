package com.communityx.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.communityx.R;
import com.communityx.utils.CustomToolBarHelper;

public class BaseActivity extends AppCompatActivity {

    protected void setToolBar(Activity activity, String title, boolean backPress){
        CustomToolBarHelper customToolBarHelper = new CustomToolBarHelper(activity);
        customToolBarHelper.setTitle(title);
        if(backPress) customToolBarHelper.enableBackPress();
    }

    protected void setGroupToolbar(Activity activity, String title, String subtitle){
        if(activity == null) return;
        TextView textTitle = activity.findViewById(R.id.text_title);
        TextView textSubtitle = activity.findViewById(R.id.text_subtitle);
        ImageView imageBack = activity.findViewById(R.id.imageBack);
        imageBack.setOnClickListener((v) -> activity.onBackPressed());

        textTitle.setText(title);
        textSubtitle.setText(subtitle);
    }
}
