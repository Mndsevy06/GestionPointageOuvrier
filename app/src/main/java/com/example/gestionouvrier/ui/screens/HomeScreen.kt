package com.example.gestionouvrier.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Report
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestionouvrier.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gestion des ouvriers") }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    HomeOptionCard(
                        icon = Icons.Filled.Add,
                        title = "Ajouter un ouvrier",
                        onClick = { navController.navigate(Screen.Add.route) }
                    )
                }
                item {
                    HomeOptionCard(
                        icon = Icons.Filled.List,
                        title = "Liste des ouvriers",
                        onClick = { navController.navigate(Screen.List.route) }
                    )
                }
                item {
                    HomeOptionCard(
                        icon = Icons.Filled.Check,
                        title = "Suivi de présence",
                        onClick = { navController.navigate(Screen.Presence.route) }
                    )
                }
                item {
                    HomeOptionCard(
                        icon = Icons.Filled.Report,
                        title = "Rapport journalier",
                        onClick = { navController.navigate(Screen.Rapport.route) }
                    )
                }
            }
        }
    )
}

@Composable
fun HomeOptionCard(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
