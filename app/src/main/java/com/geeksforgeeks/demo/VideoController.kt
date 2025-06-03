package com.geeksforgeeks.demo

import android.widget.VideoView
import java.lang.ref.WeakReference

/**
 * VideoController is a singleton object that provides centralized control over video playback.
 * It allows external components (like the PiP Receiver) to toggle playback without direct access to the VideoView.
 */
object VideoController {

    // A weak reference to the VideoView to avoid memory leaks
    private var videoViewRef: WeakReference<VideoView>? = null

    /**
     * Registers the VideoView instance for external control.
     * Stores it as a weak reference to prevent memory leaks.
     */
    fun setVideoView(videoView: VideoView) {
        videoViewRef = WeakReference(videoView)
    }

    /**
     * Toggles the video playback state.
     * If the video is playing, it pauses it; otherwise, it starts playback.
     */
    fun togglePlayPause() {
        videoViewRef?.get()?.let { videoView ->
            if (videoView.isPlaying) {
                videoView.pause()
            } else {
                videoView.start()
            }
        }
    }
}