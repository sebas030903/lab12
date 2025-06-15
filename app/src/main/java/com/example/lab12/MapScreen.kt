package com.example.lab12

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen() {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var mapType by remember { mutableStateOf(MapType.NORMAL) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-16.4040102, -71.559611), 12f) // Arequipa
    }

    // Obtener ubicación
    LaunchedEffect(locationPermissionState.status) {
        if (locationPermissionState.status.isGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                }
            }
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Botones para cambiar el tipo de mapa
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { mapType = MapType.NORMAL }) { Text("Normal") }
            Button(onClick = { mapType = MapType.SATELLITE }) { Text("Satélite") }
            Button(onClick = { mapType = MapType.TERRAIN }) { Text("Terreno") }
        }

        GoogleMap(
            modifier = Modifier.weight(1f),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = locationPermissionState.status.isGranted,
                mapType = mapType
            ),
            uiSettings = MapUiSettings(zoomControlsEnabled = true)
        ) {
            // Marcadores fijos
            val locations = listOf(
                LatLng(-16.433415, -71.5442652), // JLByR
                LatLng(-16.4205151, -71.4945209), // Paucarpata
                LatLng(-16.3524187, -71.5675994) // Zamacola
            )

            locations.forEachIndexed { index, location ->
                Marker(
                    state = rememberMarkerState(position = location),
                    title = "Punto ${index + 1}"
                )
            }

            // Ubicación actual
            userLocation?.let {
                Marker(
                    state = rememberMarkerState(position = it),
                    title = "Mi ubicación"
                )
            }
        }
    }
}
