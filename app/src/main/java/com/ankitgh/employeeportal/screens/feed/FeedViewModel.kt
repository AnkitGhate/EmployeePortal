package com.ankitgh.employeeportal.screens.feed

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FeedViewModel @ViewModelInject constructor(private val fireStoreDb: FirebaseFirestore) : ViewModel() {

    private val postLiveData = MutableLiveData<Resource<PostSchema>>()

    fun fetchPostsFromDatabase(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>> {
        postLiveData.postValue(Resource.loading(null))
        fireStoreDb.collection("posts")
            .orderBy("creation_time", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null && snapshot == null) {
                    Log.e("FeedFragment", "Exception when retrieving posts from FireStore : ${exception.message.toString()}")
                    return@addSnapshotListener
                }
                postList.clear()
                if (snapshot != null) {
                    var postListSnapShot = snapshot.toObjects(PostSchema::class.java)

                    for (post in postListSnapShot) {
                        postList.add(
                            FeedPostModel(
                                "",
                                post.userSchema?.username,
                                post.userSchema?.designation,
                                post.creation_time,
                                post.body
                            )
                        )
                    }
                    postLiveData.postValue(Resource.success(null))

                }
            }
        return postLiveData
    }
}