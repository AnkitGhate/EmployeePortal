package com.ankitgh.employeeportal.screens.feed

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.feed_fragment.*

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var feedAdapter: FeedAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: FeedViewModel by viewModels()
    var postList = ArrayList<FeedPostModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.feed_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setupObserver()

        linearLayoutManager = LinearLayoutManager(activity)
        feed_recyclerview.layoutManager = linearLayoutManager

        feedAdapter = FeedAdapter(postList)
        feed_recyclerview.adapter = feedAdapter

        button_add_post_fab.setOnClickListener {
            navController.navigate(R.id.action_feedFragment_to_createPostFragment)
        }
    }

    private fun setupObserver() {
        viewModel.fetchPostsFromDatabase(postList).observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    feedAdapter.notifyDataSetChanged()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    TODO()
                }
            }
        })
    }

}