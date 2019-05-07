package com.boucinho.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boucinho.R;
import com.boucinho.firebase.FirestoreUtils;
import com.boucinho.models.Event;
import com.boucinho.views.CardEvent;
import com.boucinho.views.TextWithLabel;
import com.boucinho.views.dialogs.EditEventDialog;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                Collections.sort(mEventList, (event1, t1) ->
                        let.equals(ListEventType.UpcomingEvents) ?
                                (int) (event1.getDate() - t1.getDate())
                                : - (int) (event1.getDate() - t1.getDate()));
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
        openDialog(event);
    }

    private void openDialog(Event event){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(event.getTitle());

        View view = getLayoutInflater().inflate(R.layout.dialog_event_details, null);

        builder.setView(view);
        ((TextWithLabel) view.findViewById(R.id.twl_details)).setContent("Details", event.getDetails());
        ((TextWithLabel) view.findViewById(R.id.twl_date)).setContent("Date", event.getFriendlyDate());
        ((TextWithLabel) view.findViewById(R.id.twl_location)).setContent("Lieu", event.getLocation());
        ((TextWithLabel) view.findViewById(R.id.twl_guests)).setContent("Guests", "-");
        ((TextWithLabel) view.findViewById(R.id.twl_duration))
                .setContent("Durée", String.format(Locale.FRANCE, "%d minutes", event.getDuration()));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, (dialog, which) -> dialog.dismiss());
        builder.setNeutralButton(getString(R.string.edit), (dialog, which) -> {
            new EditEventDialog(getContext(), event, new EditEventDialog.EditEventDialogListener() {
                @Override
                public void onEventUpdated(DialogInterface dialog, String eventKey) {
                    Log.e(getClass().getName(), "Event with key: '" + eventKey + "' has been updated.");
                    dialog.dismiss();
                }

                @Override
                public void onEventUpdateFailure(DialogInterface dialog, Exception e) {
                    Toast.makeText(getContext(),
                            "Invalid event\n" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }).show();
            dialog.dismiss();
        });
        builder.setNegativeButton(getString(R.string.delete), (dialogInterface, which) -> {
            FirestoreUtils.getCollection(FirestoreUtils.Collections.Event)
                    .document(event.getID()).delete()
                    .addOnSuccessListener(aVoid ->
                            Log.e(getClass().getName(), "Event with key: '" + event.getID() + "' has been removed.")
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(),
                                    "Error while removing event with key " + event.getID()
                                            + "\n" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show());
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
