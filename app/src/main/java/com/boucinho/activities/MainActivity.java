package com.boucinho.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.boucinho.R;
import com.boucinho.models.Event;
import com.boucinho.views.CardEvent;
import com.boucinho.views.CardEventWithPicture;
import com.boucinho.views.MyAlertDialogBuilder;
import com.boucinho.views.TextWithLabel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements CardEvent.ClickOnEventListener {

    private Calendar mCalendar;
    private CardEvent mCENextEvent;
    private CardEventWithPicture mCENextEventWithPicture;

    private static final Event mEvent = new Event("Ouverture Grizzly",
            "25 minutes de set, pas de beatbox",
            new Date(),
            "Grizzly Pub");
    private static final Event mEvent2 = new Event("Hydrophobie",
            "Set de 30 minutes sans Dj Musashi",
            new Date(),
            "Hydrophobie club");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalendar = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        mEvent.setDate(calendar.getTime());
        mCENextEvent = findViewById(R.id.ce_next_event);
        mCENextEvent.setEvent(mEvent);
        mCENextEvent.setClickOnEventListener(this);

        calendar.set(Calendar.DAY_OF_MONTH, 10);
        mEvent2.setDate(calendar.getTime());
        mCENextEventWithPicture = findViewById(R.id.ce_next_event_picture);
        mCENextEventWithPicture.setImageDrawable(R.mipmap.ic_launcher);
        mCENextEventWithPicture.setEvent(mEvent2);
        mCENextEventWithPicture.setClickOnEventListener(this);
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

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addDialog(){
        // Create the CustomAlertDialogBuilder
        MyAlertDialogBuilder dialogBuilder = new MyAlertDialogBuilder(this);
        dialogBuilder.setTitle("Ajouter un evenement");


        View view = getLayoutInflater().inflate(R.layout.dialog_add_event, null);
        dialogBuilder.setView(view);

        TextInputEditText etTitle = view.findViewById(R.id.add_event_title);
        TextInputEditText etDetail = view.findViewById(R.id.add_event_detail);
        TextInputEditText etLocation = view.findViewById(R.id.add_event_location);

        TextWithLabel twlDate = view.findViewById(R.id.add_event_date);
        twlDate.setContent(getString(R.string.date), getString(R.string.no_date_specified));

        TextWithLabel twlHour = view.findViewById(R.id.add_event_hour);
        twlHour.setContent(getString(R.string.hour), getString(R.string.no_hour_specified));

        view.findViewById(R.id.add_event_pick_date).setOnClickListener( l->
                new DatePickerDialog(this,
                        (datePicker, year, month, day) -> {
                            mCalendar.set(Calendar.YEAR, year);
                            mCalendar.set(Calendar.MONTH, month);
                            mCalendar.set(Calendar.DAY_OF_MONTH, day);
                            twlDate.setValue(new SimpleDateFormat("yyyy/MM/dd, E", Locale.FRANCE)
                                    .format(mCalendar.getTime()));
                        },
                        mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)
                ).show());

        view.findViewById(R.id.add_event_pick_hour).setOnClickListener( l->
                new TimePickerDialog(this,
                        (timePicker, hour, minute) -> {
                            mCalendar.set(Calendar.HOUR_OF_DAY, hour);
                            mCalendar.set(Calendar.MINUTE, minute);
                            twlHour.setValue(new SimpleDateFormat("HH'h'mm", Locale.FRANCE)
                                    .format(mCalendar.getTime()));
                        },
                        mCalendar.get(Calendar.HOUR_OF_DAY),
                        mCalendar.get(Calendar.MINUTE),
                        true
                ).show());

        dialogBuilder.setPositiveButton(getString(android.R.string.ok), (dialog, which) -> {
            Event event = new Event(
                    etTitle.getText().toString(), etDetail.getText().toString(),
                    mCalendar.getTime(), etLocation.getText().toString());
            if(validEvent(event)){
                addEvent(event);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Invalid event", Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setNegativeButton (getString(android.R.string.cancel), null);
        dialogBuilder.setCanceledOnTouchOutside(false);
        dialogBuilder.show();
    }

    private boolean validEvent(Event event){
        if(TextUtils.isEmpty(event.getTitle())){
            return false;
        } else if(TextUtils.isEmpty(event.getDetails())){
            return false;
        } else if(TextUtils.isEmpty(event.getLocation())){
            return false;
        } else {
            return true;
        }
    }

    private void addEvent(Event event){
        CardEvent cardEvent = new CardEvent(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(16, 8, 16, 8);
        cardEvent.setEvent(event);
        cardEvent.setClickOnEventListener(this);
        addContentView(cardEvent, lp);
    }

    @Override
    public void clickOnEvent(Event event) {
        openDialog(event);
    }
}
