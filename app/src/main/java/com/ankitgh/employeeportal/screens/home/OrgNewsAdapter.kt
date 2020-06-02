package com.ankitgh.employeeportal.screens.home

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.inflate
import kotlinx.android.synthetic.main.organisation_new_item.view.*

class OrgNewsAdapter(posts: ArrayList<OrgNewsModel>) :
    RecyclerView.Adapter<OrgNewsAdapter.OrgNewsViewHolder>() {

    private val newsList: ArrayList<OrgNewsModel> = posts

    class OrgNewsViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var news: OrgNewsModel? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindNews(news: OrgNewsModel) {
            this.news = news
            view.news_body_textview.text = news.newscontent
            view.news_date_tv.text = news.publishDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgNewsViewHolder {
        val inflatedView = parent.inflate(R.layout.organisation_new_item, false)
        return OrgNewsViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: OrgNewsViewHolder, position: Int) {
        val postItem = newsList[position]
        holder.bindNews(postItem)
    }
}