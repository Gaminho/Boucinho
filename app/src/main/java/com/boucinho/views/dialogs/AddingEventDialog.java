package com.boucinho.views.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.boucinho.R;
import com.boucinho.firebase.FirestoreUtils;
import com.boucinho.models.Event;
import com.boucinho.views.FormEvent;
import com.google.firebase.firestore.CollectionReference;

public class AddingEventDialog extends MyCustomViewDialogBuilder {

    private AddingEventDialogListener mListener;
    private FormEvent mFormEvent;

    public AddingEventDialog(@NonNull Context context, AddingEventDialogListener listener) {
        super(context);
        mListener = listener != null ?
                listener : new AddingEventDialogListener() {
            @Override
            public void onEventAdded(DialogInterface dialog, String eventKey) {
                Log.e(getClass().getName(), "No action triggered for event adding successes");
            }

            @Override
            public void onEventAddingFailure(DialogInterface dialog, Exception e) {
                Log.e(getClass().getName(), "No action triggered for event adding failures");
            }
        };
    }

    @Override
    protected void initView(Context context) {
        setTitle(context.getString(R.string.add_an_event));

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_add_fragment, null, false);
        setView(view);

        mFormEvent = view.findViewById(R.id.add_form_event);

        setPositiveButton(context.getString(android.R.string.ok), (dialog, which) -> {

            Event event = mFormEvent.getEvent();

            try {
                Event.verify(event);

                CollectionReference a = FirestoreUtils.getCollection(FirestoreUtils.Collections.Event);
                a.add(event).addOnSuccessListener(documentReference ->
                        mListener.onEventAdded(dialog, documentReference.getId()))
                        .addOnFailureListener(e -> mListener.onEventAddingFailure(dialog, e));
            } catch (Event.EventException e) {
                mListener.onEventAddingFailure(dialog, e);
            }
        });

        setNegativeButton(context.getString(android.R.string.cancel), null);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void populateView(Context context) {

    }

    public interface AddingEventDialogListener {
        void onEventAdded(DialogInterface dialog, String eventKey);
        void onEventAddingFailure(DialogInterface dialog, Exception e);
    }

}
