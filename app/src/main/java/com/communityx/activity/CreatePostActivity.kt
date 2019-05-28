package com.communityx.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.fragments.CreateEventFragment
import com.communityx.fragments.CreatePostContentFragment
import com.communityx.fragments.CrowdFundingFragment
import com.communityx.fragments.PostReportingFragment
import com.communityx.utils.CustomToolBarHelper
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        ButterKnife.bind(this)
        setUpToobar()
        postContentClicked()
    }

    private fun setUpToobar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle(R.string.create_post)
        customToolBarUtils.enableBackPress()
    }

    @OnClick(R.id.image_content, R.id.text_content)
    internal fun postContentClicked() {
        Utils.replaceFragment(this, CreatePostContentFragment(), false, "fragment_post")
        makeActiveText(text_content!!, image_content!!, R.drawable.ic_create_post_content_select)
    }

    @OnClick(R.id.text_reporting, R.id.image_reporting)
    internal fun postReportingClicked() {
        Utils.replaceFragment(this, PostReportingFragment(), false, "fragment_post")
        makeActiveText(text_reporting!!, image_reporting!!, R.drawable.ic_create_post_reporting_select)
    }

    @OnClick(R.id.text_crowdfunding, R.id.image_crowdfunding)
    internal fun postCrowdfundingClicked() {
        Utils.replaceFragment(this, CrowdFundingFragment(), false, "fragment_post")
        makeActiveText(text_create_event!!, image_crowdfunding!!, R.drawable.ic_create_post_crowdfunding_select)
    }

    @OnClick(R.id.text_create_event, R.id.image_create_event)
    internal fun createEventClicked() {
        Utils.replaceFragment(this, CreateEventFragment(), false, "fragment_post")
        makeActiveText(text_create_event!!, image_create_event!!, R.drawable.ic_create_post_event_select)
    }

    @OnClick(R.id.text_create_event, R.id.image_create_event)
    internal fun postCreateEvent() {
        makeActiveText(text_create_event!!, image_create_event!!, R.drawable.ic_create_post_event_select)
    }

    private fun makeActiveText(activeText: TextView, activeImage: ImageView, imgRes: Int) {
        image_content!!.setImageResource(R.drawable.ic_create_post_content_deselect)
        text_content!!.setTextColor(resources.getColor(R.color.colorLightGrey))
        image_reporting!!.setImageResource(R.drawable.ic_create_post_reporting_deselect)
        text_reporting!!.setTextColor(resources.getColor(R.color.colorLightGrey))
        image_crowdfunding!!.setImageResource(R.drawable.ic_create_post_crowdfunding_deselect)
        text_crowdfunding!!.setTextColor(resources.getColor(R.color.colorLightGrey))
        image_create_event!!.setImageResource(R.drawable.ic_create_post_event_deselect)
        text_create_event!!.setTextColor(resources.getColor(R.color.colorLightGrey))

        activeImage.setImageResource(imgRes)
        activeText.setTextColor(resources.getColor(R.color.colorBlackTitle))
    }
}
