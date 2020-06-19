package com.ankitgh.employeeportal.data.firestoremodel

data class User(
    var username: String = "",
    var designation: String = "",
    var password: String = "",
    var email: String = "",
    var profileuri: String = "",
    var employeeid: String = ""
)