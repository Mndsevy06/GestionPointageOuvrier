/* File: OuvrierViewModelFactory.kt */
package com.example.gestionouvrier.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OuvrierViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OuvrierViewModel::class.java)) {
            // Instanciation du ViewModel AndroidViewModel avec l'Application
            return OuvrierViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
