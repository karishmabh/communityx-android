package com.communityx.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import com.communityx.utils.CustomToolBarHelper;

public class BaseActivity extends AppCompatActivity {

    protected void setToolBar(Activity activity, String title, boolean backPress){
        CustomToolBarHelper customToolBarHelper = new CustomToolBarHelper(activity);
        customToolBarHelper.setTitle(title);
        if(backPress) customToolBarHelper.enableBackPress();
    }
}
