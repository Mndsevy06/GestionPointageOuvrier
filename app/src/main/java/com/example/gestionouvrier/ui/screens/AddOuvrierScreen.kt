package com.example.gestionouvrier.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
fun AddOuvrierScreen(
    navController: NavController,
    viewModel: OuvrierViewModel
) {
    var nom by remember { mutableStateOf("") }
    var selectedMetier by remember { mutableStateOf("") }
    var metierExpanded by remember { mutableStateOf(false) }
    var contact by remember { mutableStateOf("") }
    var dateEmbauche by remember { mutableStateOf(System.currentTimeMillis()) }
    var affectation by remember { mutableStateOf("") }

    // Liste de métiers prédéfinis
    val metiers = listOf("Maçon", "Peintre", "Électricien", "Plombier", "Menuisier", "Autre")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ajouter un ouvrier")

        OutlinedTextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nom complet") },
            modifier = Modifier.fillMaxWidth()
        )

        // ==== Dropdown "Métier" sans icônes externes ====
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedMetier,
                onValueChange = { /* lecture seule */ },
                readOnly = true,
                label = { Text("Métier") },
                trailingIcon = {
                    // flèche unicode
                    Text(
                        text = if (metierExpanded) "▲" else "▼",
                        modifier = Modifier.clickable { metierExpanded = !metierExpanded }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { metierExpanded = true }
            )
            DropdownMenu(
                expanded = metierExpanded,
                onDismissRequest = { metierExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                metiers.forEach { met ->
                    DropdownMenuItem(
                        text = { Text(met) },
                        onClick = {
                            selectedMetier = met
                            metierExpanded = false
                        }
                    )
                }
            }
        }
        // ===============================================

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
                viewModel.insert(
                    Ouvrier(
                        nomComplet   = nom,
                        metier       = selectedMetier,
                        contact      = contact,
                        dateEmbauche = dateEmbauche,
                        affectation  = affectation
                    )
                )
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Enregistrer")
        }
    }
}
