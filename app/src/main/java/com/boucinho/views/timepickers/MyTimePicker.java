package com.boucinho.views.timepickers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;

public class MyTimePicker extends LinearLayout {

    private MyHourPicker mMyHourPicker;
    private MyDatePicker mMyDatePicker;

    public MyTimePicker(Context context) {
        super(context);
        init(context);
    }

    public MyTimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTimePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        mMyDatePicker = new MyDatePicker(context);
        mMyHourPicker = new MyHourPicker(context);

        addView(mMyDatePicker);
        addView(mMyHourPicker);
    }

    public Date getTime(){
        if(null != mMyHourPicker.getDate() && null != mMyDatePicker){
            Calendar cHour = Calendar.getInstance();
            cHour.setTime(mMyHourPicker.getDate());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mMyDatePicker.getDate());
            calendar.set(Calendar.HOUR_OF_DAY, cHour.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, cHour.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        } else {
            return null;
        }
    }

    public long getTimeInMillis(){
        return null != getTime() ? getTime().getTime() : -1;
    }

    public void setDate(Date date){
        mMyHourPicker.setDate(date);
        mMyDatePicker.setDate(date);
    }

    public void setDate(long date){
        Date pDate = new Date(date);
        mMyHourPicker.setDate(pDate);
        mMyDatePicker.setDate(pDate);
    }
}
