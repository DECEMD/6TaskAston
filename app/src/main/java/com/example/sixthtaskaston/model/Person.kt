package com.example.sixthtaskaston.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    var contactId: Int,
    var name: String,
    var surname: String,
    var phoneNumber: String)
    :Parcelable
