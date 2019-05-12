package com.boucinho.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

import com.boucinho.R;
import com.boucinho.models.EventType;

import java.util.Arrays;
import java.util.List;

public class EventTypeSpinner extends Spinner {

    public EventTypeSpinner(Context context) {
        super(context);
    }

    public EventTypeSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // FIXME
    public EventType getEventType(){
        String selectedType = String.valueOf(getSelectedItem());
        if (getContext().getString(R.string.concert).equals(selectedType)){
            return EventType.Concert;
        } else if (getContext().getString(R.string.repetition).equals(selectedType)) {
            return EventType.Repetition;
        } else if (getContext().getString(R.string.studio).equals(selectedType)) {
            return EventType.Studio;
        } else if (getContext().getString(R.string.radio).equals(selectedType)) {
            return EventType.Radio;
        } else if (getContext().getString(R.string.open_mic).equals(selectedType)) {
            return EventType.OpenMic;
        } else if (getContext().getString(R.string.meeting).equals(selectedType)) {
            return EventType.Reunion;
        } else if (getContext().getString(R.string.work).equals(selectedType)) {
            return EventType.Atelier;
        } else {
            return EventType.Other;
        }
    }

    // FIXME
    public void setEventType(EventType eventType){
        List<String> items =
                Arrays.asList(getContext().getResources().getStringArray(R.array.event_types));
        int selection = 0;
        switch (eventType){
            case Concert:
                selection = items.indexOf(getContext().getString(R.string.concert));
                break;
            case Repetition:
                selection = items.indexOf(getContext().getString(R.string.repetition));
                break;
            case Studio:
                selection = items.indexOf(getContext().getString(R.string.studio));
                break;
            case Radio:
                selection = items.indexOf(getContext().getString(R.string.radio));
                break;
            case OpenMic:
                selection = items.indexOf(getContext().getString(R.string.open_mic));
                break;
            case Reunion:
                selection = items.indexOf(getContext().getString(R.string.meeting));
                break;
            case Atelier:
                selection = items.indexOf(getContext().getString(R.string.work));
                break;
        }
        setSelection(selection);
    }
}
