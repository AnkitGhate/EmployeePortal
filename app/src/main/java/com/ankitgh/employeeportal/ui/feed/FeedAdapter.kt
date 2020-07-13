package com.ankitgh.employeeportal.ui.feed

import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(feedPosts: ArrayList<FeedPostModel>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private val postsList: ArrayList<FeedPostModel> = feedPosts

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bindPost(feedPost: FeedPostModel) {
            itemView.user_title.text = feedPost.username
            Glide.with(itemView)
                .load(feedPost.profileImage)
                .placeholder(R.drawable.ic_default_profile_avatar)
                .into(itemView.profile_imageview)
            itemView.designation_title.text = feedPost.designation
            itemView.feed_body_textview.text = feedPost.feedBody
            itemView.post_time_textview.text = feedPost.postTime?.let { DateUtils.getRelativeTimeSpanString(it) }
            itemView.card_container.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.card_transition)
            itemView.likecounttv.text = feedPost.likes.toString()
            itemView.likeanimatedview.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (view != null) {
                when (view.id) {
                    R.id.likeanimatedview -> {
                        itemView.likeanimatedview.playAnimation()
                        var count = itemView.likecounttv.text.toString().toInt()
                        count += 1
                        itemView.likecounttv.text = count.toString()

                        incrementLikesAndUpdateRemote(count)
                    }
                }
            }
        }

        private fun incrementLikesAndUpdateRemote(text: Int) {
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
