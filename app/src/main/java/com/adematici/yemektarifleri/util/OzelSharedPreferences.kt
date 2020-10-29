package com.adematici.yemektarifleri.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class OzelSharedPreferences {

    companion object {
        private val ZAMAN = "zaman"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: OzelSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context) : OzelSharedPreferences = instance ?: synchronized(lock) {
            instance ?: ozelSharedPreferenceYap(context).also {
                instance = it
            }
        }

        private fun ozelSharedPreferenceYap(context: Context) : OzelSharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }

    }

    fun zamaniKaydet(zaman: Long){
        sharedPreferences?.edit(commit = true){
            putLong(ZAMAN,zaman)
        }
    }

    fun zamaniAl() = sharedPreferences?.getLong(ZAMAN,0)

}