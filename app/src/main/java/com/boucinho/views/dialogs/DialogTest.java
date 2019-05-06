package com.boucinho.views.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.boucinho.R;
import com.boucinho.models.Event;
import com.boucinho.views.FormEvent;

public class DialogTest extends MyAlertDialogBuilder {

    private FrameLayout mFLContent;
    private FormEvent mFormEvent;
    private Event.EventType mCurrentEventType;

    public DialogTest(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_1, null);
        setView(view);

        mFLContent = view.findViewById(R.id.fl_dialog_add_event_content);

        Spinner sp = view.findViewById(R.id.spinner_event_type);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long duration) {
                String currentSelection = String.valueOf(adapterView.getItemAtPosition(position));
                Event.EventType eventType = null;
                if(currentSelection.equals(getContext().getString(R.string.concert))){
                    eventType = Event.EventType.Concert;
                } else if(currentSelection.equals(getContext().getString(R.string.repetition))){
                    eventType = Event.EventType.Repetition;
                } else if(currentSelection.equals(getContext().getString(R.string.studio))){
                    eventType = Event.EventType.Studio;
                } else {
                    eventType = Event.EventType.Other;
                }
                loadForm(eventType);
                mCurrentEventType = eventType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        setPositiveButton("Test", (dialogInterface, i) -> {
            Toast.makeText(getContext(), mFormEvent.getEvent().toString(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void populateView(Context context) {

    }

    private void loadForm(Event.EventType eventType){
        View view;
        switch (eventType){
            case Concert:
//                view = LayoutInflater.from(getContext()).inflate(R.layout.form_add_event, null);
                mFormEvent = new FormEvent(getContext());
                view = mFormEvent;
                break;
            default:
                view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_event_details, null);
                break;
        }

        mFLContent.removeAllViews();
        mFLContent.addView(view);
    }
}
