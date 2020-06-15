package com.ankitgh.employeeportal.screens.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.data.firestoremodel.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.feed_fragment.*

class FeedFragment : Fragment() {

    private lateinit var feedAdapter: FeedAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel: FeedViewModel
    private lateinit var fireStoreDB: FirebaseFirestore
    var postList = ArrayList<FeedPostModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fireStoreDB = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.feed_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchPostsFromDatabase()

        linearLayoutManager = LinearLayoutManager(activity)
        feed_recyclerview.layoutManager = linearLayoutManager

        feedAdapter = FeedAdapter(postList)
        feed_recyclerview.adapter = feedAdapter
    }

    private fun fetchPostsFromDatabase() {

        fireStoreDB.collection("posts")
            .orderBy("creation_time", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null && snapshot == null) {
                    Log.e("FeedFragment", "Exception when retrieving posts from FireStore : ${exception.message.toString()}")
                    return@addSnapshotListener
                }
                postList.clear()
                if (snapshot != null) {
                    var postList = snapshot.toObjects(Post::class.java)

                    for (post in postList) {
                        this.postList.add(
                            FeedPostModel(
                                "",
                                post.user?.username,
                                post.user?.designation,
                                post.creation_time,
                                post.body
                            )
                        )
                    }
                    feedAdapter.notifyDataSetChanged()
                }
            }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
    }

}