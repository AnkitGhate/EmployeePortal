package com.ankitgh.employeeportal.domain

import com.ankitgh.employeeportal.common.Resource
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getTopHeadlines(isConnected: Boolean): Resource<List<NewsArticleModel>> {
        return withContext(dispatcher) {
            return@withContext mainRepository.getTopHeadlines(isConnected)
        }
    }
}