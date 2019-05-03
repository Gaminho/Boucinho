package com.boucinho.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreUtils {

    public enum Collections {
        Event, Project
    }

    public static FirebaseFirestore initFirestore() {
        return FirebaseFirestore.getInstance();
    }

    public static CollectionReference getCollection(FirestoreUtils.Collections collection) {
        FirebaseFirestore mInstance = initFirestore();
        CollectionReference collecRef;
        switch (collection){
            case Event:
                collecRef =  mInstance.collection("event");
                break;
            case Project:
                collecRef = mInstance.collection("test");
                break;
            default:
                collecRef = mInstance.collection("test");
                break;
        }
        return collecRef;
    }
}
