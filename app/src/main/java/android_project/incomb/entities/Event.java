package android_project.incomb.entities;

import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import android_project.incomb.activites.Host.MyPlaceActivity;

public class Event {
    // attributes
    private String placeId;
    private String hostId;
    private String idFest;
    private String eventName;
    private ArrayList<String> idGuest;
    private int eventPrice;
    private float eventTime;
    private float duration;

    //Constructor
    public Event() {
        //setIdFest(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    //Getters and Setters

    public String getPlaceId() { return placeId;  }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getIdFest() {
        return idFest;
    }

    public void setIdFest(String idFest) {
        this.idFest = idFest;
    }

    public String getEventName() { return eventName; }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


//    public String getPlaceId() { return placeId; }
//
//    public void setPlaceId(Place eventPlace) { this.placeId = eventPlace.getIdHost(); }
//
//    public String getIdFest() { return idFest; }
//
//    public void setIdFest(String idFest) { this.idFest = idFest; }
//
//    public ArrayList<String> getIdGuest() { return idGuest; }
//
//    public void setIdGuest(ArrayList<String> idGuest) { this.idGuest = idGuest; }



    //Methods
    public void addGuest(String guestID){
        AtomicReference<Place> eventPlace = null;
        FirebaseFirestore.getInstance()
                .collection("places")
                .document(placeId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    eventPlace.set(documentSnapshot.toObject(Place.class));
                });
        if(idGuest.size() < eventPlace.get().getAmountOfGuest())
            idGuest.add(guestID);
    }
}
