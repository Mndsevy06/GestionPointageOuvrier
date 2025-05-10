package com.example.gestionouvrier.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestionouvrier.data.entities.Ouvrier
import com.example.gestionouvrier.viewmodel.OuvrierViewModel

@Composable
fun EditOuvrierScreen(
    navController: NavController,
    viewModel: OuvrierViewModel,
    ouvrierId: Int
) {
    val allOuvriers by viewModel.allOuvriers.collectAsState()
    val ouvrier = remember(allOuvriers) { allOuvriers.find { it.id == ouvrierId } }
    var nom by remember { mutableStateOf(ouvrier?.nomComplet ?: "") }
    var metier by remember { mutableStateOf(ouvrier?.metier ?: "") }
    var contact by remember { mutableStateOf(ouvrier?.contact ?: "") }
    var dateEmbauche by remember { mutableStateOf(ouvrier?.dateEmbauche ?: System.currentTimeMillis()) }
    var affectation by remember { mutableStateOf(ouvrier?.affectation ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Modifier un ouvrier")
        OutlinedTextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nom complet") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = metier,
            onValueChange = { metier = it },
            label = { Text("Métier") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = contact,
            onValueChange = { contact = it },
            label = { Text("Contact") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = affectation,
            onValueChange = { affectation = it },
            label = { Text("Affectation") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                ouvrier?.let {
                    viewModel.update(
                        it.copy(
                            nomComplet = nom,
                            metier = metier,
                            contact = contact,
                            dateEmbauche = dateEmbauche,
                            affectation = affectation
                        )
                    )
                }
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Sauvegarder")
        }
    }
}