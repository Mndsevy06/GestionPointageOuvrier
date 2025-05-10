package com.example.gestionouvrier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gestionouvrier.ui.navigation.AppNavigation
import com.example.gestionouvrier.ui.theme.GestionOuvrierTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionOuvrierTheme {
                AppNavigation()
            }
        }
    }
}
