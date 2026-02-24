package com.example.cs501_ia1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Q1SettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var wifiOnlySync by remember { mutableStateOf(true) }
    var hapticFeedback by remember { mutableStateOf(true) }
    var textSize by remember { mutableFloatStateOf(0.6f) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                ListItem(
                    headlineContent = { Text("App Preferences") },
                    supportingContent = { Text("Customize notifications, appearance and behavior.") },
                    trailingContent = {
                        AssistChip(onClick = { }, label = { Text("Tips") })
                    }
                )
            }

            // align() requirement
            AssistChip(
                onClick = {
                    notificationsEnabled = true
                    darkModeEnabled = false
                    wifiOnlySync = true
                    hapticFeedback = true
                    textSize = 0.6f
                },
                label = { Text("Reset to defaults") },
                modifier = Modifier.align(Alignment.End)
            )

            Text(
                "Preferences",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {

                    SettingsRow(
                        title = "Notifications",
                        subtitle = "Receive alerts and updates",
                        onRowClick = { notificationsEnabled = !notificationsEnabled }
                    ) {
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it }
                        )
                    }

                    Divider()

                    SettingsRow(
                        title = "Dark Mode",
                        subtitle = "Use dark theme",
                        onRowClick = { darkModeEnabled = !darkModeEnabled }
                    ) {
                        Switch(
                            checked = darkModeEnabled,
                            onCheckedChange = { darkModeEnabled = it }
                        )
                    }

                    Divider()

                    SettingsRow(
                        title = "Wi-Fi Sync Only",
                        subtitle = "Avoid mobile data usage",
                        onRowClick = { wifiOnlySync = !wifiOnlySync }
                    ) {
                        Checkbox(
                            checked = wifiOnlySync,
                            onCheckedChange = { wifiOnlySync = it }
                        )
                    }

                    Divider()

                    SettingsRow(
                        title = "Haptic Feedback",
                        subtitle = "Vibrate on taps",
                        onRowClick = { hapticFeedback = !hapticFeedback }
                    ) {
                        Checkbox(
                            checked = hapticFeedback,
                            onCheckedChange = { hapticFeedback = it }
                        )
                    }

                    Divider()

                    SettingsRow(
                        title = "Text Size",
                        subtitle = "Adjust text scaling",
                        onRowClick = null
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.widthIn(min = 120.dp)
                        ) {
                            Slider(
                                value = textSize,
                                onValueChange = { textSize = it },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text("${(textSize * 100).toInt()}%")
                        }
                    }

                    Divider()

                    SettingsRow(
                        title = "Privacy",
                        subtitle = "Manage permissions",
                        onRowClick = { }
                    ) {
                        Button(
                            onClick = { },
                            modifier = Modifier.sizeIn(minWidth = 90.dp)
                        ) {
                            Text("Manage")
                        }
                    }
                }
            }

            Button(
                onClick = {
                    scope.launch { snackbarHostState.showSnackbar("Settings saved") }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Changes")
            }
        }
    }
}

@Composable
private fun SettingsRow(
    title: String,
    subtitle: String,
    onRowClick: (() -> Unit)?,
    control: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .then(if (onRowClick != null) Modifier.clickable { onRowClick() } else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        ) {
            Text(title, fontWeight = FontWeight.Medium, maxLines = 1)
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }

        Box(
            modifier = Modifier.sizeIn(minWidth = 80.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            control()
        }
    }
}