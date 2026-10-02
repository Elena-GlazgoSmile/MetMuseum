package ru.glazgosmile.metmuseum.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.glazgosmile.metmuseum.data.model.Artwork
import androidx.compose.ui.res.painterResource
import ru.glazgosmile.metmuseum.R

@Composable
fun ListScreen(
    onArtworkClick: (Int) -> Unit,
    viewModel: ListViewModel = viewModel()
) {
    val artworks = viewModel.artworks

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(artworks, key = { it.objectID }) { artwork ->
            ArtworkItem(
                artwork = artwork,
                onClick = { onArtworkClick(artwork.objectID) }
            )
        }
    }
}

@Composable
fun ArtworkItem(
    artwork: Artwork,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = artwork.primaryImageSmall,
                contentDescription = artwork.title,
                modifier = Modifier
                    .size(80.dp),
                placeholder = painterResource(id = R.drawable.seras),
                error = painterResource(id = R.drawable.seras)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = artwork.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = artwork.artistDisplayName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
                Text(
                    text = artwork.objectDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}