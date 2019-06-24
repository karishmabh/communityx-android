package com.communityx.adapters

import android.content.Context
import android.content.Intent
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.*
import com.communityx.R
import com.communityx.activity.CrowdfundingDetailActivity
import com.communityx.activity.EventDetailActivity
import com.communityx.activity.LikesActivity
import com.communityx.activity.PraiseActivity
import com.communityx.utils.AnimationUtils
import com.communityx.utils.DialogHelper
import com.communityx.utils.Utils
import com.google.android.flexbox.FlexboxLayout

class CommunityFeedAdapter(val mContext: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val POST_FEED = 0
    private val POST_EVENT_FEED = 1
    private val POST_CROWFUNDING_FEED = 2
    private val POST_SHARE_FEED = 3
    private val inflater: LayoutInflater
    private var fromProfile: Boolean = false
    private var isOtherProfile: Boolean = false

    init {
        inflater = LayoutInflater.from(mContext)
    }

    fun setFromProfile(fromProfile: Boolean) {
        this.fromProfile = fromProfile
    }

    fun setOtherProfile(otherProfile: Boolean) {
        isOtherProfile = otherProfile
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        if (i == POST_EVENT_FEED) {
            return ViewHolderEventFeed(inflater.inflate(R.layout.item_event_feed, viewGroup, false))
        } else if (i == POST_CROWFUNDING_FEED) {
            return ViewHolderCrowdfundingFeed(inflater.inflate(R.layout.item_crowdfunding_feed, viewGroup, false))
        } else if (i == POST_SHARE_FEED) {
            return ViewHolderShareFeed(inflater.inflate(R.layout.item_share_post_feed, viewGroup, false))
        }
        return ViewHolderPostFeed(inflater.inflate(R.layout.item_community_feeds, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (viewHolder is ViewHolderPostFeed) {
            viewHolder.viewPostMedia.visibility = if (i % 3 == 0 && i != 0) View.VISIBLE else View.GONE
        } else if (viewHolder is ViewHolderShareFeed) {
            viewHolder.addDummyShare(i)
        }
    }

    override fun getItemCount(): Int {
        return 14
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 4)
            POST_EVENT_FEED
        else if (position == 5)
            POST_CROWFUNDING_FEED
        else if (fromProfile && position == 2)
            POST_SHARE_FEED
        else
            POST_FEED
    }

    internal open inner class BaseFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.image_like)
        lateinit var imageLike: ImageView
        @BindView(R.id.image_more)
        lateinit var imageMore: ImageView
        @BindView(R.id.view_more)
        lateinit var viewMore: View

        var isLike = true

        init {
            ButterKnife.bind(this, itemView)
           // viewMore.visibility = if (fromProfile && !isOtherProfile) View.VISIBLE else View.GONE
        }

        @OnClick(R.id.view_comment)
        fun praisedClicked() {
            mContext!!.startActivity(Intent(mContext, PraiseActivity::class.java))
        }

        @OnClick(R.id.view_like)
        fun tappedLike() {
            mContext!!.startActivity(Intent(mContext, LikesActivity::class.java))
        }

        @OnClick(R.id.image_more)
        fun imageMoreTapped() {
            DialogHelper.showReportPopDialog(mContext, object: DialogHelper.IDialogCallback {
                override fun onConfirmed() {
                    DialogHelper.showReportDialog(mContext, this)
                }

                override fun onDenied() {
               }
            })
        }

        @OnClick(R.id.image_like)
        fun likeTapped() {
            imageLike.setImageResource(if (isLike) R.drawable.ic_my_community_like_deselect else R.drawable.ic_my_community_like_select)
            isLike = !isLike
            AnimationUtils.pulse(imageLike, 1, 300)
        }

        fun openReportDialog() {

        }
    }

    internal inner class ViewHolderPostFeed(itemView: View) : BaseFeedViewHolder(itemView) {

        @BindView(R.id.view_post_media)
        lateinit var viewPostMedia: View
        @BindView(R.id.text_post)
        lateinit var textPost: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        @OnClick(R.id.image_more)
        fun imageTapped() {
            DialogHelper.showReportPopDialog(mContext, object: DialogHelper.IDialogCallback {
                override fun onConfirmed() {
                    DialogHelper.showReportDialog(mContext, this)
                }

                override fun onDenied() {
                }
            })
        }
    }

    internal inner class ViewHolderEventFeed(itemView: View) : BaseFeedViewHolder(itemView) {

        @BindView(R.id.button_interested)
        lateinit var buttonInterested: Button

        init {
            ButterKnife.bind(this, itemView)
            buttonInterested.visibility = if (fromProfile && !isOtherProfile) View.GONE else View.VISIBLE
            itemView.setOnClickListener { v ->
                mContext!!.startActivity(
                    Intent(
                        mContext,
                        EventDetailActivity::class.java
                    )
                )
            }
        }

        @OnClick(R.id.image_more)
        fun imageTapped() {
            DialogHelper.showReportPopDialog(mContext, object: DialogHelper.IDialogCallback {
                override fun onConfirmed() {
                    DialogHelper.showReportDialog(mContext, object: DialogHelper.IDialogCallback {
                        override fun onConfirmed() {
                            DialogHelper.showReportDialog(mContext, this)
                        }

                        override fun onDenied() {
                        }
                    })
                }

                override fun onDenied() {
                }
            })
        }
    }

    internal inner class ViewHolderCrowdfundingFeed(itemView: View) : BaseFeedViewHolder(itemView) {

        @BindView(R.id.edit_amount)
        lateinit var editAmount: TextInputEditText
        @BindView(R.id.radioGroup)
        lateinit var radioGroupDonation: RadioGroup
        @BindView(R.id.textinput_amount)
        lateinit var inputLayoutAmount: TextInputLayout
        @BindView(R.id.text_other_amount)
        lateinit var radioOtherAmount: RadioButton
        @BindView(R.id.text_dollor_one)
        lateinit var radioDollorOne: RadioButton
        @BindView(R.id.text_dollor_two)
        lateinit var radioDollorTwo: RadioButton
        @BindView(R.id.button_pay)
        lateinit var buttonPay: Button

        init {
            ButterKnife.bind(this, itemView)
            radioListener()
        }

        @OnTextChanged(R.id.edit_amount)
        fun amountTypng(s: CharSequence) {
            if (s.length < 1) {
                editAmount.setText("$")
                editAmount.setSelection(1)
            }
        }

        @OnClick(R.id.image_more)
        fun imageTapped() {
            DialogHelper.showReportPopDialog(mContext, object: DialogHelper.IDialogCallback {
                override fun onConfirmed() {
                    DialogHelper.showReportDialog(mContext, this)
                }

                override fun onDenied() {
                }
            })
        }

        private fun radioListener() {
            itemView.setOnClickListener { v ->
                mContext!!.startActivity(
                    Intent(
                        mContext,
                        CrowdfundingDetailActivity::class.java
                    )
                )
            }

            radioDollorOne.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) buttonPay.setBackgroundResource(R.drawable.button_active)
                radioDollorOne.setBackgroundResource(if (isChecked) R.drawable.bg_stroke_active else R.drawable.bg_stroke_grey)
            }

            radioDollorTwo.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) buttonPay.setBackgroundResource(R.drawable.button_active)
                radioDollorTwo.setBackgroundResource(if (isChecked) R.drawable.bg_stroke_active else R.drawable.bg_stroke_grey)
            }

            radioOtherAmount.setOnCheckedChangeListener { buttonView, isChecked ->
                inputLayoutAmount.visibility = if (isChecked) View.VISIBLE else View.GONE
                if (isChecked) buttonPay.setBackgroundResource(R.drawable.button_active)
                radioOtherAmount.setBackgroundResource(if (isChecked) R.drawable.bg_stroke_active else R.drawable.bg_stroke_grey)
            }
        }
    }

    internal inner class ViewHolderShareFeed(itemView: View) : BaseFeedViewHolder(itemView) {

        @BindView(R.id.container_share_post)
        lateinit var containerSharePost: FrameLayout

        init {
            ButterKnife.bind(this, itemView)
        }

        @OnClick(R.id.image_more)
        fun imageTapped() {
            DialogHelper.showReportPopDialog(mContext, object: DialogHelper.IDialogCallback {
                override fun onConfirmed() {
                    DialogHelper.showReportDialog(mContext, this)
                }

                override fun onDenied() {
                }
            })
        }

        fun addDummyShare(position: Int) {
            val view: View
            if (position == 2) {
                view = inflater.inflate(R.layout.item_event_feed, null)
                view.findViewById<View>(R.id.text_post).visibility = View.GONE
                view.findViewById<View>(R.id.button_interested_share).visibility = View.VISIBLE
                view.findViewById<View>(R.id.view_like_share_comment).visibility = View.GONE
                view.findViewById<View>(R.id.text_miles).visibility = View.GONE
                containerSharePost.addView(view)
            }
        }
    }
}