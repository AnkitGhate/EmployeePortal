package com.ankitgh.employeeportal.screens.feed


data class FeedPostModel(
    val profileImage: String?,
    val username: String?,
    val designation: String?,
    val postTime: Long?,
    val feedBody: String?
)