package com.tinchoapps.devutils.Logging;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.tinchoapps.devutils.Utils;

public abstract class BaseLogger
{
    public void debug(@NonNull final String message)
    {
        debug(null, message, false);
    }

    public void debug(@Nullable final String tag, @NonNull final String message)
    {
        debug(tag, message, false);
    }

    public void debug(@Nullable final String tag, @NonNull final String message, final boolean displayToast)
    {
        Context context = getLoggerContext();

        if (Utils.isDebugBuild(context))
        {
            Log.d(getTag(tag), message);

            if (displayToast)
            {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void info(String info)
    {
        info(null, info);
    }

    public void info(@Nullable final String tag, String info)
    {
        Log.i(getTag(tag), info);
    }

    public void warning(String message)
    {
        warning(null, message);
    }

    public void warning(@Nullable final String tag, String message)
    {
        Log.w(getTag(tag), message);
    }

    public void error(String error)
    {
        error(null, error, null, false);
    }

    public void error(String error, @Nullable Exception e)
    {
        error(null, error, e, false);
    }

    public void error(String error, @Nullable Exception e, boolean sendToAnalytics)
    {
        error(null, error, e, sendToAnalytics);
    }

    public void error(@Nullable final String tag, String error, @Nullable Exception e, boolean sendToRemote)
    {
        Log.e(getTag(tag), error + "\n");

        if (e != null)
        {
            e.printStackTrace();
        }

        if (sendToRemote)
        {
            RemoteLogger remoteLogger = getRemoteLogger();

            if (remoteLogger != null)
            {
                remoteLogger.onSendToRemote(tag, error, e);
            }
        }
    }

    /**
     * @return a context to log, generally the app context;
     */
    protected abstract @NonNull Context getLoggerContext();

    /**
     * @return an implementation of RemoteLogger, ie: a wrapper to google analytics;
     */
    protected abstract @Nullable RemoteLogger getRemoteLogger();

    protected String getTag(@Nullable final String proposedTag)
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

    //
    //Inner classes
    //

    public interface RemoteLogger
    {
        void onSendToRemote(@Nullable final String tag, String error, @Nullable Exception e);
    }
}
