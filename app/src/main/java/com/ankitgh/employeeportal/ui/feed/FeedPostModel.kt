package com.ankitgh.employeeportal.ui.feed

data class FeedPostModel(
    val profileImage: String?,
    val username: String?,
    val designation: String?,
    val postTime: Long?,
    val feedBody: String?,
    val likes: Int?
)
