package com.example.coroutines.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.coroutines.CoroutinesApp
import com.example.coroutines.R
import kotlinx.android.synthetic.main.repos_fragment.*
import javax.inject.Inject

class ReposFragment : Fragment() {

  companion object {
    fun newInstance() = ReposFragment()
  }

  @Inject
  lateinit var viewModel: ReposViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (requireActivity().application as CoroutinesApp)
      .appComponent
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.repos_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.repos.observe(viewLifecycleOwner, Observer {
      reposRecyclerView.adapter = ReposAdapter(it)
    })

    viewModel.lookupRepos()
  }
}
