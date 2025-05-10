package com.example.gestionouvrier.data.dao

import androidx.room.*
import com.example.gestionouvrier.data.entities.Ouvrier
import kotlinx.coroutines.flow.Flow

@Dao
interface OuvrierDao {
    @Query("SELECT * FROM ouvrier_table ORDER BY nomComplet ASC")
    fun getAllOuvriers(): Flow<List<Ouvrier>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ouvrier: Ouvrier)

    @Update
    suspend fun update(ouvrier: Ouvrier)

    @Delete
    suspend fun delete(ouvrier: Ouvrier)
}