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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankitgh.employeeportal.R
import com.google.android.material.transition.Hold
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.article_fragment.*

@AndroidEntryPoint
class ArticleFragment : Fragment(), OnArticleClickListener {

    private lateinit var navController: NavController
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: ArticleViewModel by viewModels()

    private var articleList = ArrayList<ArticleModel>()
    //private var articleList = placeholderListOfArticles()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            initView(view)
        }
    }

    private fun initView(view: View) {
        viewModel.blogs.observe(viewLifecycleOwner, Observer {
            articleList = it as ArrayList<ArticleModel>
            articleAdapter.updateList(articleList)
        })
        navController = Navigation.findNavController(view)
        linearLayoutManager = LinearLayoutManager(activity)
        article_recyclerview.layoutManager = linearLayoutManager
        articleAdapter = ArticleAdapter(articleList, this)
        article_recyclerview.adapter = articleAdapter
    }

    override fun onArticleClicked(view: View?) {
        when (view?.id) {
            R.id.root_article_item -> {
                val extras = FragmentNavigatorExtras(((view to (ViewCompat.getTransitionName(view) as String))))
                var bundleArgs = bundleOf("shared_motion_element_articledetail" to ViewCompat.getTransitionName(view) as String)
                navController.navigate(R.id.action_articleFragment_to_articleDetailFragment, bundleArgs, null, extras)
            }
        }
    }
}