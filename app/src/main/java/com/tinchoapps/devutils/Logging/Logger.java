package com.tinchoapps.devutils.Logging;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.tinchoapps.devutils.Utils;

public final class Logger
{
    private Logger()
    {
    }

    public static void debug(@NonNull final Context context, @NonNull final String message)
    {
        debug(context, null, message, false);
    }

    public static void debug(@NonNull final Context context, @Nullable final String tag, @NonNull final String message)
    {
        debug(context, tag, message, false);
    }

    public static void debug(@NonNull final Context context, @Nullable final String tag, @NonNull final String message, final boolean displayToast)
    {
        if (Utils.isDebugBuild(context))
        {
            Log.d(getTag(tag), message);

            if (displayToast)
            {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void info(String info)
    {
        info(null, info);
    }

    public static void info(@Nullable final String tag, String info)
    {
        Log.i(getTag(tag), info);
    }

    public static void warning(String message)
    {
        warning(null, message);
    }

    public static void warning(@Nullable final String tag, String message)
    {
        Log.w(getTag(tag), message);
    }

    public static void error(String error)
    {
        error(null, error, null, false);
    }

    public static void error(String error, @Nullable Exception e)
    {
        error(null, error, e, false);
    }

    public static void error(String error, @Nullable Exception e, boolean sendToAnalytics)
    {
        error(null, error, e, sendToAnalytics);
    }

    public static void error(@Nullable final String tag, String error, @Nullable Exception e, boolean sendToAnalytics)
    {
        Log.e(getTag(tag), error + "\n");

        if (e != null)
        {
            e.printStackTrace();
        }
    }

    private static String getTag(@Nullable final String proposedTag)
    {
        String result;
        if (proposedTag == null)
        {
            result = Utils.getCallerCallerClassName();
        } else
        {
            result = proposedTag;
        }

        return result;
    }
}
