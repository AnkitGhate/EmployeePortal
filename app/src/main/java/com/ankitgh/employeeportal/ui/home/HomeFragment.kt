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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.Status
import com.ankitgh.employeeportal.utils.showSnackBar
import com.bumptech.glide.Glide
import com.google.android.material.transition.Hold
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@AndroidEntryPoint
class HomeFragment : Fragment(), NewsAdapter.OnItemClickListener, View.OnClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private var newsList = ArrayList<NewsArticleModel>()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var navController: NavController

    companion object {
        fun launchFragment(
            containerView: Int,
            fragmentManager: FragmentManager
        ) {
            val homeFragment = HomeFragment()
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(containerView, homeFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
    }

    @ExperimentalStdlibApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUserDataObserver()
        setupNewsArticleDataObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(home_awh_cardview, Random.nextInt(1, 100).toString())
        ViewCompat.setTransitionName(home_add_leaves_button, Random.nextInt(1, 100).toString())
        navController = Navigation.findNavController(view)
        setupRecyclerView()
        home_awh_cardview.setOnClickListener(this)
        home_add_leaves_button.setOnClickListener(this)
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(activity)
        home_organisation_news_recyclerview.layoutManager = linearLayoutManager
//        val mDividerItemDecoration = DividerItemDecoration(
//            home_organisation_news_recyclerview.context,
//            linearLayoutManager.orientation
//        )
//        home_organisation_news_recyclerview.addItemDecoration(mDividerItemDecoration)
        newsAdapter = NewsAdapter(newsList, this)
        home_organisation_news_recyclerview.adapter = newsAdapter
    }

    private fun setupNewsArticleDataObserver() {
        viewModel.getArticles.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> updateUI(it.data)
                Status.ERROR -> {
                    home_organisation_news_recyclerview.visibility = View.GONE
                    showSnackBar(requireView(), it.message.toString())
                }
                Status.LOADING -> {
                    if (it.isloading) {
                        shimmer_recyclerview_placeholder.visibility = View.VISIBLE
                    } else {
                        shimmer_recyclerview_placeholder.visibility = View.GONE
                        home_organisation_news_recyclerview.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    @ExperimentalStdlibApi
    private fun setupUserDataObserver() {
        viewModel.userData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    home_username_title.text = it.data?.username.toString().toUpperCase(Locale.ROOT)
                    home_designation_title.text = it.data?.designation?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
                    Glide.with(this)
                        .load(it.data?.photoUrl)
                        .placeholder(R.drawable.ic_default_profile_avatar)
                        .into(home_profile_image)

                }
                Status.ERROR -> showSnackBar(requireView(), it.message.toString())
                Status.LOADING -> TODO("Handle loading case for when()")
            }
        })
    }

    private fun updateUI(newsArticleList: List<NewsArticleModel>?) {
        shimmer_recyclerview_placeholder.visibility = View.GONE
        home_organisation_news_recyclerview.visibility = View.VISIBLE
        newsAdapter.updateList(newsArticleList as ArrayList<NewsArticleModel>)
    }

    override fun onItemClicked(article1: View, article: NewsArticleModel) {
        viewModel.setSelectedArticle(article)
        val extras = FragmentNavigatorExtras(((article1 to (ViewCompat.getTransitionName(article1) as String))))
        val bundleArgs = bundleOf("shared_motion_element" to ViewCompat.getTransitionName(article1) as String)
        navController.navigate(R.id.newsDetailFragment, bundleArgs, null, extras)
    }

    override fun onClick(view: View?) {
        when (view) {
            home_add_leaves_button -> {
                val extras = FragmentNavigatorExtras(((home_add_leaves_button to (ViewCompat.getTransitionName(home_add_leaves_button) as String))))
                val bundleArgs = bundleOf("addleaves_shared_motion_element" to ViewCompat.getTransitionName(home_add_leaves_button) as String)
                navController.navigate(R.id.action_homeFragment_to_addLeaveFragment, bundleArgs, null, extras)
            }
            home_awh_cardview -> {
                val extras = FragmentNavigatorExtras(((home_awh_cardview to (ViewCompat.getTransitionName(home_awh_cardview) as String))))
                val bundleArgs = bundleOf("awh_shared_motion_element" to ViewCompat.getTransitionName(home_awh_cardview) as String)
                navController.navigate(R.id.action_homeFragment_to_AWHDetailFragment, bundleArgs, null, extras)
            }
        }
    }
}
