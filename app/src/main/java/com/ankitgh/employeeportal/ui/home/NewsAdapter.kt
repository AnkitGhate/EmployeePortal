package com.ankitgh.employeeportal.ui.home

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.getRelativeDateTimeFromString
import com.ankitgh.employeeportal.utils.inflate
import kotlinx.android.synthetic.main.organisation_new_item.view.*
import java.util.*


class NewsAdapter(private var newsArticleList: ArrayList<NewsArticleModel>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    lateinit var recyclerView: RecyclerView

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view: View = itemView
        private var newsArticle: NewsArticleModel? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindNews(newsArticle: NewsArticleModel, onItemClickListener: OnItemClickListener) {
            this.newsArticle = newsArticle
            view.news_body_textview.text = newsArticle.description
            view.news_date_tv.text = getRelativeDateTimeFromString(newsArticle.publishedAt)
            itemView.setOnClickListener {
                onItemClickListener.onItemClicked(newsArticle)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflatedView = parent.inflate(R.layout.organisation_new_item, false)
        return NewsViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return newsArticleList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val postItem = newsArticleList[position]
        holder.bindNews(postItem, onItemClickListener)
    }

    fun updateList(newsList: ArrayList<NewsArticleModel>) {
        newsArticleList.clear()
        newsArticleList = newsList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(article: NewsArticleModel)
    }
}
