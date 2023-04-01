package com.example.mysubmissionawal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide.init
import com.example.mysubmissionawal.detail.DetailUser
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
        private const val DUMMY = "dicoding"
    }

    private val _userDatas = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _userDatas

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchUserDatas = MutableLiveData<List<ItemsItem>>()
    val serachUser: LiveData<List<ItemsItem>> = _searchUserDatas

    private val _getDetaillUser = MutableLiveData<DetailUsers>()
    val detailUser: LiveData<DetailUsers> = _getDetaillUser

    init {
        findAllUser()
    }

    private fun findAllUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(DUMMY)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDatas.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun setAllUser(query: String): LiveData<List<ItemsItem>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchUserDatas.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return serachUser
    }


    fun loginUser(username: String): LiveData<DetailUsers> {
        _isLoading.value = true
        ApiConfig.getApiService().getDetailUser(username).enqueue(object : Callback<DetailUsers> {
            override fun onFailure(call: Call<DetailUsers>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<DetailUsers>, response: Response<DetailUsers>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseObject = response.body()!!
                    _getDetaillUser.value = DetailUsers(
                        responseObject.login,
                        responseObject.name ?: "Anonymous",
                        responseObject.avatarUrl,
                        responseObject.followersUrl,
                        responseObject.followingUrl
                    )
                }
            }
        })

        return detailUser
    }

}