package android_project.incomb.activites.Host;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;
import com.squareup.timessquare.CalendarPickerView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Adapter.ViewPagerAdapter;
import android_project.incomb.entities.Place;

public class EditPlaceActivity extends AppCompatActivity {
    public static final int PLACE_UPDATED_OK = 5;
    public static final int PLACE_UPLOADED_FAIL = 6;

    private EditText nameEditText, capacityEditText, priceRentEditText;
    private TextView locationView;
    private CheckBox chKitchen, chWifi, chYoga, chToilet, chTable, chSink;
    private Place mPlace;
    private String docId,sRent,sAmount,sLocation;
    private Map<String, Boolean> hmap = new HashMap<>();
    private Button btnUpdate, btnCancel;
    private CalendarPickerView datePicker;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place);

        String placeString = getIntent().getStringExtra("place");
        mPlace = new Gson().fromJson(placeString, Place.class);
        findViews();
        setViews();
        findDocID();
        btnUpdate.setOnClickListener(v -> updateDetails());

        btnCancel.setOnClickListener(v -> {
            Intent intentCancel = new Intent();
            setResult(PLACE_UPLOADED_FAIL, intentCancel);
            finish(); });
    }

    private void findDocID() {
        FirebaseFirestore.getInstance()
                .collection("places")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments())
                    {
                        if(mPlace.equals(doc.toObject(Place.class))) {
                            docId = doc.getId();
                            break;
                        }
                    }
                });
    }

    private void updateDetails() {
        updatePlace();
        Intent intentUpdate = new Intent();
        intentUpdate.putExtra("Doc id",docId);
        intentUpdate.putExtra("place", new Gson().toJson(mPlace));
        setResult(PLACE_UPDATED_OK, intentUpdate);
        finish();
    }


    private void findViews() {
        //Name,Price,Capacity,Location
        this.nameEditText = findViewById(R.id.name);
        this.capacityEditText = findViewById(R.id.capacity);
        this.priceRentEditText = findViewById(R.id.priceRent);
        this.locationView = findViewById(R.id.location);
        //Buttons
        this.btnCancel = findViewById(R.id.updatePlace);
        this.btnUpdate =  findViewById(R.id.exitEdit);
        //Calender
        this.datePicker = findViewById(R.id.update_dates);
        //Check box
        this.chKitchen =  findViewById(R.id.checkbox_kitchen);
        this.chWifi = findViewById(R.id.checkbox_wifi);
        this.chYoga = findViewById(R.id.checkbox_yoga);
        this.chToilet = findViewById(R.id.checkbox_toilet);
        this.chTable = findViewById(R.id.checkbox_table);
        this.chSink = findViewById(R.id.checkbox_sink);
        //photo slider
        this.viewPager = findViewById(R.id.view_pager);
    }

    private void setViews() {
        nameEditText.setText(mPlace.getYourNameForThePlace());
        sAmount = Integer.toString(mPlace.getAmountOfGuest());
        capacityEditText.setText(sAmount);
        sRent = Double.toString(mPlace.getRent());
        priceRentEditText.setText(sRent);
        sLocation = getAddress(mPlace.getLocation());
        locationView.setText(sLocation);
        check();
        selectDate();
        silderPhotos();
    }

    private String getAddress(GeoPoint location) {
        //Geocoder geoCoder =  new Geocoder(getBaseContext(), Locale.getDefault());
        Geocoder geoCoder =  new Geocoder(this, Locale.getDefault());
        String add = "";
        try{
            List<Address> addresses = geoCoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0)
            {
                for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); i++)
                    add += addresses.get(0).getAddressLine(i) + "\n";
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }

    private void silderPhotos(){
        if(!mPlace.getImagesList().isEmpty()){
            ViewPagerAdapter adapter = new ViewPagerAdapter(this, mPlace.getImagesList());
            viewPager.setAdapter(adapter);
        }
    }

    private void check(){
        chKitchen.setChecked(mPlace.getAmenities().isKitchen());
        chWifi.setChecked(mPlace.getAmenities().isWifi());
        chYoga.setChecked(mPlace.getAmenities().isYoga());
        chToilet.setChecked(mPlace.getAmenities().isToilet());
        chTable.setChecked(mPlace.getAmenities().isTable());
        chSink.setChecked(mPlace.getAmenities().isSink());
    }

    private void selectDate() {
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        datePicker.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDate(today);
    }

    private void updatePlace() {
        mPlace.setYourNameForThePlace(nameEditText.toString());
        mPlace.setAmountOfGuest(Integer.parseInt(capacityEditText.toString()));
        mPlace.setRent(Double.parseDouble(priceRentEditText.toString()));
        amenitiesCheck();
        reservationsTimesCheck();
    }

    private void reservationsTimesCheck() {
        if(!datePicker.getSelectedDates().isEmpty()){
            Date begin = datePicker.getSelectedDates().get(0);
            Date end = datePicker.getSelectedDates().get(datePicker.getSelectedDates().size() - 1);
            mPlace.setAvailability(begin, end);
        }
    }

    private void amenitiesCheck() {
        isCheck();
        Iterator it = hmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            mPlace.updateCheck((String) pair.getKey(), (boolean) pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    private void isCheck() {
        if(chKitchen.isChecked())
            hmap.put("kitchen", true);
        if(chWifi.isChecked())
            hmap.put("wifi", true);
        if(chYoga.isChecked())
            hmap.put("yoga", true);
        if(chToilet.isChecked())
            hmap.put("toilet", true);
        if(chTable.isChecked())
            hmap.put("table", true);
        if(chSink.isChecked())
            hmap.put("sink", true);
    }
}
