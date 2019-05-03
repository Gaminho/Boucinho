package com.boucinho.views.timepickers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.boucinho.R;
import com.boucinho.views.TextWithLabel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;

public abstract class MyAbstractTimePicker extends LinearLayout {

    private TextWithLabel mTextWithLabel;
    private SimpleDateFormat mSDF = new SimpleDateFormat(getDateFormat(), Locale.FRANCE);

    protected Calendar mCalendar;

    public MyAbstractTimePicker(Context context) {
        super(context);
        init(context);
    }

    public MyAbstractTimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyAbstractTimePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mCalendar = Calendar.getInstance();
        setOrientation(HORIZONTAL);
        setWeightSum(3);

        mTextWithLabel = new TextWithLabel(context);
        mTextWithLabel.setContent(getFieldLabel(), getDefaultValue());
        addView(mTextWithLabel, new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2));

        MaterialButton materialButton = new MaterialButton(context, null, R.attr.borderlessButtonStyle);
        materialButton.setText(getButtonLabel());
        materialButton.setOnClickListener(l -> pick());
        addView(materialButton, new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
    }

    void onTimeSet(){
        mTextWithLabel.setValue(mSDF.format(mCalendar.getTime()));
    }
    protected abstract void pick();
    protected abstract String getFieldLabel();
    protected abstract String getDateFormat();
    protected abstract String getDefaultValue();
    protected abstract String getButtonLabel();

    public Date getDate(){
        return getDefaultValue().equals(mTextWithLabel.getValue()) ?
                null : mCalendar.getTime();
    }

    public void setDate(Date date){
        mCalendar.setTime(date);
        onTimeSet();
    }
}
