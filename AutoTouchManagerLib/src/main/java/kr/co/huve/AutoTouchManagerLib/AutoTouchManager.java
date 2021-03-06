package kr.co.huve.AutoTouchManagerLib;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

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
    final static String TAG = AutoTouchManager.class.getSimpleName();

    // The touch event preset.
    private List<TouchData> preset;
    // The listener that notify auto touch event finished.
    private OnFinishListener onFinishListener;
    // The listener that notify error.
    private OnErrorListener onErrorListener;

    // Permission text resource
    private String dialogTitle = "";
    private String dialogContent = "";
    private String dialogButtonContent = "";

    /**
     * Get {@link AutoTouchManager} instance.
     *
     * @return The singleton instance.
     */
    public static AutoTouchManager getInstance() {
        if (AutoTouchManagerHolder.instance == null) {
            AutoTouchManagerHolder.instance = new AutoTouchManager();
        }
        return AutoTouchManagerHolder.instance;
    }

    //region Common method field

    /**
     * Customize dialog text.
     *
     * @param title         The title that wants to change.
     * @param content       The dialog content that wants to change.
     * @param buttonContent The button text that wants to change.
     */
    public void initializeDialogResource(String title, String content, String buttonContent) {
        this.dialogTitle = title;
        this.dialogContent = content;
        this.dialogButtonContent = buttonContent;
    }

    /**
     * Simulate the preset touch event.
     *
     * @return Is success to dispatch event.
     */
    public boolean play(@NonNull final Context context) {
        if (isAvailableAccessibilityService(context)) {
            if (preset == null || preset.size() == 0) {
                outErrorLog(context, R.string.no_preset);
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
            outErrorLog(context, R.string.no_preset);
            showPermissionDialog(context);
            return false;
        }
    }

    /**
     * Check permission about AccessibilityService.
     *
     * @return Whether the AccessibilityService is available.
     */
    public boolean isAvailableAccessibilityService(@NonNull Context context) {
        int accessibilityEnabled;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            if (accessibilityEnabled == 1) {
                String settingValue = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (settingValue != null) {
                    String[] packageList = settingValue.split(":");
                    for (String packageName : packageList) {
                        if (packageName.contains(context.getPackageName())) {
                            return true;
                        }
                    }
                }
            }
        } catch (Settings.SettingNotFoundException e) {
            // can't find accessibility service.
        }
        outErrorLog(context, R.string.require_accessibility_permission);
        return false;
    }

    private void showPermissionDialog(final Context context) {
        // Check custom dialog text
        boolean hasCustomDialogText = dialogTitle.length() != 0 && dialogContent.length() != 0 && dialogButtonContent.length() != 0;

        // Make dialog and show dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(hasCustomDialogText ? dialogTitle : context.getString(R.string.check_accessibility_permission_title));
        dialogBuilder.setMessage(hasCustomDialogText ? dialogContent : context.getString(R.string.check_accessibility_permission_content));
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton(hasCustomDialogText ? dialogButtonContent : context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Check permission
                context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        }).create().show();
    }

    /**
     * Generate the home button click event.
     */
    public boolean performHomeButtonClick(AppCompatActivity activityContext) {
        if (isAvailableAccessibilityService(activityContext)) {
            TouchSimulateService.getInstance().performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
            return true;
        } else {
            showPermissionDialog(activityContext);
            outErrorLog(activityContext, R.string.require_accessibility_permission);
            return false;
        }
    }

    /**
     * Generate the back button click event.
     */
    public boolean performBackButtonClick(AppCompatActivity activityContext) {
        if (isAvailableAccessibilityService(activityContext)) {
            TouchSimulateService.getInstance().performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            return true;
        } else {
            showPermissionDialog(activityContext);
            outErrorLog(activityContext, R.string.require_accessibility_permission);
            return false;
        }
    }

    private void outErrorLog(@NonNull Context context, int res) {
        final String err = context.getString(res);
        if (onErrorListener != null) {
            onErrorListener.onError(err);
        } else {
            Log.e(TAG, err);
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
        private static AutoTouchManager instance = new AutoTouchManager();
    }

    //endregion Lazy singleton holder
}
