package com.adematici.yemektarifleri.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Yemek(
    @ColumnInfo(name = "isim")
    @SerializedName("isim")
    val yemekIsim: String?,
    @ColumnInfo(name = "malzeme")
    @SerializedName("malzeme")
    val yemekMalzeme: String?,
    @ColumnInfo(name = "yapilis")
    @SerializedName("yapilis")
    val yemekYapilis: String?,
    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    val yemekGorsel: String?) {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

}