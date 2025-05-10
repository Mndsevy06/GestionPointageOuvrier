package com.example.gestionouvrier.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestionouvrier.viewmodel.OuvrierViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RapportScreen(viewModel: OuvrierViewModel) {
    val presenceList by viewModel.allPresences.collectAsState(initial = emptyList())
    val ouvrierList by viewModel.allOuvriers.collectAsState(initial = emptyList())

    val dfDate = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val dfTime = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    val dailyReports = remember(presenceList, ouvrierList) {
        presenceList.mapNotNull { presence ->
            val ouvrier = ouvrierList.find { it.id == presence.ouvrierId } ?: return@mapNotNull null
            val date = dfDate.format(Date(presence.arrivalTime))
            val arrival = dfTime.format(Date(presence.arrivalTime))
            val departure = presence.departureTime?.let { dfTime.format(Date(it)) } ?: "-"
            DailyReport(
                date = date,
                nom = ouvrier.nomComplet,
                task = presence.tache,
                arrivalTime = arrival,
                departureTime = departure
            )
        }
    }

    val groupedReports = dailyReports.groupBy { it.date }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Rapport journalier") }) }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            groupedReports.forEach { (date, entries) ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                    ) {
                        Text("Nom", Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
                        Text("Tâche", Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
                        Text("Arrivée", Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
                        Text("Départ", Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
                    }
                }
                items(entries) { report ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                    ) {
                        Text(report.nom, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                        Text(report.task.ifEmpty { "-" }, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                        Text(report.arrivalTime, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                        Text(report.departureTime, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                    }
                    Divider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 1.dp)
                }
            }
            if (groupedReports.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Aucune présence enregistrée.", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

private data class DailyReport(
    val date: String,
    val nom: String,
    val task: String,
    val arrivalTime: String,
    val departureTime: String
)
