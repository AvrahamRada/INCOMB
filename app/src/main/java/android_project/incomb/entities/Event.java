package android_project.incomb.entities;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Event {
    // attributes
    private String placeId;
    private String hostId;
    private String idFest;
    private String eventName;
    private ArrayList<String> idGuest;

    //Constructor
    public Event() {
        this.idFest = FirebaseAuth.getInstance().getCurrentUser().getUid();
        idGuest =  new ArrayList<>();
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

    public String getEventName() { return eventName; }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public ArrayList<String> getIdGuest() { return idGuest; }

    //Methods
    public void addGuest(String guestID, Place eventPlace){
        if(idGuest.size() < eventPlace.getAmountOfGuest())
            idGuest.add(guestID);
    }
}
