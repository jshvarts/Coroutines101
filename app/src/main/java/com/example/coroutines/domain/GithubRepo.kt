package com.example.coroutines.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class GithubRepo(
  val name: String,
  val owner: GithubRepoOwner,
  @Json(name = "stargazers_count") val stars: Int
) : Parcelable