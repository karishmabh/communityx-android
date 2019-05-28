package com.communityx.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.communityx.R
import com.communityx.adapters.PraiseAdapter
import com.communityx.utils.CustomToolBarHelper

class PraiseActivity : AppCompatActivity() {

    @BindView(R.id.recycler_view_praise)
    internal var recyclerView: RecyclerView? = null
    @BindView(R.id.image_send)
    internal var imageSend: ImageView? = null
    @BindView(R.id.edit_type)
    internal var editType: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_praise)
        ButterKnife.bind(this)

        setUpToolbar()
        setPraiseData()
    }

    @OnTextChanged(R.id.edit_type)
    internal fun onTyping(s: CharSequence) {
        imageSend!!.setImageResource(if (s.length != 0) R.drawable.ic_praise_share_select else R.drawable.ic_praise_share_deselect)
    }

    private fun setPraiseData() {
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = PraiseAdapter(this)
    }

    private fun setUpToolbar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle(R.string.praise)
        customToolBarUtils.enableBackPress()
    }
}
