package com.example.mysubmissionawal

import com.example.mysubmissionawal.Utils.token
import com.example.mysubmissionawal.detail.DetailUser
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    @Headers("Authorization: token $token")
    fun getAllUser():Call<GithubResponse>

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
    fun getUser2(
        @Path("username") username: String
    ): Call<GithubResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token $token")
    fun getUser3(
        @Path("username") username: String
    ): Call<GithubResponse>
}