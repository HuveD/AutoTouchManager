package kr.co.huve.AutoTouchManagerLib;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import kr.co.huve.AsynchronousTaskManager.AsynchronousTaskManager;
import kr.co.huve.AsynchronousTaskManager.AtmTask;
import kr.co.huve.AsynchronousTaskManager.EventListener.OnTaskEndListener;
import kr.co.huve.AutoTouchManagerLib.Data.TaskType;
import kr.co.huve.AutoTouchManagerLib.Data.TouchData;
import kr.co.huve.autotouchmanager.R;

public class AutoTouchManager {

    // The touch event preset.
    private List<TouchData> preset;
    // The listener that notify auto touch event finished.
    private OnFinishListener onFinishListener;
    // The listener that notify error.
    private OnErrorListener onErrorListener;

    /**
     * Get {@link AutoTouchManager} instance.
     *
     * @return The singleton instance.
     */
    public static AutoTouchManager getInstance() {
        return AutoTouchManagerHolder.instance;
    }

    //region Common method field

    /**
     * Simulate the preset touch event.
     *
     * @return Is success to dispatch event.
     */
    public boolean play(@NonNull final AppCompatActivity activityContext) {
        if (isAvailableAccessibilityService(activityContext)) {
            if (preset == null || preset.size() == 0) {
                return false;
            } else {
                // Generate the touch event.
                final TouchGeneratorTask touchGeneratorTask = new TouchGeneratorTask(preset);
                AtmTask task = AsynchronousTaskManager.getInstance().createTask(touchGeneratorTask, TaskType.GENERATE_VIRTUAL_TOUCH.ordinal());
                task.setOnTaskEndListener(new OnTaskEndListener() {
                    @Override
                    public void onTaskEnd(int taskId) {
                        if (touchGeneratorTask.isCanceled()) {
                            if (onErrorListener != null) {
                                onErrorListener.onError("The auto touch event was canceled, because of user input.");
                            }
                        } else if (onFinishListener != null) {
                            onFinishListener.onFinish();
                        }
                    }
                });
                task.active();
                return true;
            }
        } else {
            showPermissionDialog(activityContext);
            return false;
        }
    }

    /**
     * Check permission about AccessibilityService.
     *
     * @return Whether the AccessibilityService is available.
     */
    private boolean isAvailableAccessibilityService(@NonNull Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager != null) {
            List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.DEFAULT);
            for (int i = 0; i < list.size(); i++) {
                AccessibilityServiceInfo info = list.get(i);
                if (info.getResolveInfo().serviceInfo.packageName.equals(context.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void showPermissionDialog(final AppCompatActivity activityContext) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activityContext);
        dialogBuilder.setTitle(activityContext.getString(R.string.check_accessibility_permission_title));
        dialogBuilder.setMessage(activityContext.getString(R.string.check_accessibility_permission_content));
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton(activityContext.getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Check permission
                activityContext.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        }).create().show();
    }

    /**
     * Generate the home button click event.
     */
    public boolean performHomeButtonClick(AppCompatActivity activityContext) {
        if (TouchSimulateService.getInstance() != null) {
            TouchSimulateService.getInstance().performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
            return true;
        } else {
            showPermissionDialog(activityContext);
            return false;
        }
    }

    /**
     * Generate the back button click event.
     */
    public boolean performBackButtonClick(AppCompatActivity activityContext) {
        if (TouchSimulateService.getInstance() != null) {
            TouchSimulateService.getInstance().performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            return true;
        } else {
            showPermissionDialog(activityContext);
            return false;
        }
    }

    //endregion Common method field

    //region Getter & Setter

    /**
     * Set the touch preset
     *
     * @param touchDataList The event data to simulate the touch events.
     */
    public void setTouchEventPreset(@NonNull List<TouchData> touchDataList) {
        preset = touchDataList;
    }

    /**
     * Clear the event preset.
     */
    public void clearPreset() {
        if (preset != null && preset.size() != 0) preset.clear();
    }

    public void setOnFinishListener(OnFinishListener listener) {
        onFinishListener = listener;
    }

    public void setOnErrorListener(OnErrorListener listener) {
        onErrorListener = listener;
    }

    //endregion Getter & Setter

    //region Interface

    public interface OnFinishListener {
        void onFinish();
    }

    public interface OnErrorListener {
        void onError(String message);
    }

    //endregion Interface

    //region Lazy singleton holder

    private static class AutoTouchManagerHolder {
        private static final AutoTouchManager instance = new AutoTouchManager();
    }

    //endregion Lazy singleton holder
}
