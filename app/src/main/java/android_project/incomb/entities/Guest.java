package android_project.incomb.entities;

import java.util.Calendar;

public class Guest extends Person {
    // attributes
    private String typeActivity; // yoga, lecture or Social Action
    private String location; // Physical location
    private Calendar dateOfTheActivity; //check how to save dates

    //constructor
//    public Guest(String fullName, String email, String phoneNumber) {
//        super(fullName, email, phoneNumber);
//        setTypeActivity(typeActivity);
//        setLocation(location);
//        setDateOfTheActivity(dateOfTheActivity);
//    }

    public Guest() {
        super();
    }

    //Getters and Setters
    public String getTypeActivity() {
        return typeActivity;
    }

    private void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }

    public String getLocation() {
        return location;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    public Calendar getDateOfTheActivity() {
        return dateOfTheActivity;
    }

    private void setDateOfTheActivity(Calendar dateOfTheActivity) {
        this.dateOfTheActivity = dateOfTheActivity;
    }
}
