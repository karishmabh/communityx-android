package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.CommunityAlliesAdapter
import com.communityx.models.connect_allies.ProfileData
import com.communityx.models.signup.SignUpResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.AppConstant.ACCEPTED
import com.communityx.utils.CustomProgressBar
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_connect_allies.*
import kotlinx.android.synthetic.main.layout_top_view.*

class ConnectAlliesActivity : AppCompatActivity(), CommunityAlliesAdapter.IOnAddFriendClicked {
    private var communityAlliesAdapter: CommunityAlliesAdapter? = null
    private var listProfileData: ArrayList<ProfileData> = ArrayList()

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
                listProfileData = alliesList as ArrayList<ProfileData>
                if (alliesList.size > 0) {
                    setAdapter(listProfileData)
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
        communityAlliesAdapter = CommunityAlliesAdapter(alliesList, this, this)
        recycler_view.adapter = communityAlliesAdapter
    }

    private fun navigateActivity() {
        startActivity(Intent(this, DashboardActivity::class.java))
        overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        finish()
    }

    private fun sendInvitation(id: String, position: Int, imageView: ImageView) {
        var dialog = CustomProgressBar.getInstance(this).showProgressDialog("sending invitation..")
        dialog.show()

        DataManager.sendInvitation(this, id, object : ResponseListener<SignUpResponse> {
            override fun onSuccess(response: SignUpResponse) {
                dialog.dismiss()

                imageView.setImageResource(R.drawable.ic_suggestion_friend_sent_request)
                Toast.makeText(this@ConnectAlliesActivity, "Request Sent successfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Any) {
                dialog.dismiss()
                Utils.showError(this@ConnectAlliesActivity, constraint_layout, error)
            }
        })
    }

    override fun sendInvitationTapped(id: String, position: Int, imageView: ImageView) {
        sendInvitation(id, position, imageView)
    }
}
