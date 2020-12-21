package com.dicoding.helfani.mysubmissionfinal.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserItems (
    var id: String? = null,
    var avatar: String? = null,
    var username: String? = null,
    var name : String? = null,
    var company: String? = null,
    var location: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var repository: String? = null
) : Parcelable