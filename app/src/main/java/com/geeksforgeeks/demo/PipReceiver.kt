package com.geeksforgeeks.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * PipReceiver listens for broadcast actions triggered from Picture-in-Picture (PiP) mode.
 * Specifically, it responds to the custom play/pause action added to the PiP controls.
 */
class PipReceiver : BroadcastReceiver() {

    /**
     * Called when the broadcast associated with the PiP action is received.
     * This triggers playback to toggle (play or pause) via the VideoController.
     */
    override fun onReceive(context: Context, intent: Intent) {
        VideoController.togglePlayPause() // Toggle video playback state
    }
}
