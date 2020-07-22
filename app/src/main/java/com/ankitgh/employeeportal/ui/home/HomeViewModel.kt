package com.ankitgh.employeeportal.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.domain.GetTopHeadlinesUseCase
import com.ankitgh.employeeportal.utils.NetworkUtil
import com.ankitgh.employeeportal.utils.Resource
import com.ankitgh.employeeportal.utils.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStoreDb: FirebaseFirestore,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    private var _userData = MutableLiveData<Resource<UserSchema>>()
    var userData: LiveData<Resource<UserSchema>> = _userData

    private var _getArticles = MutableLiveData<Resource<List<NewsArticleModel>>>()
    var getArticles: LiveData<Resource<List<NewsArticleModel>>> = _getArticles

    companion object {
        private lateinit var selectedArticle: NewsArticleModel
    }

    init {
        _userData = getUser()
        _getArticles = getNewsArticlesFromRemote()
    }

    private fun getUser(): MutableLiveData<Resource<UserSchema>> {
        fireStoreDb.collection("users")
            .document(firebaseAuth.currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapShot ->
                val signedInUser = userSnapShot.toObject(UserSchema::class.java)
                if (signedInUser != null) {
                    signedInUser.photoUrl = firebaseAuth.currentUser?.photoUrl
                    signedInUser.username = firebaseAuth.currentUser?.displayName.toString()
                }
                _userData.value = Resource.success(signedInUser)
                Timber.i("Signed in user : $signedInUser")
            }
            .addOnFailureListener { exception ->
                _userData.value = Resource.error(exception.message)
                Timber.e("Failure fetching Singed-In user : $exception")
            }
        return _userData
    }

    private fun getNewsArticlesFromRemote(): MutableLiveData<Resource<List<NewsArticleModel>>> {
        viewModelScope.launch {
            getTopHeadlinesUseCase.getTopHeadlines(NetworkUtil.isNetworkConnected()).let {
                when (it.status) {
                    Status.SUCCESS -> _getArticles.value = Resource.success(it.data)
                    Status.ERROR -> _getArticles.value = Resource.success(it.data)
                    Status.LOADING -> TODO("Handle loading case for when()")
                    Status.UNKNOWN -> TODO("Handle unloading case for when()")
                }
            }
        }
        return _getArticles
    }

    fun setSelectedArticle(article: NewsArticleModel) {
        selectedArticle = article
    }

    fun getSelectedArticle(): NewsArticleModel {
        return selectedArticle
    }
}
