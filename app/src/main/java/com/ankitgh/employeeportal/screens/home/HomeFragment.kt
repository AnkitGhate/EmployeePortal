package com.ankitgh.employeeportal.screens.home

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
import com.ankitgh.employeeportal.common.getPlaceHolderListOfNews
import com.ankitgh.employeeportal.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var OrgNewsAdapter: OrgNewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserName().observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    username.text = it.data?.username.toString().capitalize()
                    designation.text = it.data?.designation?.toUpperCase()
                    profileimage.hash = username.hashCode()
                    shimmer_layout_container.hideShimmer()
                }
            }
        })

        linearLayoutManager = LinearLayoutManager(activity)
        organisation_news_recyclerview.layoutManager = linearLayoutManager

        OrgNewsAdapter = OrgNewsAdapter(getPlaceHolderListOfNews())
        organisation_news_recyclerview.adapter = OrgNewsAdapter
    }

}