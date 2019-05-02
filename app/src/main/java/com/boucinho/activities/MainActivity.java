package com.boucinho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.boucinho.R;
import com.boucinho.views.CardEvent;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private CardEvent mCENextEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCENextEvent = findViewById(R.id.ce_next_event);
        mCENextEvent.setTitle("Ouverture Grizzly");
        mCENextEvent.setDetail("25 minutes de set, pas de beatbox");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        mCENextEvent.setDate(calendar.getTime());
    }
}
