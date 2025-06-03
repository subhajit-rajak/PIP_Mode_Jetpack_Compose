package com.geeksforgeeks.demo

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.util.Rational
import androidx.annotation.RequiresApi

/**
 * PipManager handles all Picture-in-Picture (PiP) related functionality,
 * such as entering PiP mode and setting up custom actions.
 */
class PipManager(private val context: Context) {

    // Holds the bounds of the video on the screen, used to hint PiP position
    private var videoBounds: Rect = Rect()

    companion object {
        // Static video URL used by the player
        val VIDEO_URL: Uri =
            Uri.parse("https://www.sample-videos.com/video321/mp4/720/big_buck_bunny_720p_30mb.mp4")
    }

    // Check if PiP is supported on the current device
    private val isPipSupported: Boolean
        get() = context.packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)

    /**
     * Updates the bounds of the video view. These bounds are used as a source hint
     * for positioning the PiP window when it appears.
     */
    fun updateBounds(bounds: Rect) {
        videoBounds = bounds
    }

    /**
     * Enters Picture-in-Picture mode if supported by the device and OS version.
     * Configures the PiP parameters such as aspect ratio, source rect hint,
     * and adds a custom action (play/pause button).
     */
    fun enterPipModeIfSupported() {
        // Exit early if PiP is not supported or the API level is below 26
        if (!isPipSupported || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        // Cast context to Activity (required for PiP mode)
        val activity = context as? android.app.Activity ?: return

        // Build PiP parameters
        val pipParams = PictureInPictureParams.Builder()
            .setSourceRectHint(videoBounds) // Hint for initial PiP window placement
            .setAspectRatio(Rational(16, 9)) // Set fixed aspect ratio
            .setActions(listOf(buildRemoteAction())) // Add custom PiP action
            .build()

        // Request system to enter PiP mode
        activity.enterPictureInPictureMode(pipParams)
    }

    /**
     * Creates a custom RemoteAction (e.g. Play/Pause button) that appears in the PiP window.
     * The action sends a broadcast, which is received by PipReceiver to toggle playback.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildRemoteAction(): RemoteAction {
        // Create an Intent to broadcast when the PiP action is clicked
        val intent = Intent(context, PipReceiver::class.java)

        // Wrap the intent in a PendingIntent (required for RemoteAction)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Create and return the RemoteAction with an icon, title, description, and intent
        return RemoteAction(
            Icon.createWithResource(context, R.drawable.play_pause), // Icon shown in PiP
            "Play/Pause", // Title
            "Toggle playback", // Description
            pendingIntent // Action intent
        )
    }
}
