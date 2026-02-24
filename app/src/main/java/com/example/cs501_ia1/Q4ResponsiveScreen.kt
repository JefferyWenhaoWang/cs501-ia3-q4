package com.example.cs501_ia1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Q4Section(
    val title: String,
    val subtitle: String,
    val completion: Float,
    val bullets: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Q4ResponsiveScreen() {
    val sections = listOf(
        Q4Section(
            title = "Overview",
            subtitle = "General account summary and usage",
            completion = 0.75f,
            bullets = listOf("Storage summary", "Usage trends", "Quick shortcuts")
        ),
        Q4Section(
            title = "Notifications",
            subtitle = "Push and email preferences",
            completion = 0.40f,
            bullets = listOf("Push alerts", "Email digests", "Reminder cadence")
        ),
        Q4Section(
            title = "Privacy",
            subtitle = "Permissions and data controls",
            completion = 0.90f,
            bullets = listOf("Data export", "Visibility rules", "Access logs")
        ),
        Q4Section(
            title = "Storage",
            subtitle = "Local cache and downloads",
            completion = 0.55f,
            bullets = listOf("Cache cleanup", "Offline files", "Auto-delete policy")
        ),
        Q4Section(
            title = "Appearance",
            subtitle = "Theme and display options",
            completion = 0.65f,
            bullets = listOf("Dark mode", "Text scale", "Compact mode")
        ),
        Q4Section(
            title = "Security",
            subtitle = "Password and device protection",
            completion = 0.30f,
            bullets = listOf("2FA setup", "Trusted devices", "Session control")
        )
    )

    var selectedIndex by remember { mutableIntStateOf(0) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var wifiOnlySync by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Q4 Responsive Layout Challenge") }
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val isPhone = maxWidth < 700.dp

            if (isPhone) {
                Q4PhoneLayout(
                    sections = sections,
                    selectedIndex = selectedIndex,
                    onSelect = { selectedIndex = it },
                    notificationsEnabled = notificationsEnabled,
                    onNotificationsChange = { notificationsEnabled = it },
                    darkModeEnabled = darkModeEnabled,
                    onDarkModeChange = { darkModeEnabled = it },
                    wifiOnlySync = wifiOnlySync,
                    onWifiOnlySyncChange = { wifiOnlySync = it }
                )
            } else {
                Q4WideLayout(
                    sections = sections,
                    selectedIndex = selectedIndex,
                    onSelect = { selectedIndex = it },
                    notificationsEnabled = notificationsEnabled,
                    onNotificationsChange = { notificationsEnabled = it },
                    darkModeEnabled = darkModeEnabled,
                    onDarkModeChange = { darkModeEnabled = it },
                    wifiOnlySync = wifiOnlySync,
                    onWifiOnlySyncChange = { wifiOnlySync = it }
                )
            }
        }
    }
}

@Composable
private fun Q4PhoneLayout(
    sections: List<Q4Section>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    notificationsEnabled: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    darkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    wifiOnlySync: Boolean,
    onWifiOnlySyncChange: (Boolean) -> Unit
) {
    val selected = sections[selectedIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Phone mode (single Column)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        AssistChip(
            onClick = { },
            label = { Text("Width < 700dp") }
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Sections",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )

                sections.forEachIndexed { index, section ->
                    val isSelected = index == selectedIndex
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(index) },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.secondaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outlineVariant
                            }
                        ),
                        tonalElevation = if (isSelected) 2.dp else 0.dp
                    ) {
                        ListItem(
                            headlineContent = { Text(section.title) },
                            supportingContent = { Text(section.subtitle) }
                        )
                    }
                }
            }
        }

        Q4SectionHeaderCard(section = selected)

        Q4PreferencesCard(
            notificationsEnabled = notificationsEnabled,
            onNotificationsChange = onNotificationsChange,
            darkModeEnabled = darkModeEnabled,
            onDarkModeChange = onDarkModeChange,
            wifiOnlySync = wifiOnlySync,
            onWifiOnlySyncChange = onWifiOnlySyncChange
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Recent Activity",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                selected.bullets.forEachIndexed { i, item ->
                    ListItem(
                        headlineContent = { Text(item) },
                        supportingContent = { Text("Phone item ${i + 1}") }
                    )
                }
            }
        }
    }
}

