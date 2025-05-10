package com.example.gestionouvrier.data.repository

import com.example.gestionouvrier.data.dao.OuvrierDao
import com.example.gestionouvrier.data.entities.Ouvrier
import kotlinx.coroutines.flow.Flow

class OuvrierRepository(private val dao: OuvrierDao) {
    val allOuvriers: Flow<List<Ouvrier>> = dao.getAllOuvriers()

    suspend fun insert(ouvrier: Ouvrier) = dao.insert(ouvrier)
    suspend fun update(ouvrier: Ouvrier) = dao.update(ouvrier)
    suspend fun delete(ouvrier: Ouvrier) = dao.delete(ouvrier)
}