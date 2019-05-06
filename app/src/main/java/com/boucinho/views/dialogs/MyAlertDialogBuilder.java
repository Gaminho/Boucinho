package com.boucinho.views.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public abstract class MyAlertDialogBuilder extends AlertDialog.Builder {

    /**
     * Click listeners
     */
    private DialogInterface.OnClickListener mPositiveButtonListener = null;
    private DialogInterface.OnClickListener mNegativeButtonListener = null;
    private DialogInterface.OnClickListener mNeutralButtonListener = null;

    /**
     * Buttons text
     */
    private CharSequence mPositiveButtonText = null;
    private CharSequence mNegativeButtonText = null;
    private CharSequence mNeutralButtonText = null;

    private DialogInterface.OnDismissListener mOnDismissListener = null;

    private Boolean mCancelOnTouchOutside = null;

    public MyAlertDialogBuilder(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MyAlertDialogBuilder setOnDismissListener (DialogInterface.OnDismissListener listener) {
        mOnDismissListener = listener;
        return this;
    }

    @Override
    public MyAlertDialogBuilder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
        mNegativeButtonListener = listener;
        mNegativeButtonText = text;
        return this;
    }

    @Override
    public MyAlertDialogBuilder setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
        mNeutralButtonListener = listener;
        mNeutralButtonText = text;
        return this;
    }

    @Override
    public MyAlertDialogBuilder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
        mPositiveButtonListener = listener;
        mPositiveButtonText = text;
        return this;
    }

    @Override
    public MyAlertDialogBuilder setNegativeButton(int textId, DialogInterface.OnClickListener listener) {
        setNegativeButton(getContext().getString(textId), listener);
        return this;
    }

    @Override
    public MyAlertDialogBuilder setNeutralButton(int textId, DialogInterface.OnClickListener listener) {
        setNeutralButton(getContext().getString(textId), listener);
        return this;
    }

    @Override
    public MyAlertDialogBuilder setPositiveButton(int textId, DialogInterface.OnClickListener listener) {
        setPositiveButton(getContext().getString(textId), listener);
        return this;
    }

    public MyAlertDialogBuilder setCanceledOnTouchOutside (boolean cancelOnTouchOutside) {
        mCancelOnTouchOutside = cancelOnTouchOutside;
        return this;
    }



    @Override
    public AlertDialog create() {
        throw new UnsupportedOperationException("MyAlertDialogBuilder.create(): use show() instead..");
    }

    @Override
    public AlertDialog show() {
        final AlertDialog alertDialog = super.create();
        populateView(getContext());

        DialogInterface.OnClickListener emptyOnClickListener = (dialog, which) -> { };

        // Enable buttons (needed for Android 1.6) - otherwise later getButton() returns null
        if (mPositiveButtonText != null) {
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mPositiveButtonText, emptyOnClickListener);
        }

        if (mNegativeButtonText != null) {
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, mNegativeButtonText, emptyOnClickListener);
        }

        if (mNeutralButtonText != null) {
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, mNeutralButtonText, emptyOnClickListener);
        }

        // Set OnDismissListener if available
        if (mOnDismissListener != null) {
            alertDialog.setOnDismissListener(mOnDismissListener);
        }

        if (mCancelOnTouchOutside != null) {
            alertDialog.setCanceledOnTouchOutside(mCancelOnTouchOutside);
        }

        alertDialog.show();

        // Set the OnClickListener directly on the Button object, avoiding the auto-dismiss feature
        // IMPORTANT: this must be after alert.show(), otherwise the button doesn't exist..
        // If the listeners are null don't do anything so that they will still dismiss the dialog when clicked
        if (mPositiveButtonListener != null) {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v ->
                    mPositiveButtonListener.onClick(alertDialog, AlertDialog.BUTTON_POSITIVE));
        }

        if (mNegativeButtonListener != null) {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v ->
                    mNegativeButtonListener.onClick(alertDialog, AlertDialog.BUTTON_NEGATIVE));
        }

        if (mNeutralButtonListener != null) {
            alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v ->
                    mNeutralButtonListener.onClick(alertDialog, AlertDialog.BUTTON_NEUTRAL));
        }

        alertDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        return alertDialog;
    }

    protected abstract void initView(Context context);
    protected abstract void populateView(Context context);
}
