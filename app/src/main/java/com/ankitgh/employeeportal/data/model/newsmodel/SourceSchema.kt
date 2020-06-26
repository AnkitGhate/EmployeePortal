package com.ankitgh.employeeportal.data.model.newsmodel

import com.google.gson.annotations.SerializedName

data class SourceSchema(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
