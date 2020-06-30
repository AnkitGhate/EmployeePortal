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


class OrgNewsAdapter(private var newsArticleList: ArrayList<NewsArticleModel>) : RecyclerView.Adapter<OrgNewsAdapter.OrgNewsViewHolder>() {

    lateinit var recyclerView: RecyclerView

    class OrgNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view: View = itemView
        private var newsArticle: NewsArticleModel? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindNews(newsArticle: NewsArticleModel) {
            this.newsArticle = newsArticle
            view.news_body_textview.text = newsArticle.newsContent
            view.news_date_tv.text = getRelativeDateTimeFromString(newsArticle.publishDate)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgNewsViewHolder {
        val inflatedView = parent.inflate(R.layout.organisation_new_item, false)
        return OrgNewsViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return newsArticleList.size
    }

    override fun onBindViewHolder(holder: OrgNewsViewHolder, position: Int) {
        val postItem = newsArticleList[position]
        holder.bindNews(postItem)
    }

    fun updateList(newsList: ArrayList<NewsArticleModel>) {
        newsArticleList.clear()
        newsArticleList = newsList
        notifyDataSetChanged()
    }
}
