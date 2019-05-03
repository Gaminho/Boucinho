package com.boucinho.views.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.boucinho.R;
import com.boucinho.firebase.FirestoreUtils;
import com.boucinho.models.Event;
import com.boucinho.views.timepickers.MyTimePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;

import androidx.annotation.NonNull;

public class AddingEventDialog extends MyAlertDialogBuilder {

    private AddingEventDialogListener mListener;
    private TextInputEditText mTIETTitle, mTIETDetails, mTIETLocation;
    private MyTimePicker mMTPDate;

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
        initView(context);
    }

    private void initView(Context context) {
        setTitle(context.getString(R.string.add_an_event));

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_add_event, null);
        setView(view);

        mTIETTitle = view.findViewById(R.id.add_event_title);
        mTIETDetails = view.findViewById(R.id.add_event_detail);
        mTIETLocation = view.findViewById(R.id.add_event_location);
        mMTPDate = view.findViewById(R.id.mtp_date);

        setPositiveButton(context.getString(android.R.string.ok), (dialog, which) -> {

            Event event = new Event(
                    mTIETTitle.getText().toString(), mTIETDetails.getText().toString(),
                    mMTPDate.getTimeInMillis(), mTIETLocation.getText().toString());
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

    public interface AddingEventDialogListener {
        void onEventAdded(DialogInterface dialog, String eventKey);
        void onEventAddingFailure(DialogInterface dialog, Exception e);
    }

}
