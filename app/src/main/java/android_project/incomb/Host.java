package android_project.incomb;

import android.graphics.drawable.PictureDrawable;

import java.util.ArrayList;
import java.util.LinkedList;

public class Host extends Person {

    private String location; // Physical location
    private int amountOfGuest;
    private typeOfActivities typeOfActivity; // yoga, lecture or Social Action
    private typeOfSpaces space;// bar or class or studio
    private double rent;
    private String yourNameForThePlace;
    private LinkedList<ReservationsTimes> reservationsTimes; // in "ReservationsTimes" there will be start & end time (2 attributes)
    private ArrayList<PictureDrawable> arrOfAllPic; // need more thinking.....

    private enum typeOfActivities {yoga,lecture,socialAction};
    private enum typeOfSpaces {bar,studio,houseLivingRoom,openSpace};


    //constructor
//    public Host(String fullName, String email, String phoneNumber) {
//        super(fullName, email, phoneNumber);
//        setLocation(location);
//        setAmountOfGuest(amountOfGuest);
//        setYourNameForThePlace(yourNameForThePlace);
//        setRent(rent);
//        setTypeOfActivity(typeOfActivity);
//    }

    public Host() {
        super();
    }

    //Getters and Setters
    private void setTypeOfActivity(typeOfActivities typeOfActivity) {
        this.typeOfActivity = (typeOfActivities)typeOfActivity;
    }
    public typeOfActivities getTypeOfActivity() {
        return typeOfActivity;
    }

    private void setTypeOfSpaces(typeOfSpaces space) {
        this.space = (typeOfSpaces)space;
    }
    public typeOfSpaces getTypeOfSpaces() {
        return space;
    }

    public String getLocation() {
        return location;
    }
    private void setLocation(String location) {
        this.location = location;
    }

    private void setYourNameForThePlace(String yourNameForThePlace) {
        this.yourNameForThePlace = yourNameForThePlace;
    }
    public String getYourNameForThePlace() {
        return this.yourNameForThePlace;
    }

    public double getRent() {
        return this.rent;
    }
    private void setRent(double rent) {
        this.rent = rent;
    }

    private boolean setAmountOfGuest(int amountOfGuest) {
        this.amountOfGuest = (amountOfGuest >= 0) ? amountOfGuest : -1;
        return this.amountOfGuest >= 0 ? true : false;
    }
    public int getAmountOfGuest() {
        return amountOfGuest;
    }
}
