package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.CommunityAlliesAdapter
import com.communityx.models.connect_allies.ProfileData
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.CustomProgressBar
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_connect_allies.*
import kotlinx.android.synthetic.main.layout_top_view.*

class ConnectAlliesActivity : AppCompatActivity() {
    private var communityAlliesAdapter: CommunityAlliesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_allies)
        ButterKnife.bind(this)

        text_title.text = getString(R.string.connect_with_allias)
        text_description.text = getString(R.string.we_found_global)

        getSuggestionsList()
    }

    @OnClick(R.id.button_community)
    internal fun buttonCommunityTapped() {
        navigateActivity()
    }

    private fun getSuggestionsList() {
        val dialog = CustomProgressBar.getInstance(this).showProgressDialog("loading...")
        DataManager.getConnectingAllies(this, object : ResponseListener<List<ProfileData>> {
            override fun onSuccess(response: List<ProfileData>) {
                dialog.dismiss()

                val alliesList = response
                if (alliesList.size > 0) {
                    setAdapter(alliesList)
                } else {
                    text_record_not_found.visibility = View.VISIBLE
                    recycler_view.visibility = View.GONE
                }
            }

            override fun onError(error: Any) {
                dialog.dismiss()
                Utils.showError(this@ConnectAlliesActivity, constraint_layout, error)
            }
        })
    }

    fun setAdapter(alliesList: List<ProfileData>) {
        recycler_view.layoutManager = LinearLayoutManager(this)
        communityAlliesAdapter = CommunityAlliesAdapter(alliesList, this)
        recycler_view.adapter = communityAlliesAdapter
    }

    private fun navigateActivity() {
        startActivity(Intent(this, DashboardActivity::class.java))
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        finish()
    }
}
