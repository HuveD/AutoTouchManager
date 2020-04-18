package kr.co.huve.AutoTouchManagerLib.Data;

public class DoubleTabData extends TouchData {
    public DoubleTabData(float screenX, float screenY) {
        super(screenX, screenY);
    }

    public DoubleTabData(float screenX, float screenY, long delay) {
        super(screenX, screenY, delay);
    }

    public DoubleTabData(float screenX, float screenY, Runnable runnable) {
        super(screenX, screenY, runnable);
    }

    public DoubleTabData(float screenX, float screenY, long delay, Runnable runnable) {
        super(screenX, screenY, delay, runnable);
    }
}
