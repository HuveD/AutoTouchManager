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
        // List of coordinates to touch
        ArrayList<TouchData> list = new ArrayList<>();
        list.add(new TouchData(100, 100));
        list.add(new TouchData(100, 200));
        list.add(new TouchData(100, 300));
        list.add(new TouchData(100, 400));
        list.add(new TouchData(100, 500));
        list.add(new TouchData(100, 600));
        list.add(new TouchData(100, 700));

        // Set the touch preset and play the virtual touch
        AutoTouchManager.getInstance().setTouchEventPreset(list);
        AutoTouchManager.getInstance().play(this);
    }

    private void sampleAutoTouchWithDelay() {
        // List of coordinates to touch
        ArrayList<TouchData> list = new ArrayList<>();
        list.add(new TouchData(100, 100, 3000));
        list.add(new TouchData(100, 200, 3000));
        list.add(new TouchData(100, 300, 3000));

        // Set the touch preset and play the virtual touch
        AutoTouchManager.getInstance().setTouchEventPreset(list);
        AutoTouchManager.getInstance().play(this);
    }

    private void sampleAutoTouchWithRunnable() {
        // List of coordinates to touch
        ArrayList<TouchData> list = new ArrayList<>();
        list.add(new TouchData(100, 100, new Runnable() {
            @Override
            public void run() {
                Log.d("EXAMPLE", "Touch1");
            }
        }));
        list.add(new TouchData(100, 200, new Runnable() {
            @Override
            public void run() {
                Log.d("EXAMPLE", "Touch2");
            }
        }));
        list.add(new TouchData(100, 300, new Runnable() {
            @Override
            public void run() {
                Log.d("EXAMPLE", "Touch3");
            }
        }));

        // Set the touch preset and play the virtual touch
        AutoTouchManager.getInstance().setTouchEventPreset(list);
        AutoTouchManager.getInstance().play(this);
    }

    private void sampleAutoTouchWithDelayRunnable() {
        // List of coordinates to touch
        ArrayList<TouchData> list = new ArrayList<>();
        list.add(new TouchData(100, 100, 3000, new Runnable() {
            @Override
            public void run() {
                background.setBackgroundColor(Color.RED);
            }
        }));
        list.add(new TouchData(100, 200, 3000, new Runnable() {
            @Override
            public void run() {
                background.setBackgroundColor(Color.GREEN);
            }
        }));
        list.add(new TouchData(100, 300, 3000, new Runnable() {
            @Override
            public void run() {
                background.setBackgroundColor(Color.WHITE);
            }
        }));

        // Set the touch preset and play the virtual touch
        AutoTouchManager.getInstance().setTouchEventPreset(list);
        AutoTouchManager.getInstance().play(this);
    }

    private void sampleHomeButton() {
        AutoTouchManager.getInstance().performHomeButtonClick(this);
    }

    private void sampleBackButton() {
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
