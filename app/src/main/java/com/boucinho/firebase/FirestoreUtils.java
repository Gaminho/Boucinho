package com.boucinho.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreUtils {

    public static FirebaseFirestore initFirestore() {
        return FirebaseFirestore.getInstance();
    }

    public enum Collections {
        Event,
        Project
    }

    public static CollectionReference getCollection(Collections collection) {
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
