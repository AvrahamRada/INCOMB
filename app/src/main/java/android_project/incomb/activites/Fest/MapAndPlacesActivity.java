package android_project.incomb.activites.Fest;

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
import android.widget.AdapterView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import android_project.incomb.R;
import android_project.incomb.entities.Event;
import android_project.incomb.entities.Host;
import android_project.incomb.entities.Person;
import android_project.incomb.activites.Fest.Adapter.PersonListAdapter;
import android_project.incomb.entities.ReservationsTimes;

public class MapAndPlacesActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int CHECK_PLACE_REQUEST_CODE = 2;
    public static final int PLACE_SEARCH_OK = 3;
    private static final String TAG = "TAG";
    private static final String MAIN_AND_PLACES_ACTIVITY = "MapAndPlacesActivity";

    //result after the DateRangeActivity
    String typeActivity;
    ReservationsTimes calender;
    List<android_project.incomb.entities.Place> places = new ArrayList<>();
    Map<android_project.incomb.entities.Place,String> placeIdmap = new HashMap<>();
    //List view result
    ListView mListView;
    ArrayList<Host> hostList;
    String eventName;
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapAndPlacesActivity.this);
        mAddressField = findViewById(R.id.fest_place);
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

//        // Initialize the AutocompleteSupportFragment.
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
//            }
//
//            @Override
//            public void onError(@NonNull Status status) {
//                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
//            }
//
//        });
//
//        // Create a RectangularBounds object.
//        // specifies latitude and longitude bounds to constrain results to the specified region.
//        RectangularBounds bounds = RectangularBounds.newInstance(
//                new LatLng(31.894756, 34.809322),   // 14A John Street, Sydney, New South Wales, 2037 Glebe Sydney Australia
//                new LatLng(32.109333, 34.855499));  // Cowper Wharf Roadway, Sydney, New South Wales, 2011 Potts Point Sydney Australia
//
//        // Use the builder to create a FindAutocompletePredictionsRequest.
//        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
//                // Call either setLocationBias() OR setLocationRestriction().
//                .setLocationBias(bounds)
////                .setLocationRestriction(bounds)
//                .setOrigin(new LatLng(31.894756,34.809322))
//                .setCountries("IL") // indicating the country or countries to which results should be restricted.
//                .setTypeFilter(TypeFilter.ADDRESS) // restrict the results to the specified place type: ADDRESS,REGIONS,LOCALITY
//                .setSessionToken(token) // A AutocompleteSessionToken, which groups the query and selection phases of a user search into a discrete session for billing purposes.
//                // The session begins when the user starts typing a query, and concludes when they select a place.
//                .setQuery("ראשון לציון")     // A query string containing the text typed by the user.
//                .build();
//
//        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
//            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
//                Log.i(TAG, prediction.getPlaceId());
//                Log.i(TAG, prediction.getPrimaryText(null).toString());
//            }
//        }).addOnFailureListener((exception) -> {
//            if (exception instanceof ApiException) {
//                ApiException apiException = (ApiException) exception;
//                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
//            }
//        });

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

        SettingsClient settingsClient = LocationServices.getSettingsClient(MapAndPlacesActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(MapAndPlacesActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(MapAndPlacesActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MapAndPlacesActivity.this, 51);
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
        startActivityForResult(new Intent(MapAndPlacesActivity.this,DateRangeActivity.class),CHECK_PLACE_REQUEST_CODE);
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
                            for (QueryDocumentSnapshot doc :queryDocumentSnapshots) {
                                placeIdmap.put(doc.toObject(android_project.incomb.entities.Place.class),doc.getId());
                            }
                            setList();
                        });
            }
        }
    }

    private void setList() {
        Iterator it = placeIdmap.entrySet().iterator();
        //list of places (empty)
        List<android_project.incomb.entities.Place> temp = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            temp.add((android_project.incomb.entities.Place) pair.getKey());
            }
        //remove elements from map by keys from list
        for(android_project.incomb.entities.Place key : temp ){
            if(!key.getTypeOfActivity().equals(typeActivity))
                placeIdmap.remove(key);
            else{
                ReservationsTimes check = key.getAvailability();
                if(calender.getStartEvent().before(check.getStartEvent()) || calender.getEndEvent().after(check.getEndEvent()))
                    placeIdmap.remove(key);
            }
        }
        showHost();
        setMap();
    }

    private void setMap() {
        double latitude;
        double longitude;
        List<android_project.incomb.entities.Place> places = new ArrayList<>(placeIdmap.keySet());
        ArrayList<LatLng> latLngArrayList = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            latitude = latLngArrayList.get(i).latitude;
            longitude = latLngArrayList.get(i).longitude;
            latLngArrayList.add(new LatLng(latitude, longitude));
            mMap.addMarker(new MarkerOptions().position(latLngArrayList.get(i)).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(i)));
        }
    }

    private void showHost() {
        hostList = new ArrayList<>();
        Iterator it = placeIdmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            android_project.incomb.entities.Place checkPlace = (android_project.incomb.entities.Place) pair.getKey();
            String placeId = (String) pair.getValue();
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(checkPlace.getIdHost())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Host addHost =  new Host();
                        addHost.setHostPlace(checkPlace);
                        addHost.setHost(documentSnapshot.toObject(Person.class));
                        addHost.setPlaceId(placeId);
                        hostList.add(addHost);
                        createAdapter(hostList);
                    });
            it.remove(); // avoids a ConcurrentModificationException
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
                getNameForEvent(eventName ->
                        uploadEventToDB(host, eventName));
            }
        }
    }

    private void uploadEventToDB(Host addHost, String eventName) {
        Event newEvent =  new Event();
        newEvent.setEventName(eventName);
        newEvent.setPlaceId(addHost.getPlaceId());
        android_project.incomb.entities.Place temp = addHost.getHostPlace();
        newEvent.setHostId(temp.getIdHost());
        FirebaseFirestore.getInstance()
                .collection("event")
                .add(newEvent)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getApplicationContext(), "Event add", Toast.LENGTH_SHORT).show();
                });
    }

    private void getNameForEvent(Consumer<String> onOkClick) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapAndPlacesActivity.this);
        alertDialog.setTitle("Make Event");
        alertDialog.setMessage("Enter event name");
        final EditText input = new EditText(MapAndPlacesActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    eventName = input.getText().toString();
                    onOkClick.accept(eventName);
                }catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Error! Wrong input, please check", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
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
                            Toast.makeText(MapAndPlacesActivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
