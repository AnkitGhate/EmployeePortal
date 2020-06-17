package com.ankitgh.employeeportal.screens.feed

import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.inflate
import kotlinx.android.synthetic.main.feed_item.view.*


class FeedAdapter(feedPosts: ArrayList<FeedPostModel>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private val postsList: ArrayList<FeedPostModel> = feedPosts

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindPost(feedPost: FeedPostModel) {
            itemView.profile_imageview.hash = feedPost.username.hashCode()
            itemView.user_title.text = feedPost.username
            itemView.designation_title.text = feedPost.designation
            itemView.feed_body_textview.text = feedPost.feedBody
            itemView.post_time_textview.text = feedPost.postTime?.let { DateUtils.getRelativeTimeSpanString(it) }
            itemView.card_container.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.card_transition)
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