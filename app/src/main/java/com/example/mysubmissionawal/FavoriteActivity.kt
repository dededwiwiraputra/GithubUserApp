package com.example.mysubmissionawal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionawal.adapter.FavoriteAdapter
import com.example.mysubmissionawal.databinding.ActivityFavoriteBinding
import com.example.mysubmissionawal.detail.DetailUser
import com.example.mysubmissionawal.detail.favorite.FavoriteEntity
import com.example.mysubmissionawal.detail.favorite.FavoriteFactory
import com.example.mysubmissionawal.model.MainViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityFavoriteBinding
    private lateinit var mainViewModel: MainViewModel

    companion object {
        private const val TAG = "Favorite Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.apply {
            title = "Favorite"
            setDisplayHomeAsUpEnabled(true)
        }

        val layoutManager = LinearLayoutManager(this)
        _binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        _binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel = ViewModelProvider(
            this,
            FavoriteFactory.getInstance(this.application)
        )[MainViewModel::class.java]

        mainViewModel.getAllFavorites().observe(this) { data ->
            if (data != null) {
                getData(data)
            }
        }
    }

    private fun getData(data: List<FavoriteEntity>) {
        val adapter = FavoriteAdapter(data as ArrayList<FavoriteEntity>)
        _binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteEntity) {
                startActivity(
                    Intent(
                        this@FavoriteActivity,
                        DetailUser::class.java
                    ).putExtra(
                        DetailUser.GET_USER,
                        data.login
                    )
                )
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}