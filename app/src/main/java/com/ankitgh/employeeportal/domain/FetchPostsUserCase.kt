package com.ankitgh.employeeportal.domain

import androidx.lifecycle.LiveData
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.ui.feed.FeedPostModel
import com.ankitgh.employeeportal.utils.Resource

class FetchPostsUserCase constructor(private val mainRepository: MainRepository) {

    fun fetchPosts(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>> {
        return mainRepository.fetchPosts(postList)
    }
}