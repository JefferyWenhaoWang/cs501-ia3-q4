package com.example.cs501_ia1

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Q3TagBrowserScreen() {
    val allTags = listOf(
        "Kotlin", "Compose", "Android", "UI", "UX", "Material3",
        "State", "Navigation", "Room", "Retrofit", "Coroutines", "Flow",
        "Testing", "Animation", "Layout", "Row", "Column", "Box",
        "FlowRow", "FlowColumn", "Git", "GitHub", "API", "Firebase"
    )

    var query by remember { mutableStateOf("") }
    var showOnlySelected by remember { mutableStateOf(false) }
    var compactMode by remember { mutableStateOf(false) }

    val selectedTags = remember { mutableStateListOf<String>() }

    val visibleTags = allTags.filter { tag ->
        val matchQuery = query.isBlank() || tag.contains(query, ignoreCase = true)
        val matchSelectedFilter = !showOnlySelected || selectedTags.contains(tag)
        matchQuery && matchSelectedFilter
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tag Browser + Filters") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // spacing requirement
        ) {

            // --- Controls Card (Material 3: Card, OutlinedTextField, Switch, Button)
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Search & Filters",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Search tags") }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Switch(
                                checked = showOnlySelected,
                                onCheckedChange = { showOnlySelected = it }
                            )
                            Text("Only selected")
                        }

                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Switch(
                                checked = compactMode,
                                onCheckedChange = { compactMode = it }
                            )
                            Text("Compact mode")
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FilledTonalButton(
                            onClick = { selectedTags.clear() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Clear All")
                        }
                        Button(
                            onClick = {
                                // quick demo action: select first few if empty
                                if (selectedTags.isEmpty()) {
                                    allTags.take(4).forEach { if (!selectedTags.contains(it)) selectedTags.add(it) }
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Quick Select")
                        }
                    }
                }
            }

            // --- Main Tag Browser (FlowRow required)
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Browse Tags (${visibleTags.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Tap a chip to select or unselect.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Divider()

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp), // spacing requirement
                        verticalArrangement = Arrangement.spacedBy(8.dp)     // spacing requirement
                    ) {
                        visibleTags.forEach { tag ->
                            val selected = selectedTags.contains(tag)

                            FilterChip(
                                selected = selected,
                                onClick = {
                                    if (selected) {
                                        selectedTags.remove(tag)
                                    } else {
                                        selectedTags.add(tag)
                                    }
                                },
                                label = { Text(tag) },
                                modifier = if (selected) {
                                    Modifier.border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = FilterChipDefaults.shape
                                    )
                                } else {
                                    Modifier
                                }
                            )
                        }
                    }
                }
            }

            // --- Selected Tags Area (FlowColumn required)
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Selected Tags (${selectedTags.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )

                        AssistChip(
                            onClick = { /* no-op */ },
                            label = { Text(if (selectedTags.isEmpty()) "None" else "Updated") }
                        )
                    }

                    Divider()

                    if (selectedTags.isEmpty()) {
                        Text(
                            text = "No tags selected yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        // Height constraint helps FlowColumn wrap into new columns when needed
                        FlowColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 60.dp, max = if (compactMode) 100.dp else 140.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            selectedTags.forEach { tag ->
                                AssistChip(
                                    onClick = { selectedTags.remove(tag) },
                                    label = { Text(tag) }
                                )
                            }
                        }

                        Text(
                            text = "Tip: Tap a selected tag chip above or in this area to remove it.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

