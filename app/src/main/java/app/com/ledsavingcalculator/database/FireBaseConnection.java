package app.com.ledsavingcalculator.database;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import app.com.ledsavingcalculator.util.EnergyCost;

public class FireBaseConnection {
    public static final String FIRE_FIREBASE_URL = "https://boiling-fire-3227.firebaseio.com/Canada/";
    private Firebase mRef;


    public FireBaseConnection() {
        mRef = new Firebase("https://boiling-fire-3227.firebaseio.com/Canada/Ontario");

    }

    public EnergyCost getSummerCost() {
        mRef = new Firebase("https://boiling-fire-3227.firebaseio.com/Canada/Ontario/Brampton/");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    System.out.println(postSnapshot);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        return new EnergyCost(0.00, 0.00, 0.00);

    }

}
