package com.example.gestionouvrier.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestionouvrier.data.entities.Ouvrier
import com.example.gestionouvrier.viewmodel.OuvrierViewModel
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresenceScreen(
    viewModel: OuvrierViewModel
) {
    val ouvrierList by viewModel.allOuvriers.collectAsState(initial = emptyList())
    val presences by viewModel.allPresences.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Pointer la présence (arrivée et départ)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(ouvrierList) { ouvrier ->
                val todayPres = presences.find {
                    it.ouvrierId == ouvrier.id &&
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                .format(Date(it.arrivalTime)) ==
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                .format(Date())
                }
                val label = when {
                    todayPres == null -> "Pointer arrivée"
                    todayPres.departureTime == null -> "Pointer départ"
                    else -> "Terminé"
                }
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(ouvrier.nomComplet)
                            Text(
                                "Métier : ${ouvrier.metier}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Button(
                            onClick = {
                                viewModel.punchPresence(ouvrier.id, "Tâche générique")
                            },
                            enabled = label != "Terminé"
                        ) {
                            Text(label)
                        }
                    }
                }
            }
        }
    }
}
