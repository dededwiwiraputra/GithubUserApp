package com.example.mysubmissionawal

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    @Headers("Authorization: token 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")
    fun getAllUser():Call<GithubResponse>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<GithubResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUser2(
        @Path("username") username: String
    ): Call<GithubResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUser3(
        @Path("username") username: String
    ): Call<GithubResponse>
}