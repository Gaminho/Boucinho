package com.boucinho.views.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.boucinho.R;
import com.boucinho.firebase.FirestoreUtils;
import com.boucinho.models.Event;
import com.boucinho.views.timepickers.MyTimePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;

import androidx.annotation.NonNull;

public class EditEventDialog extends MyAlertDialogBuilder {

    private EditEventDialogListener mListener;
    private TextInputEditText mTIETTitle, mTIETDetails, mTIETLocation;
    private MyTimePicker mMTPDate;
    private final Event mEvent;

    public EditEventDialog(@NonNull Context context, Event event, @NonNull EditEventDialogListener listener) {
        super(context);
        mListener = listener;
        mEvent = event;
    }

    @Override
    protected void initView(Context context) {
        setTitle(context.getString(R.string.edit_an_event));

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.form_add_event, null);
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
                a.document(mEvent.getID()).set(event)
                        .addOnSuccessListener(aVoid -> mListener.onEventUpdated(dialog, mEvent.getID()))
                        .addOnFailureListener(e -> mListener.onEventUpdateFailure(dialog, e));
            } catch (Event.EventException e) {
                mListener.onEventUpdateFailure(dialog, e);
            }
        });

        setNegativeButton(context.getString(android.R.string.cancel), null);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void populateView(Context context) {
        mTIETTitle.setText(mEvent.getTitle());
        mTIETDetails.setText(mEvent.getDetails());
        mTIETLocation.setText(mEvent.getLocation());
        mMTPDate.setDate(mEvent.getDate());
    }

    public interface EditEventDialogListener {
        void onEventUpdated(DialogInterface dialog, String eventKey);
        void onEventUpdateFailure(DialogInterface dialog, Exception e);
    }

}