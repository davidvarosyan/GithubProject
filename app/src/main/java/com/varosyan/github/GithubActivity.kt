package com.varosyan.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.varosyan.presenter.user_list.UserListFragment

class GithubActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.github_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, UserListFragment())
                .commit()
        }
    }
}