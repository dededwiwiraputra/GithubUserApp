package com.example.mysubmissionawal

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysubmissionawal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var rvUserList: RecyclerView
    private lateinit var searchUser: MainViewModel

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        searchUser = ViewModelProvider(this)[MainViewModel::class.java]


        mainViewModel.listUser.observe(this) { userDatas ->
            Log.d(TAG, "$userDatas")
            getUserData(userDatas)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun getUserData(userDatas: List<ItemsItem>) {
        val listUser = ArrayList<UserModel>()
        for (user in userDatas) {
            listUser.add(
                UserModel(user.login, user.id, user.avatarUrl)
            )
        }

        val adapter = UserListAdapter(listUser)
        binding.rvUser.adapter = adapter
        Log.d("TEST", "Hayolo")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = item.actionView as SearchView

            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

            searchView.queryHint = "Masukkan nama"

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {

                    if (query.isEmpty()) {
                        showLoading(true)
                    }

                    searchUser.setAllUser(query).observe(this@MainActivity) {
                        getUserData(it)
                    }

                    searchView.clearFocus()

                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        return super.onOptionsItemSelected(item)
    }

}