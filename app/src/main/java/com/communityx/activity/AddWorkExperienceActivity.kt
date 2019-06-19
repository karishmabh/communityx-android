package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import kotlinx.android.synthetic.main.activity_add_work_experience.*

class AddWorkExperienceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work_experience)
        ButterKnife.bind(this)
        setToolBar(this, resources.getString(R.string.add_work_exp), true, true)
    }

    @OnClick(R.id.edit_company_name)
    fun onSelectName() {
        val intent = Intent(this, SearchSuggestionActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                edit_company_name,
                resources.getString(R.string.anim_start))
       ActivityCompat.startActivity(this, intent, options.toBundle())
    }
}
