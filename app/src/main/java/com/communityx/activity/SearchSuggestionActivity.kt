package com.communityx.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.SearchAdapter
import kotlinx.android.synthetic.main.activity_search_suggestion.*
import kotlinx.android.synthetic.main.layout_search_bar.*

class SearchSuggestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_suggestion)
        ButterKnife.bind(this)

        setRecyclerView()
    }

    @OnClick(R.id.image_back)
    fun onBackTapped() {
        onBackPressed()
    }

    private fun setRecyclerView() {
        recycler_suggestion_list.layoutManager = LinearLayoutManager(this)
        recycler_suggestion_list.setHasFixedSize(true)
        recycler_suggestion_list.adapter = SearchAdapter(ArrayList(), this)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}
