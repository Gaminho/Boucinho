package com.boucinho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.boucinho.R;
import com.boucinho.views.CardEvent;
import com.boucinho.views.CardEventWithPicture;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private CardEvent mCENextEvent;
    private CardEventWithPicture mCENextEventWithPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCENextEvent = findViewById(R.id.ce_next_event);
        mCENextEvent.setTitle("Ouverture Grizzly");
        mCENextEvent.setDetail("25 minutes de set, pas de beatbox, " +
                "25 minutes ");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        mCENextEvent.setDate(calendar.getTime());

        mCENextEventWithPicture = findViewById(R.id.ce_next_event_picture);
        mCENextEventWithPicture.setTitle("Fermeture Grizzly");
        mCENextEventWithPicture.setDetail("25 minutes de set, pas de beatbox.");
        mCENextEventWithPicture.setImageDrawable(R.mipmap.ic_launcher);
    }
}
