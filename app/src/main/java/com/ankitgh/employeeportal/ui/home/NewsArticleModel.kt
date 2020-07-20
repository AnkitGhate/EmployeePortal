package com.ankitgh.employeeportal.ui.home

import com.ankitgh.employeeportal.data.model.newsmodel.SourceSchema

data class NewsArticleModel(
    val description: String?,
    val publishedAt: String?,
    val author: String? = "",
    val sourceSchema: SourceSchema?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val content: String?
)
