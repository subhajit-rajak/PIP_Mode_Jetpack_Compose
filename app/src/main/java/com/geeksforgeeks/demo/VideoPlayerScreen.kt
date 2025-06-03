package com.geeksforgeeks.demo

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Composable function that displays a VideoView inside a Compose UI using AndroidView interop.
 *
 * @param videoUrl The URL of the video to be played.
 * @param onBoundsChanged Callback to report the video’s on-screen position for PiP purposes.
 */
@Composable
fun VideoPlayerScreen(
    videoUrl: Uri,
    onBoundsChanged: (android.graphics.Rect) -> Unit
) {
    AndroidView(
        factory = { context ->
            // Creates a VideoView and configures it to play the given video
            VideoView(context).apply {
                setVideoURI(videoUrl)  // Set the video source
                start()                // Start playback automatically
                VideoController.setVideoView(this)  // Register with VideoController for external control (e.g., play/pause from PiP)
            }
        },
        modifier = Modifier
            .fillMaxWidth() // Makes the video view fill the width of the parent
            .onGloballyPositioned {
                // Captures the position and size of the VideoView on the screen
                // Converts Compose Rect to Android Rect for compatibility with PiP APIs
                onBoundsChanged(it.boundsInWindow().toAndroidRect())
            }
    )
}