package com.boucinho.activities.events;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.boucinho.R;
import com.boucinho.models.Event;
import com.boucinho.models.EventType;
import com.boucinho.views.timepickers.DurationPicker;
import com.boucinho.views.timepickers.MyTimePicker;
import com.google.android.material.textfield.TextInputEditText;

public class EditEventFragment extends AbstractEventFragment {

    private TextInputEditText mTIETTitle, mTIETDetails, mTIETLocation;
    private MyTimePicker mMTPDate;
    private Spinner mSpinnerEventType;
    private DurationPicker mDPDuration;

    public static EditEventFragment newInstance(){
        return new EditEventFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.valid_event:
                try {
                    mListener.updateEvent(getUpdatedEvent());
                } catch (Event.EventException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            default:
                Toast.makeText(getContext(), "From children", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView(View view) {
        Event event = mListener.getEvent();

        mTIETTitle = view.findViewById(R.id.add_event_title);
        mTIETDetails = view.findViewById(R.id.add_event_detail);
        mTIETLocation = view.findViewById(R.id.add_event_location);
        mMTPDate = view.findViewById(R.id.mtp_date);
        mSpinnerEventType = view.findViewById(R.id.spinner_event_type);
        mDPDuration = view.findViewById(R.id.dp_event_duration);

        mTIETTitle.setText(event.getTitle());
        mTIETDetails.setText(event.getDetails());
        mTIETLocation.setText(event.getLocation());
        mMTPDate.setDate(event.getDate());
        mDPDuration.setDuration(event.getDuration());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.event_add_fragment;
    }

    private Event getUpdatedEvent() throws Event.EventException {

        String selectedType = String.valueOf(mSpinnerEventType.getSelectedItem());
        EventType eventType;
        if (getContext().getString(R.string.concert).equals(selectedType)){
            eventType = EventType.Concert;
        } else if (getContext().getString(R.string.repetition).equals(selectedType)) {
            eventType = EventType.Repetition;
        } else if (getContext().getString(R.string.studio).equals(selectedType)) {
            eventType = EventType.Studio;
        } else if (getContext().getString(R.string.radio).equals(selectedType)) {
            eventType = EventType.Radio;
        } else if (getContext().getString(R.string.open_mic).equals(selectedType)) {
            eventType = EventType.OpenMic;
        } else if (getContext().getString(R.string.meeting).equals(selectedType)) {
            eventType = EventType.Reunion;
        } else if (getContext().getString(R.string.work).equals(selectedType)) {
            eventType = EventType.Atelier;
        } else {
            eventType = EventType.Other;
        }

        Event event = new Event(
                mTIETTitle.getText().toString(), mTIETDetails.getText().toString(),
                mDPDuration.getDuration(), eventType, mMTPDate.getTimeInMillis(),
                mTIETLocation.getText().toString());

        Event.verify(event);
        return event;
    }
}
