package com.communityx.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.utils.AnimationUtils
import com.communityx.utils.Utils

class PraiseAdapter(private val mContext: Context) : RecyclerView.Adapter<PraiseAdapter.ViewHolder>() {
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_praise, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        if (i % 2 == 0 && i != 0) viewHolder.showViewAllReplies((i + 1).toString(), true)
    }

    override fun getItemCount(): Int {
        return 7
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.underline)
        var viewUnderline: View? = null
        @BindView(R.id.text_all_replies)
        var textAllReplies: TextView? = null
        @BindView(R.id.image_like)
        var imageLike: ImageView? = null

        @BindString(R.string.view_replies)
        var viewReplies: String? = null

        var isLike = true

        init {
            ButterKnife.bind(this, itemView)
            showViewAllReplies("", false)
        }

        fun showViewAllReplies(repliesCount: String, shouldShow: Boolean) {
            Utils.showHideView(viewUnderline!!, shouldShow)
            Utils.showHideView(textAllReplies!!, shouldShow)
            if (shouldShow) textAllReplies!!.text = "$viewReplies ($repliesCount)"
        }

        @OnClick(R.id.image_like)
        fun likeTapped() {
            imageLike!!.setImageResource(if (isLike) R.drawable.ic_my_community_like_deselect else R.drawable.ic_my_community_like_select)
            isLike = !isLike
            AnimationUtils.pulse(imageLike, 1, 300)
        }
    }
}
