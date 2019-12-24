package kr.co.huve.AutoTouchManagerLib.Data;

public class TouchData {
    private final float screenY;
    private final float screenX;
    private final long delay;

    private Runnable runnable;

    /**
     * @param screenX The x coordinate of the screen.
     * @param screenY The y coordinate of the screen.
     */
    public TouchData(float screenX, float screenY) {
        this(screenX, screenY, 0, null);
    }

    /**
     * @param screenX The x coordinate of the screen.
     * @param screenY The y coordinate of the screen.
     * @param delay   The time that is a delay to touch event.
     */
    public TouchData(float screenX, float screenY, long delay) {
        this(screenX, screenY, delay, null);
    }

    /**
     * @param screenX  The x coordinate of the screen.
     * @param screenY  The y coordinate of the screen.
     * @param runnable The task you want to do after touch.
     */
    public TouchData(float screenX, float screenY, Runnable runnable) {
        this(screenX, screenY, 0, null);
    }

    /**
     * @param screenX  The x coordinate of the screen.
     * @param screenY  The y coordinate of the screen.
     * @param delay    The time that is a delay to touch event.
     * @param runnable The task you want to do after touch.
     */
    public TouchData(float screenX, float screenY, long delay, Runnable runnable) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.delay = delay;
        this.runnable = runnable;
    }

    //region Getter & Setter

    public long getDelay() {
        return delay;
    }

    public float getScreenY() {
        return screenY;
    }

    public float getScreenX() {
        return screenX;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    //endregion Getter & Setter
}
