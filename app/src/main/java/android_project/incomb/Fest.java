package android_project.incomb;

import java.io.IOException;
import java.util.Calendar;

public class Fest extends Person {
    // attributes
    private String typeActivity; // yoga, lecture or Social Action
    private String space;// bar or class or studio
    private String location; // Physical location
    private int amountOfGuest;
    private Calendar dateOfTheActivity; //check how to save dates

    //constructor
    public Fest(String firstName, String lastName, String email, String typeActivity, String space, String location, int amountOfGuest, Calendar dateOfTheActivity) throws IOException {
        super(firstName, lastName, email);
        setTypeActivity(typeActivity);
        setSpace(space);
        setLocation(location);
        setAmountOfGuest(amountOfGuest);
        setDateOfTheActivity(dateOfTheActivity);
    }

    //Getters and Setters
    public String getTypeActivity() {
        return typeActivity;
    }

    private void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }

    public String getSpace() {
        return space;
    }

    private void setSpace(String space) {
        this.space = space;
    }

    public String getLocation() {
        return location;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    public int getAmountOfGuest() {
        return amountOfGuest;
    }

    private void setAmountOfGuest(int amountOfGuest) throws IOException {
        if(amountOfGuest > 0)
            this.amountOfGuest = amountOfGuest;
        else {
            throw new IOException();
        }
    }

    public Calendar getDateOfTheActivity() {
        return dateOfTheActivity;
    }

    private void setDateOfTheActivity(Calendar dateOfTheActivity) {
        this.dateOfTheActivity = dateOfTheActivity;
    }
}
