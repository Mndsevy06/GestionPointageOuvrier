package com.example.gestionouvrier.data.dao

import androidx.room.*
import com.example.gestionouvrier.data.entities.Presence
import kotlinx.coroutines.flow.Flow

@Dao
interface PresenceDao {
    @Query("SELECT * FROM presence_table ORDER BY arrivalTime DESC")
    fun getAllPresences(): Flow<List<Presence>>

    // Trouver la présence d'aujourd'hui pour un ouvrier
    @Query("""
      SELECT * FROM presence_table 
      WHERE ouvrierId = :ouvrierId 
        AND date(arrivalTime/1000, 'unixepoch') = date('now')
      LIMIT 1
    """)
    suspend fun todayPresenceFor(ouvrierId: Int): Presence?

    @Insert
    suspend fun insert(presence: Presence): Long

    @Update
    suspend fun update(presence: Presence)

    @Delete
    suspend fun delete(presence: Presence)
}
