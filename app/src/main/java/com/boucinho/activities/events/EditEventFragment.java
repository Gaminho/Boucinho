package com.boucinho.activities.events;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.boucinho.R;
import com.boucinho.models.Event;
import com.boucinho.views.FormEvent;

public class EditEventFragment extends AbstractEventFragment {

    private FormEvent mFormEvent;

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
        mFormEvent = view.findViewById(R.id.add_form_event);
        mFormEvent.fillWithEvent(event);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.event_add_fragment;
    }

    private Event getUpdatedEvent() throws Event.EventException {
        Event event = mFormEvent.getEvent();
        Event.verify(event);
        return event;
    }

}
