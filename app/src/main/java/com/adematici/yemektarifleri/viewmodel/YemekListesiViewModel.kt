package com.adematici.yemektarifleri.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adematici.yemektarifleri.model.Yemek
import com.adematici.yemektarifleri.servis.YemekAPIServis
import com.adematici.yemektarifleri.servis.YemekDatabase
import com.adematici.yemektarifleri.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class YemekListesiViewModel(application: Application) : BaseViewModel(application) {

    val yemekler = MutableLiveData<List<Yemek>>()
    val yemekHataMesaji = MutableLiveData<Boolean>()
    val yemekYukleniyor = MutableLiveData<Boolean>()
    private var guncellemeZamani = 10 * 60  * 1000 * 1000 * 1000L

    private val yemekApiServis = YemekAPIServis()
    private val disposable = CompositeDisposable()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            verileriSQLitetanAl()
        } else {
            verileriInternettenAl()
        }
    }

    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriSQLitetanAl(){
        yemekYukleniyor.value = true
        launch {
            val yemekListesi = YemekDatabase(getApplication()).yemekDao().getAllYemek()
            yemekleriGoster(yemekListesi)
        }
    }

    private fun verileriInternettenAl(){
        yemekYukleniyor.value = true
        disposable.add(
            yemekApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Yemek>>() {
                    override fun onSuccess(t: List<Yemek>) {
                        sqliteSakla(t)
                        Toast.makeText(getApplication(),"Liste Güncellendi",Toast.LENGTH_SHORT).show()
                    }
                    override fun onError(e: Throwable) {
                        yemekHataMesaji.value = true
                        yemekYukleniyor.value = false
                        e.printStackTrace()
                    }
                })
        )
    } // verileriInternettenAl

    private fun yemekleriGoster(yemeklerListesi: List<Yemek>){
        yemekler.value = yemeklerListesi
        yemekHataMesaji.value = false
        yemekYukleniyor.value = false
    }

    private fun sqliteSakla(yemekListesi: List<Yemek>){
        launch {
            val dao = YemekDatabase(getApplication()).yemekDao()
            dao.deleteAllYemek()
            val uuidListesi = dao.insertAll(*yemekListesi.toTypedArray())
            var i = 0
            while (i < yemekListesi.size){
                yemekListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            yemekleriGoster(yemekListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }

}