package android_project.incomb.entities;

import java.time.Clock;
import java.util.ArrayList;

public class Event {
    // attributes
    private Place eventPlace;
    private String idFest;
    private ArrayList<String> idGuest;
    private int eventPrice;
    private String eventName;
    private Clock eventTime;
    private double duration;

    //Constructor
    public Event(Place eventPlace, String idFest, ArrayList<String> idGuest) {
        setEventPlace(eventPlace);
        setIdFest(idFest);
        this.idGuest = idGuest;
    }

    //Getters and Setters
    public Place getEventPlace() { return eventPlace; }

    public void setEventPlace(Place eventPlace) { this.eventPlace = eventPlace; }

    public String getIdFest() { return idFest; }

    public void setIdFest(String idFest) { this.idFest = idFest; }

    public ArrayList<String> getIdGuest() { return idGuest; }

    public void setIdGuest(ArrayList<String> idGuest) { this.idGuest = idGuest; }



    //Methods
    public void addGuest(String string){
        if(idGuest.size() < eventPlace.getAmountOfGuest())
            idGuest.add(string);
    }
}
