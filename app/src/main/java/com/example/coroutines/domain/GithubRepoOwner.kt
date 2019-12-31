package com.example.coroutines.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GithubRepoOwner(
  @Json(name = "login") val username: String
) : Parcelable