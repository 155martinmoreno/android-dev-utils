package com.tinchoapps.devutils.Logging;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class Logger
{
    private static Logger instance;
    private boolean isDebugBuild = false;

    public static Logger get()
    {
        if (instance == null)
        {
            instance = new Logger();
        }

        return instance;
    }


    private RemoteLogger remoteLogger;

    private Logger()
    {
    }

    public void isDebugBuild(boolean isDebugBuild)
    {
        this.isDebugBuild = isDebugBuild;
    }

    public void debug(@NonNull final String message)
    {
        debug(null, message, false, null);
    }

    public void debug(@Nullable final String tag, @NonNull final String message)
    {
        debug(tag, message, false, null);
    }

    public void debug(@Nullable final String tag, @NonNull final String message, final boolean displayToast, @Nullable final Context context)
    {
        if (isDebugBuild)
        {
            Log.d(getTag(tag), message);

            if (displayToast && context != null)
            {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void info(String info)
    {
        info(null, info, false);
    }

    public void info(String info, boolean sendToRemote)
    {
        info(null, info, sendToRemote);
    }

    public void info(@Nullable final String tag, @Nullable final String info, final boolean sendToRemote)
    {
        Log.i(getTag(tag), info);

        if (sendToRemote && remoteLogger != null)
        {
            remoteLogger.onSendInfoToRemote(tag, info);
        }
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

        if (sendToRemote && remoteLogger != null)
        {
            remoteLogger.onSendErrorToRemote(tag, error, e);
        }
    }


    protected void setRemoteLogger(@NonNull final RemoteLogger remoteLogger)
    {
        this.remoteLogger = remoteLogger;
    }

    protected String getTag(@Nullable final String proposedTag)
    {
        String result;
        if (proposedTag == null)
        {
            result = getDefaultTag();
        } else
        {
            result = proposedTag;
        }

        return result;
    }

    /**
     * From http://stackoverflow.com/a/11306854 and modified.
     * Gets the caller class name via reflection;
     *
     * @return caller class simple name;
     */
    @Nullable
    protected String getDefaultTag()
    {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i = 1; i < stElements.length; i++)
        {
            StackTraceElement ste = stElements[i];
            String classFullName = ste.getClassName();

            if (!classFullName.equals(Logger.class.getName()) && classFullName.indexOf("java.lang.Thread") != 0)
            {
                if (callerClassName == null)
                {
                    callerClassName = classFullName;
                } else if (!callerClassName.equals(classFullName))
                {
                    String tag = "";

                    int lastIndexOf = classFullName.lastIndexOf(".");

                    if (lastIndexOf > -1 && lastIndexOf + 1 <= classFullName.length())
                    {
                        tag = classFullName.substring(lastIndexOf + 1);
                    }

                    return tag;
                }
            }
        }
        return null;
    }

    //
    //Inner classes
    //

    public interface RemoteLogger
    {
        void onSendErrorToRemote(@Nullable final String tag, String error, @Nullable Exception e);

        void onSendInfoToRemote(@Nullable final String tag, @NonNull String info);
    }
}
