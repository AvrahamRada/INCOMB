package android_project.incomb.activites.Host;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.Iterator;
import java.util.Map;

import android_project.incomb.MainActivity;
import android_project.incomb.activites.Host.fragment.RentStepCalendarFragment;
import android_project.incomb.activites.Host.fragment.RentStepFourFragment;
import android_project.incomb.activites.Host.fragment.RentStepTwoFragment;
import android_project.incomb.entities.Place;
import android_project.incomb.R;

public class RentPlaceActivity extends AppCompatActivity implements IRentActivity, IRentView {

    Presenter mPresenter;
    Place newPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new Presenter(this);
        newPlace = new Place();
        setContentView(R.layout.activity_rent_place);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RentStepFourFragment(this))
                .commit();
    }

    @Override
    public void setFirstData(String capacity, String price, String place, String suitable) {
        newPlace.addAmountOfGuest(Integer.parseInt(capacity));
        newPlace.addRent(Double.parseDouble(price));
        newPlace.addTypeOfSpaces(place);
        newPlace.addTypeOfActivity(suitable);
        openSecondFragment();
    }

    @Override
    public void setTwoData(Map hmap) {
        Iterator it = hmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            newPlace.updateCheck((String) pair.getKey(), (boolean) pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        openCalendarFragment();
    }

    @Override
    public void setCalendarData(){
        //save data
        openFourFragment();
    }

    @Override
    public void setFourData(){
        //submit the place
        submitData();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void openSecondFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, /*new fragment*/new RentStepTwoFragment(this))
                .commit();
    }

    private void openCalendarFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, /*new fragment*/new RentStepCalendarFragment(this))
                .commit();
    }

    private void openFourFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, /*new fragment*/new RentStepFourFragment(this))
                .commit();
    }

    public void submitData(){
        //Place place = new Place(new GeoPoint(31,34), 30,"yoga","bar",30,"hello");
        FirebaseFirestore.getInstance()
                .collection("places")
                .add(newPlace)
                .addOnSuccessListener(documentReference -> {
                })
                .addOnFailureListener(e -> {
                    Log.i("n", "n");
                });
    }

    @Override
    public void updateUI() {
        //to do upate ui components
    }

//    public void findLocation(String location){
//        if(location!=null && !location.equals("")){
//            geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
//        }
//        try {
//            List<Address> addresses = geoCoder.getFromLocationName(location, 5);
//            String add = "";
//            if (addresses.size() > 0) {
//               GeoPoint p = new GeoPoint((int) (addresses.get(0).getLatitude() * 1E6), (int) (addresses.get(0).getLongitude() * 1E6));
//                mc.animateTo(p);
//                mapView.invalidate();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
