package kr.co.huve.AutoTouchManagerLib;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.List;

import kr.co.huve.AutoTouchManagerLib.Data.TouchData;

public class TouchGeneratorTask implements Runnable {
    private Handler mainHandler;
    private List<TouchData> data;
    private boolean isCanceled;

    public TouchGeneratorTask(@NonNull List<TouchData> data) {
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.data = data;
    }

    @Override
    public void run() {
        for (TouchData event : data) {
            try {
                Thread.sleep(event.getDelay());
            } catch (InterruptedException e) {
                isCanceled = true;
                break;
            }
            GestureDescription gestureDescription = TouchSimulateService.getInstance().createClick(event.getScreenX(), event.getScreenY());
            TouchSimulateService.getInstance().dispatchGesture(gestureDescription, callback, null);
            if (event.getRunnable() != null) {
                mainHandler.post(event.getRunnable());
            }
        }
    }

    private AccessibilityService.GestureResultCallback callback = new AccessibilityService.GestureResultCallback() {
        @Override
        public void onCompleted(GestureDescription gestureDescription) {
            super.onCompleted(gestureDescription);
            isCanceled = false;
        }

        @Override
        public void onCancelled(GestureDescription gestureDescription) {
            super.onCancelled(gestureDescription);
            isCanceled = true;
        }
    };

    public boolean isCanceled() {
        return isCanceled;
    }
}
