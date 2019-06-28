package com.communityx.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.SelectedInterestAdapter
import com.communityx.models.editinfo.Data
import com.communityx.models.editinfo.EditInfoInterestResponse
import com.communityx.models.signup.MinorsData
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.network.serviceRepo.SignUpRepo
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_sign_up_select_interest.*
import kotlinx.android.synthetic.main.toolbar.*

class EditIntroActivity : AppCompatActivity() {

    private lateinit var mInterestAdapter : SelectedInterestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_intro)
        ButterKnife.bind(this)

        setToolbar()
        loadInterest()
    }

    @OnClick(R.id.imageView)
    fun closeTapped() {
        finish()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
    }

    private fun setToolbar() {
        image_tail_toolbar.visibility = View.GONE
        text_title.text = getString(R.string.string_edit_profile)
        imageView.setImageDrawable(resources.getDrawable(R.drawable.image_close_white))
    }

    private fun loadInterest() {
        DataManager.getInterests(this, object : ResponseListener<EditInfoInterestResponse> {
            override fun onSuccess(response: EditInfoInterestResponse) {

                var list : List<Data> = response.data

                setRecycler(list)
            }

            override fun onError(error: Any) {
                Utils.showError(this@EditIntroActivity, scrollView, error)
            }
        })
    }

    private fun setRecycler(response: List<Data>) {
        /*recycler_interests.layoutManager = LinearLayoutManager(this)
        mInterestAdapter = SelectedInterestAdapter(response, this!!, scrollView)
        recycler_interests.adapter = mInterestAdapter*/
    }
}
