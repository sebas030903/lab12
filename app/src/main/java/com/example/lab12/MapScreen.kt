package com.example.lab12

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.*
import com.example.lab12.R

@Composable
fun MapScreen() {
    val context = LocalContext.current

    val initialLocation = LatLng(-16.4040102, -71.559611)
    val yuraLocation = LatLng(-16.2520984, -71.6836503)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLocation, 12f)
    }

    var customIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }

    // Cargar ícono personalizado
    LaunchedEffect(context) {
        customIcon = BitmapDescriptorFactory.fromResource(R.drawable.montana)
    }

    // Mover cámara a Yura
    LaunchedEffect(Unit) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(yuraLocation, 12f),
            durationMs = 3000
        )
    }

    // Lista de ubicaciones con títulos
    val locations = listOf(
        "JLByR" to LatLng(-16.433415, -71.5442652),
        "Paucarpata" to LatLng(-16.4205151, -71.4945209),
        "Zamacola" to LatLng(-16.3524187, -71.5675994)
    )

    // Definición de polígonos
    val mallAventuraPolygon = listOf(
        LatLng(-16.432292, -71.509145),
        LatLng(-16.432757, -71.509626),
        LatLng(-16.433013, -71.509310),
        LatLng(-16.432566, -71.508853)
    )

    val parqueLambramaniPolygon = listOf(
        LatLng(-16.422704, -71.530830),
        LatLng(-16.422920, -71.531340),
        LatLng(-16.423264, -71.531110),
        LatLng(-16.423050, -71.530600)
    )

    val plazaDeArmasPolygon = listOf(
        LatLng(-16.398866, -71.536961),
        LatLng(-16.398744, -71.536529),
        LatLng(-16.399178, -71.536289),
        LatLng(-16.399299, -71.536721)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Marcadores
            locations.forEach { (title, location) ->
                customIcon?.let {
                    Marker(
                        state = rememberMarkerState(position = location),
                        icon = it,
                        title = title,
                        snippet = "Punto de interés"
                    )
                }
            }

            // Polígonos
            Polygon(
                points = plazaDeArmasPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Blue.copy(alpha = 0.3f),
                strokeWidth = 5f
            )

            Polygon(
                points = parqueLambramaniPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Blue.copy(alpha = 0.3f),
                strokeWidth = 5f
            )

            Polygon(
                points = mallAventuraPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Blue.copy(alpha = 0.3f),
                strokeWidth = 5f
            )
        }
    }
}
