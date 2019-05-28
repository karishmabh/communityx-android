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

class CreatePostActivity : AppCompatActivity() {

    @BindView(R.id.image_content)
    internal var imageContent: ImageView? = null
    @BindView(R.id.text_content)
    internal var textContent: TextView? = null
    @BindView(R.id.image_reporting)
    internal var imageReporting: ImageView? = null
    @BindView(R.id.text_reporting)
    internal var textReporting: TextView? = null
    @BindView(R.id.image_crowdfunding)
    internal var imageCrowdfunding: ImageView? = null
    @BindView(R.id.text_crowdfunding)
    internal var textCrowdfunding: TextView? = null
    @BindView(R.id.image_create_event)
    internal var imageEvent: ImageView? = null
    @BindView(R.id.text_create_event)
    internal var textEvent: TextView? = null

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
        makeActiveText(textContent!!, imageContent!!, R.drawable.ic_create_post_content_select)
    }

    @OnClick(R.id.text_reporting, R.id.image_reporting)
    internal fun postReportingClicked() {
        Utils.replaceFragment(this, PostReportingFragment(), false, "fragment_post")
        makeActiveText(textReporting!!, imageReporting!!, R.drawable.ic_create_post_reporting_select)
    }

    @OnClick(R.id.text_crowdfunding, R.id.image_crowdfunding)
    internal fun postCrowdfundingClicked() {
        Utils.replaceFragment(this, CrowdFundingFragment(), false, "fragment_post")
        makeActiveText(textCrowdfunding!!, imageCrowdfunding!!, R.drawable.ic_create_post_crowdfunding_select)
    }

    @OnClick(R.id.text_create_event, R.id.image_create_event)
    internal fun createEventClicked() {
        Utils.replaceFragment(this, CreateEventFragment(), false, "fragment_post")
        makeActiveText(textEvent!!, imageEvent!!, R.drawable.ic_create_post_event_select)
    }

    @OnClick(R.id.text_create_event, R.id.image_create_event)
    internal fun postCreateEvent() {
        makeActiveText(textEvent!!, imageEvent!!, R.drawable.ic_create_post_event_select)
    }

    private fun makeActiveText(activeText: TextView, activeImage: ImageView, imgRes: Int) {
        imageContent!!.setImageResource(R.drawable.ic_create_post_content_deselect)
        textContent!!.setTextColor(resources.getColor(R.color.colorLightGrey))
        imageReporting!!.setImageResource(R.drawable.ic_create_post_reporting_deselect)
        textReporting!!.setTextColor(resources.getColor(R.color.colorLightGrey))
        imageCrowdfunding!!.setImageResource(R.drawable.ic_create_post_crowdfunding_deselect)
        textCrowdfunding!!.setTextColor(resources.getColor(R.color.colorLightGrey))
        imageEvent!!.setImageResource(R.drawable.ic_create_post_event_deselect)
        textEvent!!.setTextColor(resources.getColor(R.color.colorLightGrey))

        activeImage.setImageResource(imgRes)
        activeText.setTextColor(resources.getColor(R.color.colorBlackTitle))
    }
}
