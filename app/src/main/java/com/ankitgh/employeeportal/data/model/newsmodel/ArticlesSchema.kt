package com.ankitgh.employeeportal.data.model.newsmodel

import com.google.gson.annotations.SerializedName

data class ArticlesSchema(
    @SerializedName("source") val sourceSchema: SourceSchema,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String
)
