package com.example.gestionouvrier.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "presence_table")
data class Presence(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ouvrierId: Int,
    val arrivalTime: Long,          // timestamp d’arrivée
    val departureTime: Long? = null,// timestamp de départ, null si pas encore pointé
    val tache: String
)
