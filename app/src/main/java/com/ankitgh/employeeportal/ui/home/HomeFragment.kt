package com.ankitgh.employeeportal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.NetworkUtil
import com.ankitgh.employeeportal.utils.Status
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var OrgNewsAdapter: OrgNewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var newsList = ArrayList<NewsArticleModel>()
    private val viewModel: HomeViewModel by viewModels()

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    username.text = it.data?.username.toString().capitalize()
                    designation.text = it.data?.designation?.toUpperCase()
                    Glide.with(this).load(it.data?.photoUri).into(profileimage)
                }
                Status.ERROR -> TODO()
                Status.LOADING -> TODO()
                Status.UNKNOWN -> TODO()
            }
        })

        linearLayoutManager = LinearLayoutManager(activity)
        organisation_news_recyclerview.layoutManager = linearLayoutManager

        OrgNewsAdapter = OrgNewsAdapter(newsList)
        organisation_news_recyclerview.adapter = OrgNewsAdapter
    }

    override fun onStart() {
        super.onStart()
        observeData()
    }

    private fun observeData() {
        viewModel.getNewsArticlesFromRemote(NetworkUtil.isNetworkConnected(requireContext()))
            .observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.SUCCESS -> updateUI(it.data)
                    Status.ERROR -> Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_SHORT).show()
                    Status.LOADING -> TODO()
                    Status.UNKNOWN -> TODO()
                }
            })
    }

    private fun updateUI(newsArticleList: List<NewsArticleModel>?) {
        OrgNewsAdapter.updateList(newsArticleList as ArrayList<NewsArticleModel>)
    }
}
