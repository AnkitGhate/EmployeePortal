package com.ankitgh.employeeportal.screens.feed

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.inflate
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(posts: ArrayList<PostModel>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private val postsList: ArrayList<PostModel> = posts

    class FeedViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var post: PostModel? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindPost(post: PostModel) {
            this.post = post
            view.user_title.text = post.username
            view.designation_title.text = post.designation
            view.feed_body_textview.text = post.feedBody
            view.post_time_textview.text = post.postTime.toString()
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