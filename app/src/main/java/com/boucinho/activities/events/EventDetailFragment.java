package com.boucinho.activities.events;

import android.view.View;
import android.widget.TextView;

import com.boucinho.R;
import com.boucinho.models.Event;
import com.gaminho.myandroidcomponents.TextWithLabel;

import java.util.Locale;

public class EventDetailFragment extends AbstractEventFragment {

    public static EventDetailFragment newInstance(){
        return new EventDetailFragment();
    }

    @Override
    protected void initView(View view) {
        Event event = mListener.getEvent();
        ((TextView) view.findViewById(R.id.tv_title)).setText(event.getTitle());
        ((TextWithLabel) view.findViewById(R.id.twl_details)).setValue(event.getDetails());
        ((TextWithLabel) view.findViewById(R.id.twl_date)).setValue(event.getFriendlyDate());
        ((TextWithLabel) view.findViewById(R.id.twl_location)).setValue(event.getLocation());
        ((TextWithLabel) view.findViewById(R.id.twl_guests)).setValue("-");
        ((TextWithLabel) view.findViewById(R.id.twl_duration))
                .setValue(String.format(Locale.FRANCE, "%d minutes", event.getDuration()));

        view.findViewById(R.id.ll_separator).setBackgroundColor(event.getEventColor());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.event_detail_fragment;
    }
}
