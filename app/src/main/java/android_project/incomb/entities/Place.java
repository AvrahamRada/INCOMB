package android_project.incomb.entities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;
import java.util.ArrayList;

public class Place {

    private GeoPoint location; // Physical location
    private int amountOfGuest;
    private typeOfActivities activityType; // yoga, lecture or Social Action
    private typeOfSpaces spaceType;// bar or class or studio
    private double rent;
    private String placeName;
    private String idHost;
    private ArrayList<String> idGuest;

    private enum typeOfActivities {yoga,lecture,socialAction};
    private enum typeOfSpaces {bar,studio,houseLivingRoom,openSpace};

    public Place(GeoPoint location, int amountOfGuest, String activityType, String spaceType, double rent, String placeName) {
        setAmountOfGuest(amountOfGuest);
        setLocation(location);
        setTypeOfActivity(activityType);
        setTypeOfSpaces(spaceType);
        setRent(rent);
        setYourNameForThePlace(placeName);
        this.idHost = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    //Getters and Setters
    private void setTypeOfActivity(String activityType) {
        this.activityType = typeOfActivities.valueOf(activityType);
    }
    public typeOfActivities getTypeOfActivity() {
        return activityType;
    }

    private void setTypeOfSpaces(String spaceType) {
        this.spaceType = typeOfSpaces.valueOf(spaceType);
    }
    public typeOfSpaces getTypeOfSpaces() {
        return spaceType;
    }

    public GeoPoint getLocation() {
        return location;
    }
    private void setLocation(GeoPoint location) {
        this.location = location;
    }

    private void setYourNameForThePlace(String placeName) {
        this.placeName = placeName;
    }
    public String getYourNameForThePlace() {
        return this.placeName;
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

    // add guest....
}
