package com.ankitgh.employeeportal.data.model.newsmodel

import com.google.gson.annotations.SerializedName

data class NewsJsonRootSchema(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<ArticlesSchema>
)
