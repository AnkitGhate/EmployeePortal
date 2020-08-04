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

package com.ankitgh.employeeportal.ui.article

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

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.inflate
import com.ankitgh.employeeportal.utils.setImageFromGlide
import kotlinx.android.synthetic.main.article_item.view.*
import kotlin.random.Random

class ArticleAdapter(private val onItemClickListener: OnArticleClickListener) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val diffItemCallback = object : DiffUtil.ItemCallback<ArticleModel>() {
        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.articleTitle == newItem.articleTitle
        }

        override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem == newItem
        }

    }
    private var differ: AsyncListDiffer<ArticleModel> = AsyncListDiffer(this, diffItemCallback)


    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindPost(article: ArticleModel, onArticleClickListener: OnArticleClickListener) {
            ViewCompat.setTransitionName(itemView, Random.nextInt(1, 100).toString())

            itemView.article_title.text = article.articleTitle
            itemView.article_desc.text = article.articleMetaDescription
            itemView.article_author.text = article.articleAuthor.ifBlank { "UnKnown" }
            if (article.articleImageUrl.isNotEmpty()) {
                itemView.article_background_image.setImageFromGlide(itemView, article.articleImageUrl, R.color.cardBackGroundColor, itemView.article_background_image)
            } else {
                itemView.article_background_image.visibility = View.GONE
            }
            itemView.root_article_item.setOnClickListener {
                onArticleClickListener.onArticleClicked(view = itemView, articleURL = article.articleURL)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflatedView = parent.inflate(R.layout.article_item, false)
        return ArticleViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val postItem = differ.currentList[position]
        holder.bindPost(postItem, onItemClickListener)
    }

    fun submitList(list: MutableList<ArticleModel>) {
        differ.submitList(list)
    }
}

interface OnArticleClickListener {
    fun onArticleClicked(view: View?, articleURL: String)
}
