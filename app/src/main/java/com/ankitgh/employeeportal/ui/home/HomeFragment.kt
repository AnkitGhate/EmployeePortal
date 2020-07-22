package com.ankitgh.employeeportal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.Status
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment(), NewsAdapter.OnItemClickListener {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeData()
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
        navController = Navigation.findNavController(view)
        setupRecyclerView()
        home_awh_cardview.setOnClickListener {
            navController.navigate(R.id.AWHDetailFragment)
        }
        home_add_leaves_button.setOnClickListener {
            navController.navigate(R.id.addLeaveFragment)
        }
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(activity)
        home_organisation_news_recyclerview.layoutManager = linearLayoutManager

        newsAdapter = NewsAdapter(newsList, this)
        home_organisation_news_recyclerview.adapter = newsAdapter
    }

    private fun observeData() {
        viewModel.userData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    home_username_title.text = it.data?.username.toString().capitalize()
                    home_designation_title.text = it.data?.designation?.toUpperCase()
                    Glide.with(this)
                        .load(it.data?.photoUrl)
                        .placeholder(R.drawable.ic_default_profile_avatar)
                        .into(home_profile_image)

                }
                Status.ERROR -> TODO("Handle error case for when()")
                Status.LOADING -> TODO("Handle loading case for when()")
            }
        })

        viewModel.getArticles.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> updateUI(it.data)
                Status.ERROR -> Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_SHORT).show()
                Status.LOADING -> TODO("Handle loading case for when()")
            }
        })
    }

    private fun updateUI(newsArticleList: List<NewsArticleModel>?) {
        newsAdapter.updateList(newsArticleList as ArrayList<NewsArticleModel>)
    }

    override fun onItemClicked(article: NewsArticleModel) {
        viewModel.setSelectedArticle(article)
        navController.navigate(R.id.newsDetailFragment)
    }
}
