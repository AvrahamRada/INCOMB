package android_project.incomb.entities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;
import java.util.ArrayList;

public class Place {
    // attributes
    private GeoPoint location; // Physical location
    private int amountOfGuest;
    private typeOfActivities activityType; // yoga, lecture or Social Action
    private typeOfSpaces spaceType;// bar or class or studio
    private double rent;
    private String placeName;
    private String idHost;
    private ArrayList<String> idGuest;
    private Amenities amenities;

    private enum typeOfActivities {Yoga,Lecture,SocialAction};
    private enum typeOfSpaces {Bar,Studio,LivingRoom,OpenSpace};

    //Constructor
    public Place() {
        this.idHost = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.amenities = new Amenities();
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

    private void setLocation(GeoPoint location) {
        this.location = location;
    }
    public GeoPoint getLocation() {
        return location;
    }

    private void setYourNameForThePlace(String placeName) {
        this.placeName = placeName;
    }
    public String getYourNameForThePlace() {
        return this.placeName;
    }

    private void setRent(double rent) {
        this.rent = rent;
    }
    public double getRent() {
        return this.rent;
    }

    private boolean setAmountOfGuest(int amountOfGuest) {
        this.amountOfGuest = (amountOfGuest >= 0) ? amountOfGuest : -1;
        return this.amountOfGuest >= 0 ? true : false;
    }
    public int getAmountOfGuest() {
        return amountOfGuest;
    }

    //Methods
    public void addGuest(String string){ idGuest.add(string); }
    public void addLocation(GeoPoint location){ setLocation(location); }
    public void addAmountOfGuest(int amountOfGuest){ setAmountOfGuest(amountOfGuest); }
    public void addYourNameForThePlace(String placeName){ setYourNameForThePlace(placeName); }
    public void addRent(double rent){ setRent(rent); }
    public void addTypeOfActivity(String activityType) { setTypeOfActivity(activityType); }
    public void addTypeOfSpaces(String spaceType) {setTypeOfSpaces(spaceType); }
    public void updateCheck(String string, boolean check) { amenities.updateAmenities(string,check);
    }

}
