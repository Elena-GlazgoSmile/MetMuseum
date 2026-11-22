package ru.glazgosmile.metmuseum.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.glazgosmile.metmuseum.domain.model.Artwork
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(
    objectID: Int,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    LaunchedEffect(objectID) {
        viewModel.loadArtwork(objectID)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    val state = uiState
                    if (state is DetailUiState.Success) {
                        IconButton(onClick = { viewModel.toggleFavorite(state.artwork) }) {
                            Icon(
                                imageVector = if (isFavorite)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                                contentDescription = "В избранное",
                                tint = if (isFavorite)
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "Ошибка загрузки",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadArtwork(objectID) }) {
                            Text("Повторить")
                        }
                    }
                }
            }

            is DetailUiState.Success -> {
                val artwork = state.artwork

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = artwork.primaryImage.ifBlank { artwork.primaryImageSmall },
                        contentDescription = artwork.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )

                    ArtworkHeader(artwork = artwork)

                    ArtworkChips(artwork = artwork)

                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailRow("Отдел", artwork.department)
                        DetailRow("Культура", artwork.culture)
                        DetailRow("Страна", artwork.country)
                        DetailRow("Классификация", artwork.classification)
                        DetailRow("Материал", artwork.medium)
                        DetailRow("Размеры", artwork.dimensions)
                        DetailRow("Поступление", artwork.creditLine)

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { viewModel.toggleFavorite(artwork) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = if (isFavorite)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isFavorite)
                                    "Убрать из избранного"
                                else
                                    "Добавить в избранное"
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ArtworkHeader(artwork: Artwork) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (thumbnail, title, artist, date, badge) = createRefs()

        AsyncImage(
            model = artwork.primaryImageSmall,
            contentDescription = artwork.title,
            modifier = Modifier
                .size(72.dp)
                .constrainAs(thumbnail) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = artwork.title.ifBlank { "Без названия" },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(thumbnail.end, margin = 12.dp)
                end.linkTo(badge.start, margin = 8.dp)
            }
        )

        Text(
            text = artwork.artistDisplayName.ifBlank { "Неизвестный автор" },
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            modifier = Modifier.constrainAs(artist) {
                top.linkTo(title.bottom, margin = 4.dp)
                start.linkTo(thumbnail.end, margin = 12.dp)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = artwork.objectDate,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.constrainAs(date) {
                top.linkTo(artist.bottom, margin = 2.dp)
                start.linkTo(thumbnail.end, margin = 12.dp)
            }
        )

        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.constrainAs(badge) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = "#${artwork.objectID}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArtworkChips(artwork: Artwork) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (artwork.department.isNotBlank()) {
            AssistChip(
                onClick = { },
                label = { Text(artwork.department) }
            )
        }
        if (artwork.classification.isNotBlank()) {
            AssistChip(
                onClick = { },
                label = { Text(artwork.classification) }
            )
        }
        if (artwork.country.isNotBlank()) {
            AssistChip(
                onClick = { },
                label = { Text(artwork.country) }
            )
        }
        if (artwork.medium.isNotBlank()) {
            AssistChip(
                onClick = { },
                label = { Text(artwork.medium.take(30)) }
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    if (value.isBlank()) return
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}