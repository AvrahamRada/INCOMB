package android_project.incomb.entities;


public class Fest {
    // attributes
    private Event event;
    private Place eventPlace;
    private Person fest;
    private String eventId;
    private boolean selected = false;

    //Constructor
    public Fest() {

    }

    //Getters and Setters
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Place getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(Place eventPlace) {
        this.eventPlace = eventPlace;
    }

    public Person getFest() {
        return fest;
    }

    public void setFest(Person fest) {
        this.fest = fest;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
