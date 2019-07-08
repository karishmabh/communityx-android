package com.communityx.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.*
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.ProfileWorkExpAdapter
import com.communityx.models.editintroinfo.EditIntroInfoResponse
import com.communityx.models.headline.EditHeadlineRequest
import com.communityx.models.profile.*
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.*
import com.communityx.utils.AppConstant.*
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.text_headline
import kotlinx.android.synthetic.main.activity_profile.text_name
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_add_headline.*

class ProfileActivity : AppCompatActivity(), AppConstant {

    private val isOtherProfile = false
    private var profileResponse : ProfileResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        ButterKnife.bind(this)

        Utils.hideSoftKeyboard(this)
        getProfile()
        showEditIcon(!isOtherProfile)
        showAddHeadlines(true && !isOtherProfile)
        showAddAndMessageButton(isOtherProfile)
    }

    override fun onResume() {
        super.onResume()

        getProfile()
    }

    @OnClick(R.id.image_back)
    internal fun backTapped() {
        finish()
        overridePendingTransition(R.anim.anim_prev_slide_in, R.anim.anim_prev_slide_out)
    }

    @OnClick(R.id.button_add_headline)
    internal fun addHeadlineTapped() {

       showHeadlineDialog(this)
    }

    @OnClick(R.id.edit_profile)
    internal fun editProfileTapped() {
        val intent = Intent(this, EditIntroActivity::class.java)
        intent.putExtra(UserName, profileResponse)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_stay)
    }

    private fun showEditIcon(shouldShow: Boolean) {
        Utils.showHideView(edit_profile!!, shouldShow)
    }

    private fun showAddHeadlines(shouldShow: Boolean) {
        Utils.showHideView(view_add_headline!!, shouldShow)
        Utils.showHideView(text_headline!!, !shouldShow)
    }

    private fun showAddAndMessageButton(shouldShow: Boolean) {
        Utils.showHideView(view_add_msg_other!!, shouldShow)
    }

    private fun getProfile() {
        val dialog = CustomProgressBar.getInstance(this).showProgressDialog("Fetching Profile...")
        DataManager.getProfile(this, object : ResponseListener<ProfileResponse> {
            override fun onSuccess(response: ProfileResponse) {
                dialog.dismiss()

                profileResponse  = response
                setProfile(profileResponse!!.data[0])
                setAboutInfo(profileResponse!!.data[0])
            }

            override fun onError(error: Any) {
                dialog.dismiss()
                Utils.showError(this@ProfileActivity, linear_top, error)
            }
        })
    }

    private fun setProfile(profileData: Data) {
        text_name.text = profileData?.profile?.first_name + " " + profileData?.profile?.last_name
        Picasso.get().load(profileData?.profile?.profile_image).into(image_profile)
        text_title.text = profileData?.type

        if (profileData?.headline != null) {
            text_headline.text = profileData?.headline
            text_headline.visibility = View.VISIBLE
            view_add_headline.visibility = View.GONE
        }

        setFlexLayout(flex_layout_cause, profileData?.interests)
    }

    fun setFlexLayout(fLexLayout: FlexboxLayout?, interest: List<Education>) {
        fLexLayout!!.removeAllViews()

        for (i in interest.indices) {
            val checkBox = LayoutInflater.from(this).inflate(R.layout.item_interest, null) as CheckBox
            checkBox.text = interest.get(i).name
            checkBox.setBackgroundResource(com.communityx.R.drawable.bg_profile_interst)
            checkBox.setTextColor(this.resources.getColor(R.color.colorBlackTitle))

            checkBox.performClick()

            val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(10, 10, 10, 10)
            fLexLayout.addView(checkBox, lp)
        }
    }

    private fun setAboutInfo(profileData: Data) {

        var list : ArrayList<Education>  = ArrayList<Education>()

        for (e : Education in profileData?.education) {
            e.datatype = "edu"
        }
        list.addAll(profileData.education)

        for (e : Education in profileData?.clubs) {
            e.datatype = "club"
        }
        list.addAll(profileData.clubs)

        for (e : Education in profileData?.work_experience) {
            e.datatype = "we"
        }
        list.addAll(profileData.work_experience)

        val linearLayoutManager = LinearLayoutManager(this)
        recycler_work_exp!!.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(recycler_work_exp!!.context, linearLayoutManager.orientation)
        recycler_work_exp!!.addItemDecoration(dividerItemDecoration)

        val adapter = ProfileWorkExpAdapter(this, list)
        recycler_work_exp!!.adapter = adapter
    }

    fun showHeadlineDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_add_headline)
        dialog.setCancelable(true)

        val layoutParams = dialog.window!!.attributes
        val window = dialog.window
        layoutParams.copyFrom(window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = layoutParams
        layoutParams.gravity = Gravity.CENTER

        val imageClose = dialog.findViewById<ImageView>(R.id.image_close)
        val saveHeadLine = dialog.findViewById<Button>(R.id.button_continue)
        val editHeadLine = dialog.findViewById<EditText>(R.id.edit_headline)

        saveHeadLine.setOnClickListener {

            var editHeadlineRequest = EditHeadlineRequest(editHeadLine.text.toString(), AppPreference.getInstance(this).getString(
                PREF_USER_ID))

            val progressDialog = CustomProgressBar.getInstance(this).showProgressDialog("Updating headline...")
            progressDialog.show()

            DataManager.updateHeadline(this, editHeadlineRequest, object : ResponseListener<EditIntroInfoResponse> {
                override fun onSuccess(response: EditIntroInfoResponse) {
                    Toast.makeText(this@ProfileActivity, "Headline updated successfully", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                    progressDialog.dismiss()
                }

                override fun onError(error: Any) {
                    dialog.dismiss()
                    progressDialog.dismiss()
                }
            })
        }

        imageClose.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}
