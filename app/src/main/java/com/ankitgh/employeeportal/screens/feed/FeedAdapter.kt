package com.ankitgh.employeeportal.screens.feed

import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.inflate
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(feedPosts: ArrayList<FeedPostModel>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private val postsList: ArrayList<FeedPostModel> = feedPosts

    class FeedViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var feedPost: FeedPostModel? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindPost(feedPost: FeedPostModel) {
            this.feedPost = feedPost
            view.user_title.text = feedPost.username
            view.designation_title.text = feedPost.designation
            view.feed_body_textview.text = feedPost.feedBody
            view.post_time_textview.text = feedPost.postTime?.let { DateUtils.getRelativeTimeSpanString(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflatedView = parent.inflate(R.layout.feed_item, false)
        return FeedViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val postItem = postsList[position]
        holder.bindPost(postItem)
    }
}