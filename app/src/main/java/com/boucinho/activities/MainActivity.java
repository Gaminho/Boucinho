package com.boucinho.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.boucinho.R;
import com.boucinho.firebase.FirestoreUtils;
import com.boucinho.models.Event;
import com.boucinho.views.CardEvent;
import com.boucinho.views.TextWithLabel;
import com.boucinho.views.dialogs.AddingEventDialog;
import com.boucinho.views.dialogs.EditEventDialog;
import com.google.firebase.firestore.DocumentChange;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements CardEvent.ClickOnEventListener {

    private Event.EventAdapter mEventAdapter;

    private List<Event> mEventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.rv_event_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mEventAdapter = new Event.EventAdapter(mEventList);
        mEventAdapter.setCardEventListener(this);
        rv.setAdapter(mEventAdapter);

        FirestoreUtils.getCollection(FirestoreUtils.Collections.Event)
                .addSnapshotListener((queryDocumentSnapshots, exception) -> {
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
                        mEventAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_event:
                addDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void openDialog(Event event){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(event.getTitle());

        View view = getLayoutInflater().inflate(R.layout.dialog_event_details, null);

        builder.setView(view);
        ((TextWithLabel) view.findViewById(R.id.twl_details)).setContent("Details", event.getDetails());
        ((TextWithLabel) view.findViewById(R.id.twl_date)).setContent("Date", event.getFriendlyDate());
        ((TextWithLabel) view.findViewById(R.id.twl_location)).setContent("Lieu", event.getLocation());
        ((TextWithLabel) view.findViewById(R.id.twl_guests)).setContent("Guests", "-");

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, (dialog, which) -> dialog.dismiss());
        builder.setNeutralButton(getString(R.string.edit), (dialog, which) -> {
            new EditEventDialog(this, event, new EditEventDialog.EditEventDialogListener() {
                @Override
                public void onEventUpdated(DialogInterface dialog, String eventKey) {
                    Log.e(getClass().getName(), "Event with key: '" + eventKey + "' has been updated.");
                    dialog.dismiss();
                }

                @Override
                public void onEventUpdateFailure(DialogInterface dialog, Exception e) {
                    Toast.makeText(getApplicationContext(),
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
                            Toast.makeText(getApplicationContext(),
                                    "Error while removing event with key " + event.getID()
                                            + "\n" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show());
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addDialog(){
        new AddingEventDialog(this, new AddingEventDialog.AddingEventDialogListener() {
            @Override
            public void onEventAdded(DialogInterface dialog, String eventKey) {
                Log.e(getClass().getName(), "Event with key: '" + eventKey + "' has been added.");
                dialog.dismiss();
            }

            @Override
            public void onEventAddingFailure(DialogInterface dialog, Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Invalid event\n" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    @Override
    public void clickOnEvent(Event event) {
        openDialog(event);
    }
}
// 216