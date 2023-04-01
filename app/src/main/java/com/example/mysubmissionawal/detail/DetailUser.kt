package com.example.mysubmissionawal.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mysubmissionawal.R
import com.example.mysubmissionawal.UserModel
import com.example.mysubmissionawal.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
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


    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailFragmentAdapter = DetailFragmentAdapter(this)
        binding.viewPager.adapter = detailFragmentAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f


        val data = intent.getParcelableExtra<UserModel>(GET_USER)!!

        Log.d("AHA", "$data")

        getData(data)

    }

    private fun getData(data: UserModel) {
        Glide.with(this)
            .load(data.imgUrl)
            .into(binding.profileImage)
        binding.nama.text = data.name
        binding.username.text = data.login
    }

}