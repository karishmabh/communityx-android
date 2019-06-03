package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.CommunityAlliesAdapter
import com.communityx.models.login.Data
import com.communityx.models.login.LoginRequest
import com.communityx.models.login.LoginResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.AppConstant
import com.communityx.utils.AppPreference
import com.communityx.utils.CustomProgressBar
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_connect_allies.*
import kotlinx.android.synthetic.main.layout_top_view.*
import java.util.*

class ConnectAlliesActivity : AppCompatActivity() {
    private val alliesList = ArrayList<String>()
    private var communityAlliesAdapter: CommunityAlliesAdapter? = null
    private lateinit var sessionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_allies)
        ButterKnife.bind(this)

        text_title!!.text = getString(R.string.connect_with_allias)
        text_description!!.text = getString(R.string.we_found_global)

        getIntentData()
        setAdapter(alliesList)
    }

    private fun getIntentData() {
        sessionId = intent.getStringExtra(AppConstant.SESSION_KEY)
        Toast.makeText(this,sessionId,Toast.LENGTH_LONG).show()
    }

    @OnClick(R.id.button_community)
    internal fun buttonCommunityTapped() {
      navigateActivity()
    }

    fun setAdapter(alliesList: ArrayList<String>) {
        recycler_view!!.layoutManager = LinearLayoutManager(this)
        communityAlliesAdapter = CommunityAlliesAdapter(alliesList, this)
        recycler_view!!.adapter = communityAlliesAdapter
    }

    private fun navigateActivity() {
        startActivity(Intent(this, DashboardActivity::class.java))
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        finish()
    }
}
