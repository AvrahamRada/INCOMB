package android_project.incomb.activites.Fest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android_project.incomb.R;
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
    ListView mListView;

    ArrayList<Host> hostList;

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

//    private MaterialSearchBar materialSearchBar;
    private View mapView;

    private final float DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_and_places);
        mListView = (ListView)findViewById(R.id.list_view);

//        materialSearchBar = findViewById(R.id.searchBar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapAndPlacesActivity.this);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), getString(R.string.api_key));
        // Create a new Places client instance
        placesClient = Places.createClient(this);
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

//        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
//            @Override
//            public void onSearchStateChanged(boolean enabled) {
//
//            }
//
//            @Override
//            public void onSearchConfirmed(CharSequence text) {
//                startSearch(text.toString(), true, null, true);
//            }
//
//            @Override
//            public void onButtonClicked(int buttonCode) {
//                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
//                    //opening or closing a navigation drawer
//                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
//                    materialSearchBar.disableSearch();
//                }
//            }
//        });
//
//        materialSearchBar.addTextChangeListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
//                        .setTypeFilter(TypeFilter.ADDRESS)
//                        .setSessionToken(token)
//                        .setQuery(s.toString())
//                        .build();
//                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
//                    @Override
//                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
//                        if (task.isSuccessful()) {
//                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
//                            if (predictionsResponse != null) {
//                                predictionList = predictionsResponse.getAutocompletePredictions();
//                                List<String> suggestionsList = new ArrayList<>();
//                                for (int i = 0; i < predictionList.size(); i++) {
//                                    AutocompletePrediction prediction = predictionList.get(i);
//                                    suggestionsList.add(prediction.getFullText(null).toString());
//                                }
//                                materialSearchBar.updateLastSuggestions(suggestionsList);
//                                if (!materialSearchBar.isSuggestionsVisible()) {
//                                    materialSearchBar.showSuggestionsList();
//                                }
//                            }
//                        } else {
//                            Log.i("mytag", "prediction fetching task unsuccessful");
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
//            @Override
//            public void OnItemClickListener(int position, View v) {
//                if (position >= predictionList.size()) {
//                    return;
//                }
//                AutocompletePrediction selectedPrediction = predictionList.get(position);
//                String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
//                materialSearchBar.setText(suggestion);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        materialSearchBar.clearSuggestions();
//                    }
//                }, 1000);
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                if (imm != null)
//                    imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
//                final String placeId = selectedPrediction.getPlaceId();
//                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);
//
//                FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
//                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
//                    @Override
//                    public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
//                        Place place = fetchPlaceResponse.getPlace();
//                        Log.i("mytag", "Place found: " + place.getName());
//                        LatLng latLngOfPlace = place.getLatLng();
//                        if (latLngOfPlace != null) {
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        if (e instanceof ApiException) {
//                            ApiException apiException = (ApiException) e;
//                            apiException.printStackTrace();
//                            int statusCode = apiException.getStatusCode();
//                            Log.i("mytag", "place not found: " + e.getMessage());
//                            Log.i("mytag", "status code: " + statusCode);
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void OnItemDeleteListener(int position, View v) {
//
//            }
//        });

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }

        });

        // Create a RectangularBounds object.
        // specifies latitude and longitude bounds to constrain results to the specified region.
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(31.894756, 34.809322),   // 14A John Street, Sydney, New South Wales, 2037 Glebe Sydney Australia
                new LatLng(32.109333, 34.855499));  // Cowper Wharf Roadway, Sydney, New South Wales, 2011 Potts Point Sydney Australia

        // Use the builder to create a FindAutocompletePredictionsRequest.
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
//                .setLocationRestriction(bounds)
                .setOrigin(new LatLng(31.894756,34.809322))
                .setCountries("IL") // indicating the country or countries to which results should be restricted.
                .setTypeFilter(TypeFilter.ADDRESS) // restrict the results to the specified place type: ADDRESS,REGIONS,LOCALITY
                .setSessionToken(token) // A AutocompleteSessionToken, which groups the query and selection phases of a user search into a discrete session for billing purposes.
                // The session begins when the user starts typing a query, and concludes when they select a place.
                .setQuery("ראשון לציון")     // A query string containing the text typed by the user.
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                Log.i(TAG, prediction.getPlaceId());
                Log.i(TAG, prediction.getPrimaryText(null).toString());
            }
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
//                if (materialSearchBar.isSuggestionsVisible())
//                    materialSearchBar.clearSuggestions();
//                if (materialSearchBar.isSearchEnabled())
//                    materialSearchBar.disableSearch();
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
                            places.addAll(queryDocumentSnapshots.toObjects(android_project.incomb.entities.Place.class));
                            setList();
                        });
            }
        }
    }

    private void setList() {
//        for (android_project.incomb.entities.Place placeCheck: places) {
//            ReservationsTimes check = placeCheck.getAvailability();
//            FirebaseFirestore.getInstance().collection("users").document(placeCheck.getIdHost()).get().addOnSuccessListener(documentSnapshot -> {
//                Person host = documentSnapshot.toObject(Person.class);
//                host.getFullName();
//                host.getEmail();
//                host.getPhoneNumber();
//            });
            // need to remove to places that out of range
            //if(check.getStartEvent().before(calender.getStartEvent()) || check.getEndEvent().after(calender.getEndEvent()))
            //places.remove(placeCheck);
//        }
//        for (android_project.incomb.entities.Place placeCheck: places) {
//            ReservationsTimes check = placeCheck.getAvailability();
//            // need to remove to places that out of range
//            //if(check.getStartEvent().before(calender.getStartEvent()) || check.getEndEvent().after(calender.getEndEvent()))
//                //places.remove(placeCheck);
//        }
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
        ArrayList<Host> temp = new ArrayList<>();
        for (android_project.incomb.entities.Place placeCheck: places) {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(placeCheck.getIdHost())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Person host = documentSnapshot.toObject(Person.class);
                        temp.add(new Host(host.getFullName(),host.getEmail(),host.getPhoneNumber()));
                        createAdapter(temp);
                    });
        }
    }

    private void createAdapter(ArrayList<Host> temp) {
        PersonListAdapter adapter = new PersonListAdapter(this,R.layout.adapter_view_layout,temp);
        mListView.setAdapter(adapter);
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

}
