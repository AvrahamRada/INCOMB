package android_project.incomb.entities;

import android.graphics.drawable.PictureDrawable;

import java.util.ArrayList;
import java.util.LinkedList;

public class Host extends Person {

    private ArrayList<Place> listOfPlaces;
    //private ArrayList<Host> list;
    private LinkedList<ReservationsTimes> reservationsTimes; // in "ReservationsTimes" there will be start & end time (2 attributes)
    private ArrayList<PictureDrawable> arrOfAllPic; // need more thinking.....

    //constructor
//    public Host(String fullName, String email, String phoneNumber) {
//        super(fullName, email, phoneNumber);
//        setLocation(location);
//        setAmountOfGuest(amountOfGuest);
//        setYourNameForThePlace(yourNameForThePlace);
//        setRent(rent);
//        setTypeOfActivity(typeOfActivity);
//    }Gson to string, from string

    public Host() {
        super();
    }

    public void addPlace(Place newPlace){
        listOfPlaces.add(newPlace);
    }
}
/* can delete this because i will use adapter*/