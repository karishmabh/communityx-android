package com.communityx.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.ProfileInfoAdapter
import com.communityx.database.FakeDatabase
import com.communityx.utils.AppConstant
import kotlinx.android.synthetic.main.activity_see_all_about.*

class SeeAllAboutActivity : BaseActivity(), AppConstant {

    private var isOtherProfile: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_about)
        ButterKnife.bind(this)

        isOtherProfile = intent.getBooleanExtra(AppConstant.IS_OTHER_PROFILE, false)
        setToolBar(this, getString(R.string.experiences), true)
        setAllInfo()
        setFloatingButtonVisibility()
    }

    @OnClick(R.id.fab_add)
    internal fun tappedAddInfo() {
        openNewInfoDialog(this)
    }

    private fun setFloatingButtonVisibility() {
        if (isOtherProfile) {
            fab_add!!.hide()
        }
    }

    private fun setAllInfo() {
        val linearLayoutManager = LinearLayoutManager(this)
        recycler_view!!.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        recycler_view!!.addItemDecoration(dividerItemDecoration)
        val adapter = ProfileInfoAdapter(this, FakeDatabase.get().profileInfoDao.profileInfo)
        adapter.setOtherProfile(isOtherProfile)
        adapter.setFromDetialActivity(true)
        recycler_view!!.adapter = adapter
    }

    private fun openNewInfoDialog(activity: Activity) {
        fab_add!!.hide()
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_new_info, null)
        val bottomSheetDialog = BottomSheetDialog(activity)
        bottomSheetDialog.setContentView(dialogView)

        bottomSheetDialog.findViewById<View>(R.id.image_add_work)!!.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@SeeAllAboutActivity, AddWorkExperienceActivity::class.java))
            }
        })

        bottomSheetDialog.findViewById<View>(R.id.image_add_volunteer)!!.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@SeeAllAboutActivity, AddVolunteerActivity::class.java))
            }
        })

        bottomSheetDialog.findViewById<View>(R.id.image_cross)!!.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetDialog.setOnDismissListener {
            fab_add!!.show()
        }

        bottomSheetDialog.show()
    }
}
