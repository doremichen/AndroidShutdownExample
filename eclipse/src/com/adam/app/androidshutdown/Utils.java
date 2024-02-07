/**
 * This is util tool
 */
package com.adam.app.androidshutdown;

import android.util.Log;

public final class Utils {
    private static final String TAG = "ShutDownDemo";
    private static final boolean ISLOG = true;
    
    public static void print(Object obj, String str) {
        if (ISLOG) {
            Log.i(TAG, obj.getClass().getSimpleName() + " " + str);
        }
    }
}
