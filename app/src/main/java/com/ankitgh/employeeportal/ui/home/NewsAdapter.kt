/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ankitgh.employeeportal.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.getRelativeDateTimeFromString
import com.ankitgh.employeeportal.utils.inflate
import kotlinx.android.synthetic.main.organisation_new_item.view.*
import timber.log.Timber
import java.util.*
import kotlin.random.Random

class NewsAdapter(
    private var newsArticleList: ArrayList<NewsArticleModel>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private lateinit var recyclerView: RecyclerView

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view: View = itemView
        private var newsArticle: NewsArticleModel? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Timber.d("CLICK!")
        }

        fun bindNews(newsArticle: NewsArticleModel, onItemClickListener: OnItemClickListener) {
            ViewCompat.setTransitionName(itemView, Random.nextInt(1, 100).toString())
            this.newsArticle = newsArticle
            view.news_body_textview.text = newsArticle.description
            view.news_date_tv.text = getRelativeDateTimeFromString(newsArticle.publishedAt)
            itemView.setOnClickListener {
                onItemClickListener.onItemClicked(it, newsArticle)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflatedView = parent.inflate(R.layout.organisation_new_item, false)
        inflatedView.layoutParams.height = recyclerView.height / 3
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
        fun onItemClicked(article1: View, article: NewsArticleModel)
    }
}
