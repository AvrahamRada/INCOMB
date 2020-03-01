package android_project.incomb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RentPlace extends AppCompatActivity {

    private TextInputEditText mCapacity,mLocation,mPrice,mDisPrice, mDishour;
    private FirebaseAuth fAuth;
    private Button button;
    private Geocoder geoCoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_place);
        getSupportActionBar().hide();

        mCapacity = (TextInputEditText) findViewById(R.id.capacity);
        mLocation = (TextInputEditText) findViewById(R.id.Location);
        mPrice = (TextInputEditText) findViewById(R.id.price);
        mDisPrice = (TextInputEditText) findViewById(R.id.discount_price);
        mDishour = (TextInputEditText) findViewById(R.id.discount_hour);
        button = (Button) findViewById(R.id.firstStep);

        //spinner-place
        Spinner placeSpin = findViewById(R.id.columnSpinner);
        ArrayAdapter<CharSequence> adapterPlace = ArrayAdapter.createFromResource(this, R.array.spinner_place, android.R.layout.simple_spinner_item);
        adapterPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpin.setAdapter(adapterPlace);
        placeSpin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        //spinner-suitable 'need to check how to do a multiple spinner'
        //https://stackoverflow.com/questions/5015686/android-spinner-with-multiple-choice
        Spinner suitableSpin = findViewById(R.id.columnSpinner);
        ArrayAdapter<CharSequence> adapterSuitable = ArrayAdapter.createFromResource(this, R.array.spinner_suitable, android.R.layout.simple_spinner_item);
        adapterSuitable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suitableSpin.setAdapter(adapterSuitable);
        suitableSpin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        //String locationHost =  mLocation.getText().toString();
        //findLocation(locationHost);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();

            }
        });
    }

    public void nextStep() {
        Intent nextStep = new Intent(this, RentPlaceCalendar.class);
        startActivity(nextStep);
        finish();
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
