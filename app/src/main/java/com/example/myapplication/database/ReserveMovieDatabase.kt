package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.ReserveMovieDao
import com.example.myapplication.database.entity.ReserveMovie


@Database(entities = [ReserveMovie::class], version = 1)
abstract class ReserveMovieDatabase:RoomDatabase() {
    abstract fun reserveMovieDao():ReserveMovieDao


    companion object{
        private var instance:ReserveMovieDatabase? = null

        @Synchronized
        fun getInstance(context: Context):ReserveMovieDatabase? {

            if(instance == null){
                synchronized(ReserveMovieDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ReserveMovieDatabase::class.java,
                        "reserveMovie.db"
                    ).build()
                }
            }

            return instance
        }


        fun destroyInstance(){
            instance = null
        }

    }

}