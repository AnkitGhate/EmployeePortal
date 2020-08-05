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

package com.ankitgh.employeeportal.ui.article.articleDetail

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.Status.LOADING
import com.ankitgh.employeeportal.utils.Status.SUCCESS
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.article_detail_fragment.*

@AndroidEntryPoint
class ArticleDetailFragment : Fragment(R.layout.article_detail_fragment) {

    private lateinit var navController: NavController
    private var sharedMotionElement: String? = null
    private var articleUrl: String? = null
    private val viewModel: ArticleDetailFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
        arguments?.let {
            sharedMotionElement = ArticleDetailFragmentArgs.fromBundle(it).sharedMotionElementArticledetail
            articleUrl = ArticleDetailFragmentArgs.fromBundle(it).articledetailUrl.toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(root_article_detail_fragment, sharedMotionElement)
        navController = Navigation.findNavController(view)

        viewModel.fetchArticle(articleUrl as String).observe(viewLifecycleOwner, Observer { articleResource ->
            when (articleResource.status) {
                SUCCESS -> {
                    article_detail_progressBar.animate().alpha(0f)
                        .withEndAction {
                            article_detail_progressBar.isVisible = false
                            articleResource.data.let {
                                article_body_tv.text = it?.body
                                article_author_tv.text = it?.author
                                article_date_tv.text = it?.readingTime
                                content_container.animate().alpha(1f)
                            }

                        }
                }
                LOADING -> {
                    //article_detail_progressBar.isVisible = articleResource.isloading
                }
            }
        })

        article_backButton.setOnClickListener {
            navController.popBackStack(R.id.articleFragment, false)
        }
    }
}