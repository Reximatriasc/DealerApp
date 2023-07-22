package com.example.dealerapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "dealer_table")
data class Dealer  (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val Nama: String,
    val Alamat: String,
    val Telp: String,
    val latitude:Double?,
    val longitude: Double?,
) : Parcelable