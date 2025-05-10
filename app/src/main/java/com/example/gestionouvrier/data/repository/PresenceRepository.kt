package com.example.gestionouvrier.data.repository

import com.example.gestionouvrier.data.dao.PresenceDao
import com.example.gestionouvrier.data.entities.Presence
import kotlinx.coroutines.flow.Flow

class PresenceRepository(private val dao: PresenceDao) {
    val allPresences: Flow<List<Presence>> = dao.getAllPresences()

    suspend fun insert(presence: Presence) {
        dao.insert(presence)
    }

    suspend fun update(presence: Presence) {
        dao.update(presence)
    }

    suspend fun delete(presence: Presence) {
        dao.delete(presence)
    }

    suspend fun getPresenceForOuvrierOnDate(ouvrierId: Int, date: String): Presence? {
        return dao.todayPresenceFor(ouvrierId)
    }

    suspend fun punch(ouvrierId: Int, tache: String) {
        val now = System.currentTimeMillis()
        val today = dao.todayPresenceFor(ouvrierId)
        if (today == null) {
            // 1er pointage = arrivée
            dao.insert(Presence(ouvrierId = ouvrierId, arrivalTime = now, tache = tache))
        } else if (today.departureTime == null) {
            // 2e pointage = départ
            dao.update(today.copy(departureTime = now))
        }
        // sinon on ignore
    }
}
