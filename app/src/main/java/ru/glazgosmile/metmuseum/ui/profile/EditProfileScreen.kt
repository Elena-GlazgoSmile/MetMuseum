package ru.glazgosmile.metmuseum.ui.profile

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var resumeUrl by remember { mutableStateOf("") }
    var newAvatarBytes by remember { mutableStateOf<ByteArray?>(null) }
    var previewAvatar by remember { mutableStateOf<Any?>(null) }

    LaunchedEffect(profile) {
        if (fullName.isEmpty()) fullName = profile.fullName
        if (position.isEmpty()) position = profile.position
        if (resumeUrl.isEmpty()) resumeUrl = profile.resumeUrl
        if (previewAvatar == null) previewAvatar = profile.avatarPath
    }

    var showImageSourceDialog by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            newAvatarBytes = inputStream?.readBytes()
            previewAvatar = it
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            newAvatarBytes = stream.toByteArray()
            previewAvatar = it
        }
    }

    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        galleryLauncher.launch("image/*")
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            android.widget.Toast.makeText(
                context,
                "Без разрешения на камеру съемка невозможна",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактирование") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.updateProfile(fullName, position, resumeUrl, newAvatarBytes)
                        onBack()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Сохранить")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { showImageSourceDialog = true },
                contentAlignment = Alignment.Center
            ) {
                if (previewAvatar != null) {
                    AsyncImage(model = previewAvatar, contentDescription = "Аватар", modifier = Modifier.fillMaxSize())
                } else {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(64.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("ФИО") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = position,
                onValueChange = { position = it },
                label = { Text("Должность") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = resumeUrl,
                onValueChange = { resumeUrl = it },
                label = { Text("Ссылка на резюме (PDF)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text("Выберите фото") },
            text = { Text("Откуда взять аватарку?") },
            confirmButton = {
                TextButton(onClick = {
                    showImageSourceDialog = false

                    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        Manifest.permission.READ_MEDIA_IMAGES
                    } else {
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    }

                    val hasPermission = androidx.core.content.ContextCompat.checkSelfPermission(
                        context, permission
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

                    if (hasPermission) {
                        galleryLauncher.launch("image/*")
                    } else {
                        galleryPermissionLauncher.launch(permission)
                    }
                }) { Text("Галерея") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showImageSourceDialog = false
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }) { Text("Камера") }
            }
        )
    }
}