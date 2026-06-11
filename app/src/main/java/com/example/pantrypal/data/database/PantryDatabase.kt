package com.example.pantrypal.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pantrypal.data.dao.PantryDao
import com.example.pantrypal.data.dao.UserDao
import com.example.pantrypal.data.entity.PantryItem
import com.example.pantrypal.data.entity.User

@Database(
    entities = [
        User::class,
        PantryItem::class
    ],
    version = 1
)
abstract class PantryDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun pantryDao(): PantryDao

    companion object {

        @Volatile
        private var INSTANCE: PantryDatabase? = null

        fun getDatabase(
            context: Context
        ): PantryDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        PantryDatabase::class.java,
                        "pantry_db"
                    ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}