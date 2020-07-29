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

package com.ankitgh.employeeportal.ui.home.newsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.ui.home.HomeViewModel
import com.ankitgh.employeeportal.utils.getRelativeDateTimeFromString
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.news_detail_fragment.*
import kotlinx.android.synthetic.main.titlebar.*

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var navController: NavController
    private var sharedelement: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
        arguments?.let {
            sharedelement = NewsDetailFragmentArgs.fromBundle(it).sharedMotionElement
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(newsDetailFragment_container, sharedelement)
        navController = Navigation.findNavController(view)
        title_tv.text = viewModel.getSelectedArticle().sourceSchema?.name
        nd_news_title_tv.text = viewModel.getSelectedArticle().title
        nd_author_tv.text = viewModel.getSelectedArticle().author
        nd_published_time_tv.text = getRelativeDateTimeFromString(viewModel.getSelectedArticle().publishedAt.toString())
        Glide.with(view).load(viewModel.getSelectedArticle().urlToImage).into(nd_news_background_imageview)
        nd_news_body_tv.text = viewModel.getSelectedArticle().content

        navBackButton.setOnClickListener {
            navController.popBackStack(R.id.homeFragment, false)
        }
    }
}
