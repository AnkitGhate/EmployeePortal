package com.ankitgh.employeeportal.data.model.firestoremodel

data class BasicUserInfoSchema(
    var username: String? = "",
    var designation: String? = "",
    var email: String? = "",
    var photourl: String? = "",
    var likes: Int? = 0
)