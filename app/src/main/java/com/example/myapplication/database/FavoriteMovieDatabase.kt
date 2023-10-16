package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.FavoriteMovieDao
import com.example.myapplication.database.entity.FavoriteMovie


@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMovieDatabase:RoomDatabase() {
    abstract fun favoriteMovieDao():FavoriteMovieDao


    companion object{
        private var instance:FavoriteMovieDatabase? = null

        @Synchronized
        fun getInstance(context: Context):FavoriteMovieDatabase? {

            if(instance == null){
                synchronized(FavoriteMovieDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteMovieDatabase::class.java,
                        "favoriteMovie.db"
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