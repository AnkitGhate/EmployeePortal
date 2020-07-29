package com.ankitgh.employeeportal.ui.feed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.domain.FetchPostsUserCase
import com.ankitgh.employeeportal.utils.Resource

class FeedViewModel @ViewModelInject constructor(private val fetchPostsUserCase: FetchPostsUserCase) : ViewModel() {

    fun fetchPosts(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>> {
        return fetchPostsUserCase.fetchPosts(postList)
    }
}