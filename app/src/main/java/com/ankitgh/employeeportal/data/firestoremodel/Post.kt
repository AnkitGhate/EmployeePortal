package com.ankitgh.employeeportal.data.firestoremodel

data class Post(var body: String = "", var creation_time: Long = 0, var user: User? = null)