package com.boucinho.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.boucinho.R;
import com.boucinho.utils.FragmentUtils;
import com.boucinho.views.dialogs.AddingEventDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView btn = findViewById(R.id.bnv_event_menu);
        btn.setOnNavigationItemSelectedListener(this);

        FragmentUtils.loadFragment(getSupportFragmentManager(),
                ListEventFragment.newInstance(ListEventFragment.ListEventType.UpcomingEvents),
                R.id.fl_main_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_event:
//                    new DialogTest(this).show();
                addDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch(menuItem.getItemId()){
            case R.id.upcoming_events:
                fragment = ListEventFragment.newInstance(ListEventFragment.ListEventType.UpcomingEvents);
                break;
            case R.id.all_events:
                fragment = ListEventFragment.newInstance(ListEventFragment.ListEventType.AllEvents);
                break;
        }

        FragmentUtils.loadFragment(getSupportFragmentManager(), fragment, R.id.fl_main_content);
        menuItem.setChecked(true);

        return false;
    }
}