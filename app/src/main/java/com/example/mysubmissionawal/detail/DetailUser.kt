package com.example.mysubmissionawal.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mysubmissionawal.R
import com.example.mysubmissionawal.databinding.ActivityDetailUserBinding
import com.example.mysubmissionawal.detail.favorite.FavoriteEntity
import com.example.mysubmissionawal.detail.favorite.FavoriteFactory
import com.example.mysubmissionawal.model.DetailUsers
import com.example.mysubmissionawal.model.MainViewModel
import com.example.mysubmissionawal.model.UserModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val GET_USER = "get user"
    }


    private var _binding: ActivityDetailUserBinding? = null
    private lateinit var searchUser: MainViewModel

    lateinit var mainViewModel: MainViewModel
    private val binding get() = _binding!!
    private var favoriteEntity: FavoriteEntity? = null
    private var favChecker = false
    private var detailUser = DetailUsers()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val headBar = supportActionBar
        headBar!!.title = "Detail User"

        mainViewModel = ViewModelProvider(
            this,
            FavoriteFactory.getInstance(this.application)
        )[MainViewModel::class.java]

        searchUser = ViewModelProvider(
            this,
            FavoriteFactory.getInstance(this.application)
        )[MainViewModel::class.java]

//        Get Data From Intent
        val data = intent.getStringExtra(GET_USER)
        if (data != null) {
            getDetailUserApi(data)
        }

//        Send Data To Fragment
        val value = Bundle()
        value.putString("dataValue", "${data}")
        val detailFragmentAdapter = DetailFragmentAdapter(this)
        detailFragmentAdapter.setBundle(value)
        binding.viewPager.adapter = detailFragmentAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

//        Set Place Holder
        binding.layoutView.visibility = View.INVISIBLE
        binding.load.startShimmer()

//        Set User Detail
        mainViewModel.detailUser.observe(this) {
            binding.apply {
                Glide.with(this@DetailUser)
                    .load(it.avatarUrl)
                    .circleCrop()
                    .into(profileImage)
                nama.text = it.name
                username.text = it.login
                followers.text = it.followers+" Followers"
                following.text = it.following+" Following"
            }
            Log.d("Favorite", "$it")
            detailUser = it
            favoriteEntity = FavoriteEntity(it.id, it.login)
            mainViewModel.getAllFavorites().observe(this) { favorite ->
                if (favorite != null) {
                    for (data in favorite) {
                        if (it.id == data.id) {
                            favChecker = true
                            binding.favorite?.setImageResource(R.drawable.ic_baseline_favorite_24)
                        }
                    }
                }
            }
        }

        binding.favorite?.setOnClickListener {
            if (!favChecker) {
                favChecker = true
                binding.favorite?.setImageResource(R.drawable.ic_baseline_favorite_24)
                setDb(detailUser)
            } else {
                favChecker = false
                binding.favorite?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                mainViewModel.delete(detailUser.id)
                Toast.makeText(this, "Remove From Favorite List", Toast.LENGTH_LONG).show()
            }
        }


//        Set Loading
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }.apply {
            Handler().postDelayed({
                binding.layoutView.visibility = View.VISIBLE
                binding.load.stopShimmer()
                binding.load.visibility = View.INVISIBLE
            }, 1000)
        }



        headBar.setDisplayHomeAsUpEnabled(true)
        headBar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE

    }

    private fun getDetailUserApi(data: String) {
        mainViewModel.getDetailListUser(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setDb(value: DetailUsers) {
        favoriteEntity?.let {
            it.id = value.id
            it.login = value.login
            it.avatarUrl = value.avatarUrl
            mainViewModel.insert(it)
            Toast.makeText(this, "Favorite Added", Toast.LENGTH_SHORT).show()
        }
        Log.d("Favorite Set", "$favoriteEntity")
    }

}