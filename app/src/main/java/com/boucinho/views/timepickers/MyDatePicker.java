package com.boucinho.views.timepickers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;

import com.boucinho.R;

import java.util.Calendar;

import androidx.annotation.Nullable;

public class MyDatePicker extends MyAbstractTimePicker implements DatePickerDialog.OnDateSetListener {

    public MyDatePicker(Context context) {
        super(context);
    }

    public MyDatePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDatePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected String getDateFormat() {
        return "yyyy/MM/dd, E";
    }

    @Override
    protected String getFieldLabel() {
        return getContext().getString(R.string.date);
    }

    @Override
    protected String getDefaultValue() {
        return getContext().getString(R.string.no_date_specified);
    }

    @Override
    protected String getButtonLabel() {
        return getContext().getString(R.string.date);
    }

    @Override
    protected void pick() {
        new DatePickerDialog(getContext(), this, mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        super.onTimeSet();
    }
}
