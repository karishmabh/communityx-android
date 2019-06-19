package com.communityx.activity

import android.os.Bundle
import butterknife.ButterKnife
import com.communityx.R

class AddVolunteerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_volunteer)
        ButterKnife.bind(this)
        setToolBar(this, resources.getString(R.string.add_volunteer_experience), true, true)
    }
}
