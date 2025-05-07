package com.example.aquariumshopapp.ui.screens.payment.components

import org.osmdroid.config.Configuration
import android.preference.PreferenceManager
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun OsmMapView(navController: NavController) {
    val context = LocalContext.current
    val density = androidx.compose.ui.platform.LocalDensity.current
    val heightInPx = with(density) { 500.dp.toPx().toInt() }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        factory = { ctx ->
            val mapView = MapView(ctx)

            // Ép kích thước cụ thể cho MapView gốc
            mapView.layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                heightInPx
            )

            Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)

            val daNang = GeoPoint(16.0544, 108.2022)
            mapView.controller.setZoom(15.0)
            mapView.controller.setCenter(daNang)

            val marker = Marker(mapView)
            marker.position = daNang
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = "Cửa hàng tại Đà Nẵng"
            mapView.overlays.add(marker)

            mapView
        }
    )
}
