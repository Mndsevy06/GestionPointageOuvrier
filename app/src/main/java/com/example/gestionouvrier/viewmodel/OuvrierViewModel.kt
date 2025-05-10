package com.example.gestionouvrier.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionouvrier.data.database.AppDatabase
import com.example.gestionouvrier.data.entities.Ouvrier
import com.example.gestionouvrier.data.entities.Presence
import com.example.gestionouvrier.data.repository.OuvrierRepository
import com.example.gestionouvrier.data.repository.PresenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OuvrierViewModel(application: Application) : AndroidViewModel(application) {
    private val ouvrierRepository: OuvrierRepository
    private val presenceRepository: PresenceRepository

    val allOuvriers: StateFlow<List<Ouvrier>>
    val allPresences: StateFlow<List<Presence>>

    init {
        val db = AppDatabase.getDatabase(application)
        ouvrierRepository = OuvrierRepository(db.ouvrierDao())
        presenceRepository = PresenceRepository(db.presenceDao())

        allOuvriers = ouvrierRepository.allOuvriers
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
        allPresences = presenceRepository.allPresences
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    }

    fun insert(ouvrier: Ouvrier) = viewModelScope.launch(Dispatchers.IO) {
        ouvrierRepository.insert(ouvrier)
    }

    fun update(ouvrier: Ouvrier) = viewModelScope.launch(Dispatchers.IO) {
        ouvrierRepository.update(ouvrier)
    }

    fun delete(ouvrier: Ouvrier) = viewModelScope.launch(Dispatchers.IO) {
        ouvrierRepository.delete(ouvrier)
    }

    fun enregistrerPresence(presence: Presence) = viewModelScope.launch(Dispatchers.IO) {
        presenceRepository.insert(presence)
    }

    fun updatePresence(presence: Presence) = viewModelScope.launch(Dispatchers.IO) {
        presenceRepository.update(presence)
    }

    fun deletePresence(presence: Presence) = viewModelScope.launch(Dispatchers.IO) {
        presenceRepository.delete(presence)
    }

    // ✅ Méthode ajoutée pour gérer la présence
    fun punchPresence(ouvrierId: Int, tache: String) = viewModelScope.launch(Dispatchers.IO) {
        val currentTime = System.currentTimeMillis()
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(currentTime))

        // Rechercher une présence existante pour aujourd'hui
        val existingPresence = presenceRepository.getPresenceForOuvrierOnDate(ouvrierId, todayDate)

        if (existingPresence == null) {
            // Enregistrer l'heure d'arrivée
            val newPresence = Presence(
                ouvrierId = ouvrierId,
                tache = tache,
                arrivalTime = currentTime,
                departureTime = null
            )
            presenceRepository.insert(newPresence)
        } else if (existingPresence.departureTime == null) {
            // Enregistrer l'heure de départ
            val updatedPresence = existingPresence.copy(departureTime = currentTime)
            presenceRepository.update(updatedPresence)
        }
        // Sinon, la présence est déjà complète pour aujourd'hui
    }
}
