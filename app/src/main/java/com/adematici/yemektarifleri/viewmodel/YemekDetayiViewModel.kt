package com.adematici.yemektarifleri.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.adematici.yemektarifleri.model.Yemek
import com.adematici.yemektarifleri.servis.YemekDatabase
import kotlinx.coroutines.launch

class YemekDetayiViewModel(application: Application) : BaseViewModel(application) {

    val yemekLiveData = MutableLiveData<Yemek>()

    fun roomVerisiniAl(uuid: Int){
        launch {
            val dao = YemekDatabase(getApplication()).yemekDao()
            val yemek = dao.getYemek(uuid)
            yemekLiveData.value = yemek
        }
    }

}