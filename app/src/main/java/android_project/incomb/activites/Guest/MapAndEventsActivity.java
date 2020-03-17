package android_project.incomb.activites.Guest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.activites.Fest.Adapter.PersonListAdapter;
import android_project.incomb.activites.Fest.DateRangeActivity;
import android_project.incomb.activites.Fest.MapAndPlacesActivity;
import android_project.incomb.entities.Host;
import android_project.incomb.entities.Person;
import android_project.incomb.entities.ReservationsTimes;

public class MapAndEventsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int CHECK_PLACE_REQUEST_CODE = 2;
    public static final int PLACE_SEARCH_OK = 3;
    private static final String TAG = "TAG";
    private static final String MAIN_AND_PLACES_ACTIVITY = "MapAndPlacesActivity";

    //result after the DateRangeActivity
    String typeActivity;
    ReservationsTimes calender;
    List<android_project.incomb.entities.Place> places = new ArrayList<>();
    //List view result
    ListView mListView;
    ArrayList<Host> hostList;
    String name;

    private AutoCompleteTextView mAddressField;
    private StringBuilder mResult;
    private List<String> options;

    // Map Object
    private GoogleMap mMap;
    //responsible of current location of device
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //loading suggestions
    private PlacesClient placesClient;
    // places suggested by google
    private List<AutocompletePrediction> predictionList;
    // Store the last location of device
    private Location mLastKnownLocation;
    // updating the user if last location is null
    private LocationCallback locationCallback;

//  private MaterialSearchBar materialSearchBar;
    private View mapView;

    private final float DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_and_places);
        mListView = (ListView)findViewById(R.id.list_view);

        // Map Config
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapAndEventsActivity.this);
        mAddressField = findViewById(R.id.guest_place);
        // Initialize the SDK
        Places.initialize(getApplicationContext(), getString(R.string.api_key));
        // Create a new Places client instance
        placesClient = Places.createClient(this);
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        mAddressField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                autoComplete();
            }
        });

    }

    // GOOGLE outocomplete
    private void autoComplete() {
        PlacesClient placesClient = Places.createClient(getApplicationContext());
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596));
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setLocationBias(bounds)
                .setCountry("IL")
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(mAddressField.getText().toString())
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(response -> {
            mResult = new StringBuilder();
            options = new ArrayList();
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                mResult.append(" ").append(prediction.getFullText(null) + "\n");
                options.add(prediction.getFullText(null).toString());
            }

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, options);

            mAddressField.setAdapter(adapter);
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
            }
        });
    }

        // Map is ready and loaded
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);
        }

        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(MapAndEventsActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(MapAndEventsActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(MapAndEventsActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MapAndEventsActivity.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                return false;
            }
        });
    }

    public void openDateRangePick(View view) {
        startActivityForResult(new Intent(MapAndEventsActivity.this,DateRangeActivity.class),CHECK_PLACE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
        else if(requestCode == CHECK_PLACE_REQUEST_CODE){
            if(resultCode == PLACE_SEARCH_OK){
                typeActivity = data.getStringExtra("type Activity");
                String CalenderString = data.getStringExtra("calendar");
                calender = new Gson().fromJson(CalenderString, ReservationsTimes.class);
                FirebaseFirestore.getInstance()
                        .collection("places")
                        .whereEqualTo( "typeOfActivity",typeActivity)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            places.addAll(queryDocumentSnapshots.toObjects(android_project.incomb.entities.Place.class));
                            setList();
                        });
            }

            int i =0;
            ArrayList<LatLng> latLngArrayList = new ArrayList<>();
            for (android_project.incomb.entities.Place placeCheck: places) {
                GeoPoint geo = placeCheck.getLocation();
                latLngArrayList.add(new LatLng(geo.getLatitude(),geo.getLongitude()));
                mMap.addMarker(new MarkerOptions().position(latLngArrayList.get(i)).title("Marker"));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(i)));
                i++;
            }

        }
    }

    private void setList() {
        List<android_project.incomb.entities.Place> temp = new ArrayList<>();
        for(android_project.incomb.entities.Place placeCheck: places){
            ReservationsTimes check = placeCheck.getAvailability();
            if(calender.getStartEvent().before(check.getStartEvent()) || calender.getEndEvent().after(check.getEndEvent()))
                temp.add(placeCheck);
        }
        places.removeAll(temp);
        showHost();
    }

    private void showHost() {
        hostList = new ArrayList<>();
        for (android_project.incomb.entities.Place placeCheck: places) {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(placeCheck.getIdHost())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Person host = documentSnapshot.toObject(Person.class);
                        hostList.add(new Host(host.getFullName(),host.getEmail(),host.getPhoneNumber()));
                        createAdapter(hostList);
                    });
        }
    }

    //list of host - need to keep it for every one
    private void createAdapter(ArrayList<Host> temp) {
        PersonListAdapter adapter = new PersonListAdapter(this,R.layout.adapter_view_layout,temp);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            if(!temp.get(position).isSelected()){
                temp.get(position).setSelected(!temp.get(position).isSelected());
                view.setBackgroundColor(getColor(R.color. colorGreenPrimary));
            }
            else{
                temp.get(position).setSelected(!temp.get(position).isSelected());
                view.setBackgroundColor(getColor(R.color.white));
            }
        });
    }

    public void makeEventClick(View view) {
        for (Host host:hostList) {
            if(host.isSelected()){
                getNameForEvent();
            }
        }
    }

    private void getNameForEvent() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapAndEventsActivity.this);
        alertDialog.setTitle("Make Event");
        alertDialog.setMessage("Enter event name");
        final EditText input = new EditText(MapAndEventsActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    name = input.getText().toString();
                }catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Error! Wrong input, please check", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(MapAndEventsActivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
