package android_project.incomb.entities;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Place {
    // attributes
    private GeoPoint location; // Physical location
    private int amountOfGuest;
    private typeOfActivities activityType; // yoga, lecture or Social Action
    private typeOfSpaces spaceType;// bar or class or studio
    private double rent;
    private String placeName;
    private String idHost;

    private Amenities amenities;
    private ReservationsTimes availability;
    private List<String> imagesList = new ArrayList<>();

    private enum typeOfActivities {Yoga, Lecture, SocialAction};

    private enum typeOfSpaces {Bar, Studio, LivingRoom, OpenSpace};

    //Constructor
    public Place() {
        this.idHost = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.amenities = new Amenities();
    }

    //Getters and Setters
    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public boolean setAmountOfGuest(int amountOfGuest) {
        this.amountOfGuest = (amountOfGuest >= 0) ? amountOfGuest : -1;
        return this.amountOfGuest >= 0 ? true : false;
    }

    public int getAmountOfGuest() {
        return amountOfGuest;
    }

    public void setTypeOfActivity(String activityType) {
        this.activityType = typeOfActivities.valueOf(activityType);
    }

    public typeOfActivities getTypeOfActivity() {
        return activityType;
    }

    public void setTypeOfSpaces(String spaceType) {
        this.spaceType = typeOfSpaces.valueOf(spaceType);
    }

    public typeOfSpaces getTypeOfSpaces() {
        return spaceType;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getRent() {
        return this.rent;
    }


    public void setYourNameForThePlace(String placeName) {
        this.placeName = placeName;
    }

    public String getYourNameForThePlace() {
        return this.placeName;
    }

    public String getIdHost() {
        return this.idHost;
    }

    public Amenities getAmenities() { return amenities;}

    public ReservationsTimes getAvailability() {
        return availability;
    }

    public void setAvailability(Date startEvent, Date endEvent) {
        this.availability = new ReservationsTimes(startEvent, endEvent);
    }

    public List<String> getImagesList() { return imagesList; }

    public void setImagesList(List<Uri> uriImagesList) {
        for (Uri uri : uriImagesList) {
            this.imagesList.add(uri.toString());
        }
    }

    //Methods
    public void updateCheck(String string, boolean check) {
        amenities.updateAmenities(string, check);
    }

    public void updateAvailability(Date startEvent, Date endEvent) {
        availability.updareReservation(startEvent, endEvent);
    }
}
