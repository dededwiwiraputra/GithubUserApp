package com.example.mysubmissionawal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val idUser: Int,
    val imgUrl: String,
    val jumlahFollower: String,
    val jumlahFollowing: String,
    val login : String
) : Parcelable