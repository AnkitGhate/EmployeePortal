package com.ankitgh.employeeportal.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.Status
import com.ankitgh.employeeportal.utils.showSnackBar
import com.google.android.material.transition.Hold
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.feed_fragment.*

@AndroidEntryPoint
class FeedFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var feedAdapter: FeedAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: FeedViewModel by viewModels()
    var postList = ArrayList<FeedPostModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setupObserver()
    }

    private fun initView(view: View) {
        navController = Navigation.findNavController(view)
        linearLayoutManager = LinearLayoutManager(activity)
        feed_recyclerview.layoutManager = linearLayoutManager
        feedAdapter = FeedAdapter(postList)
        feed_recyclerview.adapter = feedAdapter
        feed_button_add_post_fab.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel.fetchPosts(postList).observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    feedAdapter.notifyDataSetChanged()
                }
                Status.LOADING -> {
                    if (it.isloading) feed_progressBar.visibility = View.VISIBLE else feed_progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    showSnackBar(requireView(), it.message.toString())
                }
            }
        })
    }

    override fun onClick(view: View?) {
        when (view) {
            feed_button_add_post_fab -> {
                navController.navigate(R.id.createPostFragment)
            }
        }
    }
}