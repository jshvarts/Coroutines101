package com.example.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutines.views.UserDetailFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, UserDetailFragment.newInstance())
        .commitNow()
    }
  }
}
