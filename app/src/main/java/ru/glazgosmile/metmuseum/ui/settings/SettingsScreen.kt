package ru.glazgosmile.metmuseum.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.glazgosmile.metmuseum.data.local.SettingsRepositoryImpl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onApply: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val settingsRepo = remember { SettingsRepositoryImpl(context) }

    var query by remember { mutableStateOf("painting") }
    var hasImages by remember { mutableStateOf(false) }
    var isLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        settingsRepo.queryFlow.collect { query = it; isLoaded = true }
    }
    LaunchedEffect(Unit) {
        settingsRepo.hasImagesFlow.collect { hasImages = it }
    }

    if (!isLoaded) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки поиска") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onQueryChange(it) },
                label = { Text("Поисковый запрос (дефолт: painting)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = hasImages, onCheckedChange = { viewModel.onHasImagesChange(it) })
                Spacer(modifier = Modifier.width(8.dp))
                Text("Только с картинками")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.saveSettings { onApply() } },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Готово")
            }
        }
    }
}