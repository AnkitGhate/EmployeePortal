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
