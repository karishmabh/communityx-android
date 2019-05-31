package com.communityx.fragments


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.activity.EventActivity
import com.communityx.activity.LoginActivity
import com.communityx.activity.ProfileActivity
import com.communityx.models.logout.LogoutResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

import java.util.Objects

class ProfileFragment : Fragment(), AppConstant {

    @BindView(R.id.image_user_profile)
    internal var imageUser: CircleImageView? = null
    @BindView(R.id.linear_top)
    internal var linearTop: LinearLayout? = null
    @BindView(R.id.text_name)
    internal var textName: TextView? = null
    @BindView(R.id.text_title)
    internal var textTitle: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        ButterKnife.bind(this, view)
        val customToolBarHelper = CustomToolBarHelper(view)
        customToolBarHelper.setTitle(R.string.my_profile)

        initializeProfileData()
        return view
    }

    @OnClick(R.id.button_view_profile, R.id.image_user_profile)
    internal fun tappedProfile() {
        val intent = Intent(context, ProfileActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            Objects.requireNonNull<FragmentActivity>(activity),
            imageUser!!,
            Objects.requireNonNull<String>(ViewCompat.getTransitionName(imageUser!!))
        )
        Objects.requireNonNull<Context>(context).startActivity(intent, options.toBundle())
    }

    @OnClick(R.id.image_event_arrow, R.id.image_crowdfunding_arrow, R.id.image_logout_arrow)
    internal fun tappedOptions(it: View) {
        if (it.id == R.id.image_event_arrow) {
            startActivity(Intent(context, EventActivity::class.java))
            Objects.requireNonNull<FragmentActivity>(activity)
                .overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        }
    }

    @OnClick(R.id.constraint_logout)
    internal fun tappedLogout() {
        DialogHelper.showLogoutDialog(activity, object : DialogHelper.IDialogCallback {
            override fun onConfirmed() {
                doLogout()
            }

            override fun onDenied() {}
        })
    }

    private fun initializeProfileData() {
        if (!isAdded) return

        val image = AppPreference.getInstance(activity!!).getString(AppConstant.PREF_USERIMAGE)
        if (!TextUtils.isEmpty(image)) {
            Picasso.get().load(image).into(imageUser)
        }

        textName!!.text = AppPreference.getInstance(activity!!).getString(AppConstant.PREF_EMAIL)
        textTitle!!.text = AppPreference.getInstance(activity!!).getString(AppConstant.PREF_PROFESSION)
    }

    private fun doLogout() {
        if (activity == null) return
        val dialog = CustomProgressBar.getInstance(activity!!).showProgressDialog("Logging out..")
        DataManager.doLogOut(activity!!, object : ResponseListener<LogoutResponse> {
            override fun onSuccess(response: LogoutResponse) {
                dialog.dismiss()

                AppPreference.getInstance(activity!!).clearSession()
                startActivity(
                    Intent(
                        activity,
                        LoginActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                Toast.makeText(activity, "You have been Logout successfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Any) {
                dialog.dismiss()
                Utils.showError(activity, linearTop, error)
            }
        })
    }
}
