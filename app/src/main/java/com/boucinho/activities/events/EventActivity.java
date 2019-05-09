package com.boucinho.activities.events;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.boucinho.R;
import com.boucinho.firebase.FirebaseOperator;
import com.boucinho.firebase.FirestoreUtils;
import com.boucinho.models.Event;
import com.boucinho.utils.FragmentUtils;
import com.boucinho.views.dialogs.SimpleDialog;
import com.google.firebase.firestore.CollectionReference;

public class EventActivity extends AppCompatActivity implements FirebaseOperator,
        AbstractEventFragment.EventFragmentListener  {

    public static final String EXTRA_EVENT = "event";
    private Event mCurrentEvent;
    private boolean isInEditionMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        if(null != getIntent().getSerializableExtra(EXTRA_EVENT)) {
            mCurrentEvent = (Event) getIntent().getSerializableExtra(EXTRA_EVENT);
        } else {
            mCurrentEvent = new Event();
        }

        FragmentUtils.loadFragment(getSupportFragmentManager(),
                EventDetailFragment.newInstance(), R.id.fl_event_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.update_event).setVisible(!isInEditionMode);
        menu.findItem(R.id.remove_event).setVisible(!isInEditionMode);
        menu.findItem(R.id.valid_event).setVisible(isInEditionMode);
        menu.findItem(R.id.cancel_event).setVisible(isInEditionMode);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_event:
                loadFragment(EditEventFragment.newInstance());
                return true;
            case R.id.remove_event:
                openRemoveDialog();
                return true;
            case R.id.cancel_event:
                openConfirmDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public CollectionReference getEventCollection() {
        return FirestoreUtils.getCollection(FirestoreUtils.Collections.Event);
    }

    @Override
    public Event getEvent() {
        return mCurrentEvent;
    }

    @Override
    public void updateEvent(Event event) {
        getEventCollection().document(mCurrentEvent.getID()).set(event)
                .addOnSuccessListener(aVoid -> {
                    event.setID(mCurrentEvent.getID());
                    mCurrentEvent = event;
                    loadFragment(EventDetailFragment.newInstance());
                })
                .addOnFailureListener(e -> {
                    Log.e(getClass().getCanonicalName(), e.getLocalizedMessage(), e);
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onBackPressed() {
        if(isInEditionMode){
            openConfirmDialog();
        } else {
            super.onBackPressed();
        }
    }

    private void openConfirmDialog(){
        new SimpleDialog(this,
                getResources().getString(R.string.warning),
                getResources().getString(R.string.event_cancel_modification))
                .setPositiveButton(android.R.string.ok,
                        (dialogInterface, i) -> {
                            loadFragment(EventDetailFragment.newInstance());
                            dialogInterface.dismiss();
                        })
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) ->
                        dialogInterface.dismiss()).show();
    }

    private void openRemoveDialog(){
        new SimpleDialog(this,
                getResources().getString(R.string.warning),
                getResources().getString(R.string.event_confirm_delete))
                .setPositiveButton(android.R.string.ok,
                        (dialogInterface, i) ->
                                getEventCollection().document(getEvent().getID()).delete()
                                        .addOnSuccessListener(aVoid -> {
                                            onBackPressed();
                                            dialogInterface.dismiss();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            dialogInterface.dismiss();
                                        })
                )
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) ->
                        dialogInterface.dismiss())
                .show();
    }

    private void loadFragment(Fragment fragment){
        isInEditionMode = fragment instanceof EditEventFragment;
        FragmentUtils.loadFragment(getSupportFragmentManager(), fragment, R.id.fl_event_content);
        invalidateOptionsMenu();
    }
}
