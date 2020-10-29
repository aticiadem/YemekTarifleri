package com.adematici.yemektarifleri.servis

import com.adematici.yemektarifleri.model.Yemek
import io.reactivex.Single
import retrofit2.http.GET

interface YemekAPI  {

    // https://raw.githubusercontent.com/aticiadem/YemekKitabiJSONVeriSeti/main/yemekler.json

    // BASE_URL -> https://raw.githubusercontent.com/
    // aticiadem/YemekKitabiJSONVeriSeti/main/yemekler.json

    @GET("aticiadem/YemekKitabiJSONVeriSeti/main/yemekler.json")
    fun getYemek(): Single<List<Yemek>>

}