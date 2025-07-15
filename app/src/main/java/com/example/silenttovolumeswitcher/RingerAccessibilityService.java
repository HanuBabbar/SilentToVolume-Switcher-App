package com.example.silenttovolumeswitcher;


import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;

public class RingerAccessibilityService extends AccessibilityService {

    private Handler handler = new Handler();
    private boolean alreadyScheduled = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        monitorRingerMode();
    }

    private void monitorRingerMode() {
        if (alreadyScheduled) return;

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int mode = audioManager.getRingerMode();

        if (mode == AudioManager.RINGER_MODE_SILENT || mode == AudioManager.RINGER_MODE_VIBRATE) {
            alreadyScheduled = true;

            handler.postDelayed(() -> {
                int currentMode = audioManager.getRingerMode();
                if (currentMode != AudioManager.RINGER_MODE_NORMAL) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                alreadyScheduled = false;
            }, 10 * 1000); // 30 minutes
        }
    }

    @Override
    public void onInterrupt() {}
}
