package com.example.coroutines.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutines.R
import com.example.coroutines.domain.GithubRepo

class ReposAdapter(private val repos: List<GithubRepo>) :
  RecyclerView.Adapter<ReposAdapter.RepoViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
    val repoName = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_repo, parent, false) as TextView
    return RepoViewHolder(repoName)
  }

  override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
    holder.repoName.text = repos[position].name
  }

  override fun getItemCount() = repos.count()

  class RepoViewHolder(val repoName: TextView) : RecyclerView.ViewHolder(repoName)
}