package com.boucinho.views.dialogs;

import android.content.Context;

import androidx.annotation.NonNull;

import com.gaminho.myandroidcomponents.dialogs.MyAlertDialogBuilder;

public class SimpleDialog extends MyAlertDialogBuilder {

    public SimpleDialog(@NonNull Context context, String title, String message) {
        super(context);
        setTitle(title);
        setMessage(message);
    }
}
