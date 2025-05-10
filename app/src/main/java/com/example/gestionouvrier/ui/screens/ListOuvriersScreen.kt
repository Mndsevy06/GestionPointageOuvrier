package com.example.gestionouvrier.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestionouvrier.data.entities.Ouvrier
import com.example.gestionouvrier.ui.navigation.Screen
import com.example.gestionouvrier.viewmodel.OuvrierViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOuvriersScreen(
    navController: NavController,
    viewModel: OuvrierViewModel
) {
    // On récupère la liste
    val ouvrierList by viewModel.allOuvriers.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Liste des ouvriers",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(
                items = ouvrierList,
                key = { it.id }
            ) { ouvrier ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = ouvrier.nomComplet,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = ouvrier.affectation,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        // Bouton Éditer
                        IconButton(
                            onClick = {
                                navController.navigate(Screen.Edit.createRoute(ouvrier.id))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Éditer",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Bouton Supprimer
                        IconButton(
                            onClick = {
                                viewModel.delete(ouvrier)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Supprimer",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}
