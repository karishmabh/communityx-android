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
import kotlinx.android.synthetic.main.activity_praise.*

class PraiseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_praise)
        ButterKnife.bind(this)

        setUpToolbar()
        setPraiseData()
    }

    @OnTextChanged(R.id.edit_type)
    internal fun onTyping(s: CharSequence) {
        image_send!!.setImageResource(if (s.isNotEmpty()) R.drawable.ic_praise_share_select else R.drawable.ic_praise_share_deselect)
    }

    private fun setPraiseData() {
        recycler_view_praise!!.layoutManager = LinearLayoutManager(this)
        recycler_view_praise!!.adapter = PraiseAdapter(this)
    }

    private fun setUpToolbar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle(R.string.praise)
        customToolBarUtils.enableBackPress()
    }
}
