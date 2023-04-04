package com.example.mysubmissionawal.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmissionawal.api.ApiConfig
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

    private val _getUserFollowers = MutableLiveData<DetailUsers>()
    val userFollowers: LiveData<DetailUsers> = _getUserFollowers

    private val _getUserFollowing = MutableLiveData<List<ItemsItem>>()
    val userFollowing: LiveData<List<ItemsItem>> = _getUserFollowing


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


    fun getDetailListUser(login: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(login)
        client.enqueue(object : Callback<DetailUsers> {
            override fun onResponse(
                call: Call<DetailUsers>,
                response: Response<DetailUsers>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _getDetaillUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUsers>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findUserFollowers(value: String): LiveData<DetailUsers> {
        val client = ApiConfig.getApiService().getUserFollowers(value)
        client.enqueue(object : Callback<DetailUsers> {
            override fun onResponse(
                call: Call<DetailUsers>,
                response: Response<DetailUsers>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _getUserFollowers.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUsers>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onGagal: ${t.message}")
            }
        })
        return userFollowers
    }

    fun findUserFollowing(value: String): LiveData<List<ItemsItem>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(value)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _getUserFollowing.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return userFollowing
    }

}