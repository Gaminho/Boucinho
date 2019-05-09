package com.boucinho.views.dialogs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public abstract class MyCustomViewDialogBuilder extends MyAlertDialogBuilder {

    public MyCustomViewDialogBuilder(@NonNull Context context) {
        super(context);
        initView(context);
    }

    @Override
    public AlertDialog show() {
        AlertDialog dialog = super.show();
        populateView(getContext());
        return dialog;
    }

    protected abstract void initView(Context context);
    protected abstract void populateView(Context context);
}
