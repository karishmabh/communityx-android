package com.communityx.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.communityx.R
import com.communityx.utils.CustomToolBarHelper

open class BaseActivity : AppCompatActivity() {

    protected fun setToolBar(activity: Activity, title: String, backPress: Boolean) {
        val customToolBarHelper = CustomToolBarHelper(activity)
        customToolBarHelper.setTitle(title)
        if (backPress) customToolBarHelper.enableBackPress()
    }

    protected fun setToolBar(activity: Activity, title: String, backPress: Boolean, changeicon: Boolean) {
        val customToolBarHelper = CustomToolBarHelper(activity)
        customToolBarHelper.setTitle(title)

        if (backPress) customToolBarHelper.enableBackPress()

        if (changeicon)
        customToolBarHelper.setLogoIcon(R.drawable.ic_cross)
    }

    protected fun setGroupToolbar(activity: Activity?, title: String, subtitle: String) {
        if (activity == null) return
        val textTitle = activity.findViewById<TextView>(R.id.text_title)
        val textSubtitle = activity.findViewById<TextView>(R.id.text_subtitle)
        val imageBack = activity.findViewById<ImageView>(R.id.imageBack)
        imageBack.setOnClickListener { v -> activity.onBackPressed() }

        textTitle.text = title
        textSubtitle.text = subtitle
    }
}
