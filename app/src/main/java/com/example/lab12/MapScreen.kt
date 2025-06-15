package com.example.lab12

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.example.lab12.R

@Composable
fun MapScreen() {
    val context = LocalContext.current

    // Centro de la cámara en Arequipa
    val arequipaLocation = LatLng(-16.4040102, -71.559611)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(arequipaLocation, 12f)
    }

    // Cargar el ícono personalizado
    var customIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }
    LaunchedEffect(context) {
        customIcon = BitmapDescriptorFactory.fromResource(R.drawable.montana)
    }

    // Lista de ubicaciones
    val locations = listOf(
        LatLng(-16.433415, -71.5442652), // JLByR
        LatLng(-16.4205151, -71.4945209), // Paucarpata
        LatLng(-16.3524187, -71.5675994)  // Zamacola
    )

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Agregar un marcador para cada ubicación
            locations.forEachIndexed { index, location ->
                customIcon?.let {
                    Marker(
                        state = rememberMarkerState(position = location),
                        icon = it,
                        title = "Ubicación ${index + 1}",
                        snippet = "Punto de interés"
                    )
                }
            }
        }
    }
}
