package com.boucinho.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.boucinho.R;
import com.boucinho.models.Event;
import com.boucinho.models.EventType;
import com.gaminho.myandroidcomponents.timepickers.DurationPicker;
import com.gaminho.myandroidcomponents.timepickers.MyTimePicker;
import com.google.android.material.textfield.TextInputEditText;

public class FormEvent extends LinearLayout {

    private TextInputEditText mTIETTitle, mTIETDetails, mTIETLocation;
    private DurationPicker mDPDuration;
    private MyTimePicker mMTPDate;
    private EventTypeSpinner mETSEventType;

    public FormEvent(Context context) {
        super(context);
        init(context);
    }

    public FormEvent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FormEvent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.form_event, this);
        mTIETTitle = findViewById(R.id.add_event_title);
        mTIETDetails = findViewById(R.id.add_event_detail);
        mTIETLocation = findViewById(R.id.add_event_location);
        mDPDuration = findViewById(R.id.dp_event_duration);
        mMTPDate = findViewById(R.id.mtp_date);
        mETSEventType = findViewById(R.id.spinner_event_type);
    }

    public Event getEvent(){
        String title = TextUtils.isEmpty(mTIETTitle.getText()) ? "" : mTIETTitle.getText().toString();
        String details = TextUtils.isEmpty(mTIETDetails.getText()) ? "" : mTIETDetails.getText().toString();
        int duration = mDPDuration.getDuration();
        EventType eventType = mETSEventType.getEventType();
        long date = mMTPDate.getTimeInMillis();
        String location = TextUtils.isEmpty(mTIETLocation.getText()) ? "" : mTIETLocation.getText().toString();
        return new Event(title, details, duration, eventType, date, location);
    }

    public void fillWithEvent(Event event){
        String details = TextUtils.isEmpty(event.getDetails()) ? "" : event.getDetails();
        mTIETTitle.setText(event.getTitle());
        mTIETDetails.setText(details);
        mTIETLocation.setText(event.getLocation());
        mETSEventType.setEventType(event.getType());
        mDPDuration.setDuration(event.getDuration());
        mMTPDate.setDate(event.getDate());
    }
}
