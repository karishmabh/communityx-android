package com.communityx.activity

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.ProfileInfoAdapter
import com.communityx.database.FakeDatabase
import com.communityx.utils.AppConstant

class SeeAllAboutActivity : BaseActivity(), AppConstant {

    @BindView(R.id.recycler_view)
    internal var recyclerView: RecyclerView? = null
    @BindView(R.id.fab_add)
    internal var fabAdd: FloatingActionButton? = null

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
            fabAdd!!.hide()
        }
    }

    private fun setAllInfo() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)
        val adapter = ProfileInfoAdapter(this, FakeDatabase.get().profileInfoDao.profileInfo)
        adapter.setOtherProfile(isOtherProfile)
        adapter.setFromDetialActivity(true)
        recyclerView!!.adapter = adapter
    }

    private fun openNewInfoDialog(activity: Activity) {
        fabAdd!!.hide()
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_new_info, null)
        val bottomSheetDialog = BottomSheetDialog(activity)
        bottomSheetDialog.setContentView(dialogView)

        bottomSheetDialog.findViewById<View>(R.id.image_cross)!!.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetDialog.setOnDismissListener { fabAdd!!.show() }

        bottomSheetDialog.show()
    }
}
