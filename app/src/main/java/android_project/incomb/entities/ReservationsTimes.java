package android_project.incomb.entities;

import java.util.Date;

public class ReservationsTimes {
    // attributes
    private Date startEvent;
    private Date endEvent;

    //constructor
    public ReservationsTimes() {
    }

    public ReservationsTimes(Date startEvent, Date endEvent) {
        setStartEvent(startEvent);
        setEndEvent(endEvent);
    }

    //Getters and Setters
    public Date getStartEvent() { return startEvent; }

    public void setStartEvent(Date startEvent) { this.startEvent = startEvent; }

    public Date getEndEvent() { return endEvent; }

    public void setEndEvent(Date endEvent) { this.endEvent = endEvent; }
}
