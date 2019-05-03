package com.boucinho.views.timepickers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TimePicker;

import com.boucinho.R;

import java.util.Calendar;

import androidx.annotation.Nullable;

public class MyHourPicker extends MyAbstractTimePicker implements TimePickerDialog.OnTimeSetListener {

    public MyHourPicker(Context context) {
        super(context);
    }

    public MyHourPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHourPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected String getDateFormat() {
        return "HH'h'mm";
    }

    @Override
    protected String getFieldLabel() {
        return getContext().getString(R.string.hour);
    }

    @Override
    protected String getDefaultValue() {
        return getContext().getString(R.string.no_hour_specified);
    }

    @Override
    protected String getButtonLabel() {
        return getContext().getString(R.string.hour);
    }

    @Override
    protected void pick() {
        new TimePickerDialog(getContext(), this,
                mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE),
                true).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        super.onTimeSet();
    }
}
