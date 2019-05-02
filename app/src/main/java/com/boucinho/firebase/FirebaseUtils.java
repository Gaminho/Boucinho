package com.boucinho.firebase;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    public enum Reference {
        Event, Project
    }

    public static void initDatabase(Context context){
        FirebaseApp.initializeApp(context);
    }

    private static FirebaseDatabase getDatabase(){
        return FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getTableReference(Reference reference) {
        DatabaseReference ref;
        switch (reference){
            case Event:
                ref =  getDatabase().getReference("event");
                break;
            case Project:
                ref = getDatabase().getReference();
                break;
            default:
                ref = getDatabase().getReference();
                break;
        }
        return ref;
    }
}
