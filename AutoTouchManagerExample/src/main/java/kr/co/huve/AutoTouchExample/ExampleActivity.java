package kr.co.huve.AutoTouchExample;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import kr.co.huve.AutoTouchManagerLib.AutoTouchManager;
import kr.co.huve.AutoTouchManagerLib.Data.TouchData;

public class ExampleActivity extends AppCompatActivity {
    private View background;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = findViewById(R.id.background);
    }

    //region sample of usage

    private void sampleAutoTouch() {
        // Create the virtual touch List.
        // The events below occur sequentially.
        ArrayList<TouchData> list = new ArrayList<>();
        // 'Touch 1' occurs immediately.
        list.add(new TouchData(100, 100));
        // 'Touch 2' occurs after 'Touch 1' finished.
        list.add(new TouchData(100, 200));
        // 'Touch 3' occurs after 'Touch 2' finished.
        list.add(new TouchData(100, 300));
        // 'Touch 4' occurs after 'Touch 3' finished.
        list.add(new TouchData(100, 400));
        // 'Touch 5' occurs after 'Touch 4' finished.
        list.add(new TouchData(100, 500));
        // 'Touch 6' occurs after 'Touch 5' finished.
        list.add(new TouchData(100, 600));
        // 'Touch 7' occurs after 'Touch 6' finished.
        list.add(new TouchData(100, 700));

        // Set the touch preset on the AutoTouchManager.
        AutoTouchManager.getInstance().setTouchEventPreset(list);
        // Play the touch preset.
        AutoTouchManager.getInstance().play(this);
    }

    private void sampleAutoTouchWithDelay() {
        // Create the virtual touch List.
        // The events below occur sequentially.
        ArrayList<TouchData> list = new ArrayList<>();
        // 'Touch 1' occurs after 3000ms.
        list.add(new TouchData(100, 100, 3000));
        // 'Touch 2' occurs 3000ms after 'Touch 1' is complete.
        list.add(new TouchData(100, 200, 3000));
        // 'Touch 3' occurs 3000ms after 'Touch 2' is complete.
        list.add(new TouchData(100, 300, 3000));

        // Set the touch preset on the AutoTouchManager.
        AutoTouchManager.getInstance().setTouchEventPreset(list);
        // Play the touch preset.
        AutoTouchManager.getInstance().play(this);
    }

    private void sampleAutoTouchWithRunnable() {
        // Create the virtual touch List.
        // The events below occur sequentially.
        ArrayList<TouchData> list = new ArrayList<>();
        // 'Touch 1' occurs immediately.
        list.add(new TouchData(100, 100, new Runnable() {
            @Override
            public void run() {
                // This Runnable execute after 'Touch 1' finished.
                Log.d("EXAMPLE", "Touch1");
            }
        }));
        // 'Touch 2' occurs after 'Touch 1' finished.
        list.add(new TouchData(100, 200, new Runnable() {
            @Override
            public void run() {
                // This Runnable execute after 'Touch 2' finished.
                Log.d("EXAMPLE", "Touch2");
            }
        }));
        // 'Touch 3' occurs after 'Touch 2' finished.
        list.add(new TouchData(100, 300, new Runnable() {
            @Override
            public void run() {
                // This Runnable execute after 'Touch 3' finished.
                Log.d("EXAMPLE", "Touch3");
            }
        }));

        // Set the touch preset on the AutoTouchManager.
        AutoTouchManager.getInstance().setTouchEventPreset(list);
        // Play the touch preset.
        AutoTouchManager.getInstance().play(this);
    }

    private void sampleAutoTouchWithDelayRunnable() {
        // Create the virtual touch List.
        // The events below occur sequentially.
        ArrayList<TouchData> list = new ArrayList<>();
        // 'Touch 1' occurs after 3000ms.
        list.add(new TouchData(100, 100, 3000, new Runnable() {
            @Override
            public void run() {
                // This Runnable execute after 'Touch 1' finished.
                background.setBackgroundColor(Color.RED);
            }
        }));
        // 'Touch 2' occurs 3000ms after 'Touch 1' is complete.
        list.add(new TouchData(100, 200, 3000, new Runnable() {
            @Override
            public void run() {
                // This Runnable execute after 'Touch 2' finished.
                background.setBackgroundColor(Color.GREEN);
            }
        }));
        // 'Touch 3' occurs 3000ms after 'Touch 2' is complete.
        list.add(new TouchData(100, 300, 3000, new Runnable() {
            @Override
            public void run() {
                // This Runnable execute after 'Touch 3' finished.
                background.setBackgroundColor(Color.WHITE);
            }
        }));

        // Set the touch preset on the AutoTouchManager.
        AutoTouchManager.getInstance().setTouchEventPreset(list);
        // Play the touch preset.
        AutoTouchManager.getInstance().play(this);
    }

    private void sampleHomeButton() {
        // Home button event occur immediately.
        AutoTouchManager.getInstance().performHomeButtonClick(this);
    }

    private void sampleBackButton() {
        // Back button event occur immediately.
        AutoTouchManager.getInstance().performBackButtonClick(this);
    }

    //endregion sample of usage

    //region Click event field

    public void onClickSampleTouch(View view) {
        sampleAutoTouch();
    }

    public void onClickSampleTouchWithDelay(View view) {
        sampleAutoTouchWithDelay();
    }

    public void onClickSampleTouchWithRunnable(View view) {
        sampleAutoTouchWithRunnable();
    }

    public void onClickSampleTouchWithDelayRunnable(View view) {
        sampleAutoTouchWithDelayRunnable();
    }

    public void onClickBackButton(View view) {
        sampleBackButton();
    }

    public void onClickHomeButton(View view) {
        sampleHomeButton();
    }

    //endregion Click event field
}
