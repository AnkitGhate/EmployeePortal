package com.ankitgh.employeeportal.ui.feed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import timber.log.Timber

class FeedViewModel @ViewModelInject constructor(private val fireStoreDb: FirebaseFirestore) : ViewModel() {

    private val postLiveData = MutableLiveData<Resource<PostSchema>>()

    fun fetchPostsFromDatabase(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>> {
        postLiveData.postValue(Resource.loading())
        fireStoreDb.collection("posts")
            .orderBy("creation_time", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null && snapshot == null) {
                    Timber.e("Exception when retrieving posts from FireStore : ${exception.message}")
                    return@addSnapshotListener
                }
                postList.clear()
                if (snapshot != null) {
                    val postListSnapShot = snapshot.toObjects(PostSchema::class.java)

                    for (post in postListSnapShot) {
                        postList.add(
                            FeedPostModel(
                                "",
                                username = post.userSchema?.username,
                                designation = post.userSchema?.designation,
                                postTime = post.creation_time,
                                feedBody = post.body,
                                likes = post.userSchema?.likes
                            )
                        )
                    }
                    postLiveData.postValue(Resource.success(null))
                }
            }
        return postLiveData
    }
}
