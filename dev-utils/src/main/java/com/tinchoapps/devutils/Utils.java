package com.tinchoapps.devutils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;

public final class Utils
{
    private Utils()
    {
    }

    public static boolean isDebugBuild(@NonNull final Context context)
    {
        return (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param context Context to get resources and device specific display metrics
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float dpToPixel(@NonNull final Context context, final float dp)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param context Context to get resources and device specific display metrics
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float pixelsToDp(@NonNull final Context context, final float px)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    public static Point getScreenSize(@NonNull final Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            display.getSize(size);
        } else
        {
            size.set(display.getWidth(), display.getHeight());
        }

        return size;
    }

    @Nullable
    public static File getPictureDirectoryForApp(String dirName)
    {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirName);

        if (!mediaStorageDir.exists())
        {
            if (!mediaStorageDir.mkdirs())
            {
                return null;
            }
        }

        return mediaStorageDir;
    }

    /**
     * Throws AssertionError if the input is false.
     */
    public static void assertTrue(boolean condition)
    {
        if (!condition)
        {
            throw new AssertionError();
        }
    }

    /**
     * Returns the next power of two.
     * Returns the input if it is already power of 2.
     * Throws IllegalArgumentException if the input is <= 0 or
     * the answer overflows.
     */
    public static int nextPowerOf2(int n)
    {
        if (n <= 0 || n > (1 << 30)) throw new IllegalArgumentException("n is invalid: " + n);
        n -= 1;
        n |= n >> 16;
        n |= n >> 8;
        n |= n >> 4;
        n |= n >> 2;
        n |= n >> 1;
        return n + 1;
    }

    /**
     * Returns the previous power of two.
     * Returns the input if it is already power of 2.
     * Throws IllegalArgumentException if the input is <= 0
     */
    public static int prevPowerOf2(int n)
    {
        if (n <= 0) throw new IllegalArgumentException();
        return Integer.highestOneBit(n);
    }
}
