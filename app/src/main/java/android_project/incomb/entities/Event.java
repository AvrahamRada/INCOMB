package android_project.incomb.entities;

import com.google.firebase.auth.FirebaseAuth;

import java.time.Clock;
import java.util.ArrayList;

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

    public String getPlaceId() {
        return placeId;
    }

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

    public String getEventName() {
        return eventName;
    }

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
//    public void addGuest(String string){
//        if(idGuest.size() < eventPlace.getAmountOfGuest())
//            idGuest.add(string);
//    }
}
