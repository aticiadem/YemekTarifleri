package com.adematici.yemektarifleri.servis

import com.adematici.yemektarifleri.model.Yemek
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class YemekAPIServis  {

    // https://raw.githubusercontent.com/aticiadem/YemekKitabiJSONVeriSeti/main/yemekler.json

    // BASE_URL -> https://raw.githubusercontent.com/
    // aticiadem/YemekKitabiJSONVeriSeti/main/yemekler.json

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(YemekAPI::class.java)


    fun getData() : Single<List<Yemek>>{
        return api.getYemek()
    }
}