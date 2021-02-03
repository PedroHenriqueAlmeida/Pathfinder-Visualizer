package com.pedro.pathfindervisualizer.utils;

import android.util.Log;

public abstract class ThreadUtils {
    private static final String TAG = "THREAD-UTILS";

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            Log.e(TAG, "sleep: ", interruptedException);
        }
    }

    public static void run(Runnable runnable) {
        new Thread() {
            @Override
            public void run() {
                runnable.run();
            }
        }.start();
    }
}
