package com.tinchoapps.devutils.Dialog;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

public abstract class BaseDialog<LISTENER extends BaseDialog.Listener> extends DialogFragment
{
    private LISTENER listener;

    public void setListener(final LISTENER listener)
    {
        this.listener = listener;
    }

    protected LISTENER getListener()
    {
        return listener;
    }

    @Override
    public void onCancel(final DialogInterface dialog)
    {
        super.onCancel(dialog);

        if (listener != null)
        {
            listener.onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog)
    {
        super.onDismiss(dialog);

        if (listener != null)
        {
            listener.onDismiss(dialog);
        }
    }

    //
    // Listener
    //

    public interface Listener
    {
        void onDismiss(final DialogInterface dialog);

        void onCancel(final DialogInterface dialog);
    }

    public static class SimpleListener implements Listener
    {
        @Override
        public void onDismiss(final DialogInterface dialog)
        {

        }

        @Override
        public void onCancel(final DialogInterface dialog)
        {

        }
    }
}
