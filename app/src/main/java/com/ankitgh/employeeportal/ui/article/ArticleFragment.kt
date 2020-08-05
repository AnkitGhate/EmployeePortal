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

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.Status.*
import com.ankitgh.employeeportal.utils.showSnackBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.Hold
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.article_fragment.*

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.article_fragment), OnArticleClickListener {

    private lateinit var navController: NavController
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            initView()
        }
    }

    private fun initView() {
        observeArticles()
        navController = Navigation.findNavController(requireView())
        linearLayoutManager = LinearLayoutManager(activity)
        article_recyclerview.layoutManager = linearLayoutManager
        articleAdapter = ArticleAdapter(this)
        articleAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        article_recyclerview.adapter = articleAdapter
    }

    private fun observeArticles() {
        viewModel.blogs.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    article_progressBar.animate().alpha(0f).withEndAction {
                        it.data?.let { list ->
                            articleAdapter.submitList(list)
                            article_recyclerview.animate().alpha(1f)

                        }
                    }
                }
                ERROR -> showSnackBar(requireView(), it.message.toString(), Snackbar.LENGTH_LONG)
            }
        })
    }

    override fun onArticleClicked(view: View?, articleURL: String) {
        when (view?.id) {
            R.id.root_article_item -> {
                val extras = FragmentNavigatorExtras(((view to (ViewCompat.getTransitionName(view) as String))))
                var bundleArgs = bundleOf(
                    "shared_motion_element_articledetail" to ViewCompat.getTransitionName(view) as String,
                    "articledetail_url" to articleURL
                )
                navController.navigate(R.id.action_articleFragment_to_articleDetailFragment, bundleArgs, null, extras)
            }
        }
    }
}