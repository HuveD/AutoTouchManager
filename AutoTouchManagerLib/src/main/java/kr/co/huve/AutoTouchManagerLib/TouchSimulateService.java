package kr.co.huve.AutoTouchManagerLib;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.view.accessibility.AccessibilityEvent;

public class TouchSimulateService extends AccessibilityService {
    private static TouchSimulateService instance;

    @Override
    public void onServiceConnected() {
        if (instance == null) {
            instance = this;
        }
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();

        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.DEFAULT | AccessibilityServiceInfo.FEEDBACK_HAPTIC;
        info.notificationTimeout = 100; // millisecond
        setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (instance == null) {
            instance = this;
        }
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //region Common method field

    /**
     * Create a gesture event to simulate touch event.
     *
     * @param x The X coordinate to generate the touch event.
     * @param y The Y coordinate to generate the touch event.
     * @return The gesture to simulate touch event.
     */
    GestureDescription createClick(float x, float y) {
        // for a single tap a duration of 1 ms is enough
        final int DURATION = 1;

        Path clickPath = new Path();
        clickPath.moveTo(x, y);
        GestureDescription.StrokeDescription clickStroke =
                new GestureDescription.StrokeDescription(clickPath, 0, DURATION);
        GestureDescription.Builder clickBuilder = new GestureDescription.Builder();
        clickBuilder.addStroke(clickStroke);
        return clickBuilder.build();
    }

    //endregion Common method field

    //region Getter & Setter

    static TouchSimulateService getInstance() {
        return instance;
    }

    //endregion Getter & Setter
}