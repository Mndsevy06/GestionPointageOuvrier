package com.example.gestionouvrier.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gestionouvrier.data.dao.OuvrierDao
import com.example.gestionouvrier.data.dao.PresenceDao
import com.example.gestionouvrier.data.entities.Ouvrier
import com.example.gestionouvrier.data.entities.Presence

@Database(
    entities = [Ouvrier::class, Presence::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ouvrierDao(): OuvrierDao
    abstract fun presenceDao(): PresenceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ouvrier_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
