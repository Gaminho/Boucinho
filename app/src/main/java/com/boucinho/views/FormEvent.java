package com.boucinho.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.boucinho.R;
import com.boucinho.models.Event;
import com.boucinho.views.timepickers.MyTimePicker;
import com.google.android.material.textfield.TextInputEditText;

public class FormEvent extends LinearLayout {

    private TextInputEditText mTIETTitle, mTIETDetails, mTIETLocation;
    private MyTimePicker mMTPDate;

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
        inflate(context, R.layout.form_add_event, this);

        mTIETTitle = findViewById(R.id.add_event_title);
        mTIETDetails = findViewById(R.id.add_event_detail);
        mTIETLocation = findViewById(R.id.add_event_location);
        mMTPDate = findViewById(R.id.mtp_date);
    }

    public Event getEvent(){
        return new Event(mTIETTitle.getText().toString(), mTIETDetails.getText().toString(),
                mMTPDate.getTimeInMillis(), mTIETLocation.getText().toString());
    }
}
