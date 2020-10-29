package com.adematici.yemektarifleri.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adematici.yemektarifleri.model.Yemek

@Database(entities = arrayOf(Yemek::class),version = 1)
abstract class YemekDatabase : RoomDatabase() {

    abstract fun yemekDao() : YemekDAO

    // Singleton

    companion object{

        @Volatile private var instance: YemekDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }

        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            YemekDatabase::class.java, "yemekdatabase").build()
    }

}