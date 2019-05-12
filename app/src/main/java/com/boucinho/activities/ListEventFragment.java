package com.boucinho.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boucinho.R;
import com.boucinho.activities.events.EventActivity;
import com.boucinho.firebase.FirestoreUtils;
import com.boucinho.models.Event;
import com.boucinho.views.CardEvent;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ListEventFragment extends Fragment implements CardEvent.ClickOnEventListener {

    private static final String ARG_LIST_EVENT_TYPE = "list-event-type";

    public enum ListEventType {
        UpcomingEvents, AllEvents
    }

    private Event.EventAdapter mEventAdapter;
    private List<Event> mEventList = new ArrayList<>();


    public static ListEventFragment newInstance(ListEventType listEventType){
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST_EVENT_TYPE, listEventType);
        ListEventFragment listEventFragment = new ListEventFragment();
        listEventFragment.setArguments(args);
        switch (listEventType){
            case AllEvents:
                break;
            case UpcomingEvents:
                break;
        }
        return listEventFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventAdapter = new Event.EventAdapter(mEventList);
        mEventAdapter.setCardEventListener(this);

        ListEventType let = (ListEventType) getArguments().getSerializable(ARG_LIST_EVENT_TYPE);

        Query query = let.equals(ListEventType.UpcomingEvents) ?
                FirestoreUtils.getCollection(FirestoreUtils.Collections.Event)
                        .whereGreaterThan("date", new Date().getTime())
                : FirestoreUtils.getCollection(FirestoreUtils.Collections.Event);

        query.addSnapshotListener((queryDocumentSnapshots, exception) -> {
            if (exception != null) {
                Log.e(getClass().getName(), "listen:error", exception);
                return;
            }

            for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                Event event = dc.getDocument().toObject(Event.class);
                String id = dc.getDocument().getId();
                event.setID(id);
                switch(dc.getType()){
                    case ADDED:
                        event.setID(id);
                        mEventList.add(event);
                        break;
                    case REMOVED:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            mEventList.removeIf(ev -> ev.getID().equals(id));
                        } else {
                            for (Event ev : mEventList){
                                if(ev.getID().equals(id)){
                                    mEventList.remove(ev);
                                    break;
                                }
                            }
                        }
                        break;
                    case MODIFIED:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Event old = mEventList.stream().filter(ev -> ev.getID().equals(id)).findFirst().get();
                            old.clone(event);
                        } else {
                            for (Event ev : mEventList){
                                if(ev.getID().equals(id)){
                                    ev.clone(event);
                                    break;
                                }
                            }
                        }
                        break;
                }


                Collections.sort(mEventList, Comparator.comparing(Event::getDate));

                if(let.equals(ListEventType.AllEvents)){
                    Collections.reverse(mEventList);
                }

                mEventAdapter.notifyDataSetChanged();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        RecyclerView rv = view.findViewById(R.id.rv_event_list);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(mEventAdapter);

        return view;
    }

    @Override
    public void clickOnEvent(Event event) {
        Intent intent = new Intent(getActivity(), EventActivity.class);
        intent.putExtra(EventActivity.EXTRA_EVENT, event);
        getActivity().startActivity(intent);
    }

}
