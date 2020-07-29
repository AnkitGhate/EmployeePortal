package com.ankitgh.employeeportal.ui.home

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.domain.GetTopHeadlinesUseCase
import com.ankitgh.employeeportal.domain.GetUserInfoUserCase
import com.ankitgh.employeeportal.utils.NetworkUtil
import com.ankitgh.employeeportal.utils.Resource
import com.ankitgh.employeeportal.utils.Status.*
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val getUserInfoUserCase: GetUserInfoUserCase,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val appContext: Application
) : ViewModel() {

    private var _userData = MutableLiveData<Resource<UserSchema>>()
    var userData: LiveData<Resource<UserSchema>> = _userData

    private var _getArticles = MutableLiveData<Resource<List<NewsArticleModel>>>()
    var getArticles: LiveData<Resource<List<NewsArticleModel>>> = _getArticles

    companion object {
        private lateinit var selectedArticle: NewsArticleModel
    }

    init {
        viewModelScope.launch {
            _userData = getUser()
            _getArticles = getNewsArticlesFromRemote()
        }
    }

    private fun getUser(): MutableLiveData<Resource<UserSchema>> {
        getUserInfoUserCase.getUser().observeForever(Observer {
            when (it.status) {
                SUCCESS -> _userData.value = it
                ERROR -> _userData.value = it
                LOADING -> _userData.value = it
            }
        })
        return _userData
    }

    private suspend fun getNewsArticlesFromRemote(): MutableLiveData<Resource<List<NewsArticleModel>>> {
        getTopHeadlinesUseCase.getTopHeadlines(NetworkUtil.isNetworkConnected(appContext)).let {
            when (it.status) {
                SUCCESS -> _getArticles.value = Resource.success(it.data)
                ERROR -> _getArticles.value = Resource.error(it.message)
                LOADING -> _getArticles.value = Resource.loading(it.isloading)
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
