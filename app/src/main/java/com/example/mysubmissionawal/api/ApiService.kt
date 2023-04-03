package com.example.mysubmissionawal.api

import com.example.mysubmissionawal.model.DetailUsers
import com.example.mysubmissionawal.model.GithubResponse
import com.example.mysubmissionawal.Utils.token
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token $token")
    fun getUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $token")
    fun getDetailUser(
        @Path("username") username: String
    ):Call<DetailUsers>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $token")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<GithubResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token $token")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<GithubResponse>
}