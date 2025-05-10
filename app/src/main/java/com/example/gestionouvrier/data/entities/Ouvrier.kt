package com.example.gestionouvrier.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ouvrier_table")
data class Ouvrier(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomComplet: String,
    val metier: String,
    val contact: String,
    val dateEmbauche: Long,
    val affectation: String
)