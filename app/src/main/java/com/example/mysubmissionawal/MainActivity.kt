package com.example.mysubmissionawal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysubmissionawal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var rvUserList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


        mainViewModel.listUser.observe(this) { userDatas ->
            getUserData(userDatas)
        }

//        mainViewModel.isLoading.observe(this) {
//            showLoading(it)
//        }
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

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu.findItem(R.id.search).actionView as SearchView
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//
//        searchView.queryHint = "Masukkan nama"
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            /*
//            Gunakan method ini ketika search selesai atau OK
//             */
//            override fun onQueryTextSubmit(query: String): Boolean {
//                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
//                searchView.clearFocus()
//                return true
//            }
//
//            /*
//            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
//             */
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })
//        return true
//    }
}