@Composable
private fun Q4WideLayout(
    sections: List<Q4Section>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    notificationsEnabled: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    darkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    wifiOnlySync: Boolean,
    onWifiOnlySyncChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Left pane: navigation/options list (Column)
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Sections",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                AssistChip(
                    onClick = { },
                    label = { Text("Tablet / Landscape mode") }
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    sections.forEachIndexed { index, section ->
                        val isSelected = index == selectedIndex
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelect(index) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primaryContainer
                            } else {
                                MaterialTheme.colorScheme.surface
                            },
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outlineVariant
                            ),
                            tonalElevation = if (isSelected) 3.dp else 0.dp
                        ) {
                            ListItem(
                                headlineContent = { Text(section.title) },
                                supportingContent = { Text(section.subtitle) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                FilledTonalButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Apply Changes")
                }

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reset")
                }
            }
        }

        // Right pane: detail content (Box + Column mixed)
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
        ) {
            Q4DetailPaneWide(
                section = sections[selectedIndex],
                notificationsEnabled = notificationsEnabled,
                onNotificationsChange = onNotificationsChange,
                darkModeEnabled = darkModeEnabled,
                onDarkModeChange = onDarkModeChange,
                wifiOnlySync = wifiOnlySync,
                onWifiOnlySyncChange = onWifiOnlySyncChange
            )
        }
    }
}

@Composable
private fun Q4DetailPaneWide(
    section: Q4Section,
    notificationsEnabled: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    darkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    wifiOnlySync: Boolean,
    onWifiOnlySyncChange: (Boolean) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Q4SectionHeaderCard(section = section)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Q4MetricCard(
                    title = "Progress",
                    value = "${(section.completion * 100).toInt()}%",
                    modifier = Modifier.weight(1f)
                )
                Q4MetricCard(
                    title = "Items",
                    value = "${section.bullets.size}",
                    modifier = Modifier.weight(1f)
                )
                Q4MetricCard(
                    title = "Status",
                    value = if (section.completion >= 0.7f) "Good" else "Needs Work",
                    modifier = Modifier.weight(1f)
                )
            }

            Q4PreferencesCard(
                notificationsEnabled = notificationsEnabled,
                onNotificationsChange = onNotificationsChange,
                darkModeEnabled = darkModeEnabled,
                onDarkModeChange = onDarkModeChange,
                wifiOnlySync = wifiOnlySync,
                onWifiOnlySyncChange = onWifiOnlySyncChange
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Recent Activity (LazyColumn)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )

                    val activityItems = listOf(
                        "Opened ${section.title}",
                        "Edited setting group",
                        "Saved preferences",
                        "Viewed help content",
                        "Adjusted sync behavior",
                        "Checked progress",
                        "Reviewed permissions",
                        "Confirmed changes"
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(activityItems) { item ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                            ) {
                                ListItem(
                                    headlineContent = { Text(item) },
                                    supportingContent = {
                                        Text("Detail view for ${section.title}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Q4SectionHeaderCard(section: Q4Section) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = section.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = section.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            LinearProgressIndicator(
                progress = section.completion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Text(
                text = "Completion: ${(section.completion * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun Q4MetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun Q4PreferencesCard(
    notificationsEnabled: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    darkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    wifiOnlySync: Boolean,
    onWifiOnlySyncChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Quick Preferences",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Q4SwitchRow(
                label = "Notifications",
                checked = notificationsEnabled,
                onCheckedChange = onNotificationsChange
            )

            Q4SwitchRow(
                label = "Dark Mode",
                checked = darkModeEnabled,
                onCheckedChange = onDarkModeChange
            )

            Q4SwitchRow(
                label = "Wi-Fi Only Sync",
                checked = wifiOnlySync,
                onCheckedChange = onWifiOnlySyncChange
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save")
                }
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
private fun Q4SwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}