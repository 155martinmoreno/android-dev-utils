package com.tinchoapps.devutils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

public final class Utils
{
    private Utils()
    {
    }

    public static boolean isDebugBuild(final Context context)
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
    public static float dpToPixel(Context context, float dp)
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
    public static float pixelsToDp(Context context, float px)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    /**
     * From http://stackoverflow.com/a/11306854.
     * Gets the caller class name via reflection;
     *
     * @return caller class name
     */
    public static
    @Nullable
    String getCallerCallerClassName()
    {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i = 1; i < stElements.length; i++)
        {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Utils.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0)
            {
                if (callerClassName == null)
                {
                    callerClassName = ste.getClassName();
                } else if (!callerClassName.equals(ste.getClassName()))
                {
                    return ste.getClassName();
                }
            }
        }
        return null;
    }
}
