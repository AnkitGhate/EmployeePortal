package com.ankitgh.employeeportal.data.model.firestoremodel

data class PostSchema(var body: String = "", var creation_time: Long = 0, var userSchema: UserSchema? = null)
