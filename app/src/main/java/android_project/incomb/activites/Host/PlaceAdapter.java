package android_project.incomb.activites.Host;

import java.util.ArrayList;
import java.util.List;

import android_project.incomb.entities.Place;

public class PlaceAdapter {
    List<Place> myPlace = new ArrayList<>();

    public void addPlace(Place newPlace){
        myPlace.add(newPlace);
    }
}
