package com.geeksforgeeks.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

/**
 * MainActivity is the entry point of the app and hosts the Jetpack Compose UI.
 * It also integrates Picture-in-Picture (PiP) functionality using PipManager.
 */
class MainActivity : ComponentActivity() {

    // Manages Picture-in-Picture logic such as entering PiP mode and updating bounds
    private lateinit var pipManager: PipManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize PipManager with the current Activity context
        pipManager = PipManager(this)

        // Set the Compose UI content
        setContent {
            MaterialTheme {
                // Display the video player and provide a callback to report its screen bounds
                VideoPlayerScreen(
                    videoUrl = PipManager.VIDEO_URL, // Static video URL
                    onBoundsChanged = { pipManager.updateBounds(it) } // Report video bounds to PipManager
                )
            }
        }
    }

    /**
     * Called when the user is about to leave the activity (e.g., pressing the Home button).
     * This is the trigger point for entering Picture-in-Picture mode.
     */
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        pipManager.enterPipModeIfSupported() // Attempt to enter PiP mode if supported
    }
}